package com.companymanagement.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.dao.VendorDAO;
import com.companymanagement.model.Account;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.NotificationPreferedTypeService;
import com.companymanagement.service.VendorService;
import com.companymanagement.util.ConfUtil;

@Service("VendorService")
public class VendorServiceImpl extends BaseServiceImpl<Long, Vendor> implements VendorService {

	@Autowired
	protected VendorDAO dao;

	@Autowired
	protected AccountRoleService accRoleService;

	@Autowired
	protected NotificationPreferedTypeService nptService;

	@Autowired
	protected AccountService accService;

	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) dao);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		dao.setEntityManager(entityManager);
	}

	@Override
	public Vendor findVendorByRegNo(Long regNo) throws CompanyMgmtException {
		Map<String, Long> queryParams = new HashMap<String, Long>();
		queryParams.put("regNo", regNo);

		List<Vendor> vendors = findByNamedQueryAndNamedParams("Vendor.findVendorReg", queryParams);
		if (vendors.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_VENDORS_BY_SAME_NAME");
		}
		if (vendors.size() == 0) {
			return null;
		}
		return vendors.get(0);
	}

	@Override
	public Vendor findVendorByAccount(Account account) throws CompanyMgmtException {
		Map<String, Account> queryParams = new HashMap<String, Account>();
		queryParams.put("account", account);

		List<Vendor> vendors = findByNamedQueryAndNamedParams("Vendor.findByAccount", queryParams);
		if (vendors.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_VENDORS_BY_SAME_ID");
		}
		if (vendors.size() == 0) {
			return null;
		}
		return vendors.get(0);
	}

	@Override
	public List<Vendor> findVendorsByStatus(String status) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("status", status);

		List<Vendor> vendors = findByNamedQueryAndNamedParams("Vendor.findByStatus", queryParams);
		if (vendors.size() == 0) {
			return null;
		}
		return vendors;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Vendor vendor) throws CompanyMgmtException {
		vendor.setNpt(nptService.findNotificationPreferedType(vendor.getNpt().getName()));
		Vendor findVendor = findVendorByAccount(vendor.getAccount());

		// try catch for Upload Document
		try {
			String url = getDocumentURL(vendor);
			if (url != null) {
				vendor.setDocFileUrl(url);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CompanyMgmtException("Can not save vendor documents", e);
		}
		if (findVendor != null) {
			findVendor.setName(vendor.getName());
			findVendor.setStatus(vendor.getStatus());
			findVendor.setContact(vendor.getContact());
			// findVendor.setCertList(vendor.getCertList());
			findVendor.setDocFileUrl(vendor.getDocFileUrl());
			findVendor.setAccount(vendor.getAccount());
			findVendor.setNpt(vendor.getNpt());
			findVendor.setEmail(vendor.getEmail());
			dao.merge(findVendor);
		} else {
			dao.persist(vendor);
		}
	}

	// For uploading of file
	private String getDocumentURL(Vendor vendor) throws IOException {
		String locToSave = ResourceUtils.getFile(ConfUtil.get("fileServerLocation")).getPath();
		System.out.println(locToSave);
		if (vendor.getDocByteArray() != null && vendor.getDocFileExtention() != null) {
			String path = locToSave + File.separator + vendor.getName() + "." + vendor.getDocFileExtention();
			File file = new File(path);
			FileUtils.writeByteArrayToFile(file, vendor.getDocByteArray());
			System.out.println(ConfUtil.get("fileServerWebURL"));

			String webUrl = ConfUtil.get("fileServerWebURL") + vendor.getName() + "." + vendor.getDocFileExtention();
			return webUrl;
		}

		return null;
		// FileUtils.w
	}

	@Override
	@Transactional
	public void acceptByRegNo(Long regNo) throws CompanyMgmtException {
		Vendor findVendor = findVendorByRegNo(regNo);
		if (findVendor != null) {
			findVendor.getAccount().setAccountRole(accRoleService.findAccountRole("vendor"));
			findVendor.setStatus("Accepted");
			dao.merge(findVendor);
		}
	}

	@Override
	@Transactional
	public void rejectByRegNo(Long regNo) throws CompanyMgmtException {
		Vendor findVendor = findVendorByRegNo(regNo);
		if (findVendor != null) {
			findVendor.setStatus("Rejected");
			dao.merge(findVendor);
		}
	}

	@Override
	@Transactional
	public void deleteByRegNo(Vendor vendor) throws CompanyMgmtException {
		Vendor findVendor = findVendorByRegNo(vendor.getRegNo());
		if (findVendor != null) {
			if (findVendor.getDocFileUrl() != null) {
				String url = findVendor.getDocFileUrl();
				if (url != null && url.trim().length() != 0) {
					String locToSave = ConfUtil.get("fileServerLocation");
					String path = locToSave + File.separator + findVendor.getName() + "."
							+ url.substring(url.lastIndexOf(".") + 1, url.length());
					File file = new File(path);

					try {
						FileUtils.forceDelete(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						// do nothing
					}
				}
			}
			dao.remove(findVendor);
		}

	}

}
