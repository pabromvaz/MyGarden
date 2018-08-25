
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.MessageEmail;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageEmailTest extends AbstractTest {

	@Autowired
	private MessageEmailService	messageEmailService;

	@Autowired
	private ActorService		actorService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	//Enviar mensajes a otro actor.
	//Listar los mensajes que ha recibido y reponderlos.
	//Listar los mensajes que ha enviado y reenviarlos.
	//Borrar los mensahes que ha recibido o enviado.

	//En este primer driver se comprueba que un actor pueda enviar un mensaje a otro actor

	@Test
	public void driverSendMesage() {
		final Object testingData[][] = {
			{
				"gardener1", "Envio1", "text1", this.actorService.findOne(9), null
			}, {
				"gardener1", "", "text1", this.actorService.findOne(9), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.SendMesage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Actor) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void SendMesage(final String sender, final String subject, final String text, final Actor recipient, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final MessageEmail messageEmail = this.messageEmailService.create(recipient);

			messageEmail.setSubject(subject);
			messageEmail.setText(text);

			this.messageEmailService.save(messageEmail);

			this.messageEmailService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un actor puede responder a un determinado mensaje.

	@Test
	public void driverRequestMessage() {
		final Object testingData[][] = {
			{
				"gardener1", "Envio1", "text1", 64, null
			}, {
				"gardener1", "", "text1", 64, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.RequestMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void RequestMessage(final String sender, final String subject, final String text, final int message, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final MessageEmail aux1 = this.messageEmailService.findOne(message);
			final MessageEmail aux2 = this.messageEmailService.reply(aux1);

			aux2.setSubject(subject);
			aux2.setText(text);

			this.messageEmailService.save(aux2);

			this.messageEmailService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un actor puede archivar un determinado mensaje.
	// El test negativo se produce porque se intenta archivar un mensaje que no pertenece al usuario
	@Test
	public void driverFileMessage() {
		final Object testingData[][] = {
			{
				"gardener1", 64, null
			}, {
				"gardener1", 65, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.FileMessage((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void FileMessage(final String sender, final int message, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final MessageEmail aux1 = this.messageEmailService.findOne(message);
			this.messageEmailService.change(aux1, true);

			this.messageEmailService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un actor puede borrar un mensaje suyo.

	@Test
	public void driverBorrarMensaje() {
		final Object testingData[][] = {
			{
				"gardener1", 64, null
			}, {
				"gardener1", 65, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.borrarMensaje((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void borrarMensaje(final String user, final int message, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final MessageEmail aux = this.messageEmailService.findOne(message);
			this.messageEmailService.delete(aux);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
