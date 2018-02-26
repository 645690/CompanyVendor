package com.companymanagement.notification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

import com.companymanagement.common.CompanyMgmtException;

public class ConfigUtil {
	
	static Properties props = new Properties();
	
	
	static{
		try {
//			FileInputStream fis = new FileInputStream("C:\\Users\\645682\\Desktop\\CST\\CompanyMgmt\\src\\main\\resources\\config.properties");
			File file = ResourceUtils.getFile("classpath:config.properties");
			FileInputStream fis = new FileInputStream(file);
			props.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CompanyMgmtException("Can not load properties", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CompanyMgmtException("Can not load properties", e);
		}
	}
	
	public static String getKey(String key){
		return props.getProperty(key);
	}

}
