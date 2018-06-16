
package services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	private final Properties	properties	= new Properties();

	private final String		password	= "passwordDelCorreo";

	private Session				session;


	private void init() {

		this.properties.put("mail.smtp.host", "smtp.gmail.com");
		this.properties.put("mail.smtp.starttls.enable", "true");
		this.properties.put("mail.smtp.port", 587);
		this.properties.put("mail.smtp.mail.sender", "correo@gmail.com");
		this.properties.put("mail.smtp.user", "mygardenwebapplication");
		this.properties.put("mail.smtp.auth", "true");

		this.session = Session.getDefaultInstance(this.properties);
	}

	public void sendEmail(final String email, final String description) {

		this.init();
		try {
			final MimeMessage message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress((String) this.properties.get("mail.smtp.mail.sender")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Alerta de incidencia");
			message.setText(description + ".Este email esta generado de forma automática, no intente responderlo porque no será contestado.");
			final Transport t = this.session.getTransport("smtp");
			t.connect((String) this.properties.get("mail.smtp.user"), this.password);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		} catch (final MessagingException me) {

			return;
		}

	}

}
