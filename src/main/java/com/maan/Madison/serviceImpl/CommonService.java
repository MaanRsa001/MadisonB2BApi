package com.maan.Madison.serviceImpl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommonService {
	
	Logger log =LogManager.getLogger(getClass());

	
	
	public Properties getappconstantsProperty() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("appconstants.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (Exception e) {
			log.error(e);
		}
		return prop;
	}
	
	public Properties getappfieldnamesProperty() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("app_field_names.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (Exception e) {
			log.error(e);
		}
		return prop;
	}
	
	public Properties getapplicationProperty() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (Exception e) {
			log.error(e);
		}
		return prop;
	}

	public String reqPrint(Object response) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes ="";
		try {
			//log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
			jsonRes=mapper.writeValueAsString(response);
		} catch (Exception e) {
			log.error(e);
		}
		return jsonRes;		
	}
	
	

}
