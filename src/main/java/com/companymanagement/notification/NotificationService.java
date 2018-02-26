package com.companymanagement.notification;

import org.apache.commons.mail.EmailException;

public interface NotificationService {
	
	public void sendMail(String to, String cc[], String subject,String msg) throws Exception;

}
