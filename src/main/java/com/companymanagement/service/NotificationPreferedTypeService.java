package com.companymanagement.service;

import com.companymanagement.model.NotificationPreferedType;

public interface NotificationPreferedTypeService extends BaseService {

	NotificationPreferedType findNotificationPreferedType(String npt);

	void saveOrUpdate(NotificationPreferedType npt);

}
