package com.maan.Madison.utilityServiceImpl;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {
	
	String SMTP_AUTH_USER="";
	String SMTP_AUTH_PWD="";
	
	 public SMTPAuthenticator(String username, String password) {
		this.SMTP_AUTH_USER=username;
		this.SMTP_AUTH_PWD=password;
	}

	public PasswordAuthentication getPasswordAuthentication()
     {
         return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
     }

}
