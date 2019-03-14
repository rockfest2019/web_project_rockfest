package com.semernik.rockfest.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



// TODO: Auto-generated Javadoc
/**
 * The Class EmailUtil.
 */
public class EmailUtil {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static EmailUtil instance = new EmailUtil();

	/** The Constant PATH. */
	private final static String PATH = "/email.properties";

	/** The Constant MAIL_FROM_KEY. */
	private final static String MAIL_FROM_KEY = "mail.from";

	/** The Constant USERNAME_KEY. */
	private final static String USERNAME_KEY = "username";

	/** The Constant PASSWORD_KEY. */
	private final static String PASSWORD_KEY = "password";

	/** The Constant PASSWORD_CHANGED. */
	private final static String PASSWORD_CHANGED = "password changed";

	/** The Constant USER. */
	private final static String USER = "user: ";

	/** The Constant NEW_PASSWORD. */
	private final static String NEW_PASSWORD = ". New password:";

	/** The Constant PASSWORD. */
	private final static String PASSWORD = ". Password:";

	/** The Constant PASSWORD_MESSAGE. */
	private final static String PASSWORD_MESSAGE = "Password message";

	/** The Constant REGISTRATION_MESSAGE. */
	private final static String REGISTRATION_MESSAGE = "Registration passed";

	/** The Constant REGISTRATION_MESSAGE_BODY. */
	private final static String REGISTRATION_MESSAGE_BODY = "You are registered at RockFest. Your login: ";

	/** The properties. */
	private Properties properties;

	/** The authenticator. */
	private Authenticator authenticator;

	/**
	 * Gets the single instance of EmailUtil.
	 *
	 * @return single instance of EmailUtil
	 */
	public static EmailUtil getInstance() {
		return instance;
	}

	/**
	 * Instantiates a new email util.
	 */
	private EmailUtil (){
		properties = new Properties();
		try {
			ClassLoader loader = EmailUtil.class.getClassLoader();
			InputStream stream = EmailUtil.class.getResourceAsStream(PATH);
			properties.load(stream);
			authenticator = new Authenticator() {
		        private PasswordAuthentication pa = new PasswordAuthentication(properties.getProperty(USERNAME_KEY),
		        		properties.getProperty(PASSWORD_KEY));
		        @Override
		        public PasswordAuthentication getPasswordAuthentication() {
		            return pa;
		        }
		    };
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send email.
	 *
	 * @param emailTo the email to
	 * @param subject the subject
	 * @param body the body
	 * @return true, if successful
	 */
	public boolean sendEmail(String emailTo, String subject, String body){
		boolean send = true;
		Session session = Session.getInstance(properties, authenticator);
		MimeMessage message = new MimeMessage(session);
		try {
		    message.setFrom(new InternetAddress(session.getProperties().getProperty(MAIL_FROM_KEY)));
		    InternetAddress[] address = {new InternetAddress(emailTo)};
		    message.setRecipients(Message.RecipientType.TO, address);
		    message.setSubject(subject);
		    message.setSentDate(new Date());
		    message.setText(body);
		    Transport.send(message);
		} catch (MessagingException e) {
			send = false;
		   logger.error("Failed to send email", e);
		}
		return send;
	}

	/**
	 * Send new password message.
	 *
	 * @param email the email
	 * @param login the login
	 * @param newPassword the new password
	 * @return true, if successful
	 */
	public boolean sendNewPasswordMessage(String email, String login, String newPassword) {
		String body = USER + login + NEW_PASSWORD + newPassword;
		return sendEmail(email, PASSWORD_MESSAGE, body);
	}

	/**
	 * Send password message.
	 *
	 * @param email the email
	 * @param login the login
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean sendPasswordMessage(String email, String login, String password) {
		String body = USER + login + PASSWORD + password;
		return sendEmail(email, PASSWORD_MESSAGE, body);

	}

	/**
	 * Send registration success message.
	 *
	 * @param email the email
	 * @param login the login
	 * @return true, if successful
	 */
	public boolean sendRegistrationSuccessMessage(String email, String login) {
		String body = REGISTRATION_MESSAGE_BODY + login;
		return sendEmail(email, REGISTRATION_MESSAGE, body);

	}

}
