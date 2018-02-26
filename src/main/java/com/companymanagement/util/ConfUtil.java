package com.companymanagement.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class ConfUtil {

	static Properties props = new Properties();

	static {
		try {
			FileInputStream fis = new FileInputStream(ResourceUtils.getFile("classpath:config.properties"));
			// used when you put config.properties into target folder
			// FileInputStream fis = new FileInputStream("config.properties");
			props.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}

}
