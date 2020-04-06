package it.dstech.utility;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailUtility {
	public static void sendEmail(String indirizzoMail, String soggettoMail, String testoMail)throws AddressException, MessagingException, IOException {
             
		
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.properties"));
					
		
		Properties propertiesMail = new Properties();

		propertiesMail.put("mail.smtp.host", properties.getProperty("mail.host"));
		propertiesMail.put("mail.smtp.port", properties.getProperty("mail.smtp.socketFactory.port"));
		propertiesMail.put("mail.smtp.auth", properties.getProperty("mail.smtp.auth"));
		propertiesMail.put("mail.smtp.starttls.enable", properties.getProperty("mail.smtp.starttls.enable"));
		propertiesMail.put("mail.smtp.socketFactory.class", properties.getProperty("mail.smtp.socketFactory.class"));
		propertiesMail.put("mail.smtp.socketFactory.fallback", properties.getProperty("mail.smtp.socketFactory.fallback"));
		
		
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.username"), properties.getProperty("mail.password"));
			}
		};

		Session session = Session.getDefaultInstance(propertiesMail, auth);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(properties.getProperty("mail.username")));
		InternetAddress[] toAddresses = { new InternetAddress(indirizzoMail) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(soggettoMail);
		msg.setSentDate(new Date());
		msg.setText(testoMail);

		Transport.send(msg);

	}
}