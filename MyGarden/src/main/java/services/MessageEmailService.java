
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageEmailRepository;
import domain.Actor;
import domain.MessageEmail;

@Service
@Transactional
public class MessageEmailService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageEmailRepository	messageEmailRepository;

	@Autowired
	private ActorService			actorService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public MessageEmailService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public MessageEmail findOne(final int messageId) {
		Assert.isTrue(messageId != 0);
		MessageEmail messageEmail;

		messageEmail = this.messageEmailRepository.findOne(messageId);
		Assert.notNull(messageEmail);
		return messageEmail;
	}

	public Collection<MessageEmail> findAll() {
		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findAll();

		return result;
	}

	public MessageEmail create() {
		MessageEmail result;
		//final Chorbi chorbi;

		Calendar calendar;

		result = new MessageEmail();
		final Actor actor = this.actorService.findByPrincipal();

		Assert.notNull(actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setMoment(calendar.getTime());
		result.setSender(actor);
		result.setDeletedForRecipient(false);
		result.setDeletedForSender(false);
		result.setArchivedForSender(false);
		result.setArchivedForRecipient(false);

		return result;
	}

	public MessageEmail create(final Actor recipient) {
		MessageEmail result;

		Calendar calendar;

		result = new MessageEmail();

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setMoment(calendar.getTime());
		result.setSender(actor);
		result.setRecipient(recipient);
		result.setDeletedForRecipient(false);
		result.setDeletedForSender(false);
		result.setArchivedForSender(false);
		result.setArchivedForRecipient(false);
		return result;
	}

	public MessageEmail reply(final MessageEmail messageEmail) {
		Assert.notNull(messageEmail);
		Assert.isTrue(messageEmail.getRecipient().equals(this.actorService.findByPrincipal()));
		final MessageEmail result = this.create();
		result.setRecipient(messageEmail.getSender());
		result.setSubject(messageEmail.getSubject());
		return result;
	}

	public void delete(final MessageEmail messageEmail) {
		Assert.notNull(messageEmail);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue((messageEmail.getSender().equals(actor) && messageEmail.getDeletedForSender() == false) || (messageEmail.getRecipient().equals(actor) && messageEmail.getDeletedForRecipient() == false));

		if (messageEmail.getRecipient().equals(actor) && messageEmail.getDeletedForSender() == false) {
			messageEmail.setDeletedForRecipient(true);
			this.messageEmailRepository.save(messageEmail);
		} else if (messageEmail.getSender().equals(actor) && messageEmail.getDeletedForRecipient() == false) {
			messageEmail.setDeletedForSender(true);
			this.messageEmailRepository.save(messageEmail);
		} else if ((messageEmail.getSender().equals(actor) || messageEmail.getRecipient().equals(actor)) && messageEmail.getDeletedForRecipient() == false && messageEmail.getDeletedForSender() == false)
			this.messageEmailRepository.delete(messageEmail);
	}

	public MessageEmail save(MessageEmail messageEmail) {
		Assert.notNull(messageEmail);
		Assert.notNull(messageEmail.getId() != 0);

		messageEmail = this.messageEmailRepository.save(messageEmail);

		return messageEmail;
	}

	// Other business methods -------------------------------------------------

	public Collection<MessageEmail> findMessageEmailsSentByActorId(final int actorId) {

		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findMessagesSentByActorId(actorId);

		return result;

	}

	public Collection<MessageEmail> findMessageEmailsReceivedByActorId(final int actorId) {

		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findMessagesReceivedByActorId(actorId);

		return result;

	}

	public Collection<MessageEmail> findMessageEmailsArchivedAndSentByActorId(final int actorId) {

		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findMessagesArchivedAndSentByActorId(actorId);

		return result;

	}

	public Collection<MessageEmail> findMessageEmailsArchivedAndReceivedByActorId(final int actorId) {

		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findMessagesArchivedAndReceivedByActorId(actorId);

		return result;

	}

	public Collection<MessageEmail> findArchivedMessageEmailsByActorId(final int actorId) {
		Collection<MessageEmail> result;
		Collection<MessageEmail> aux;

		result = this.messageEmailRepository.findMessagesArchivedAndSentByActorId(actorId);
		aux = this.messageEmailRepository.findMessagesArchivedAndReceivedByActorId(actorId);
		result.addAll(aux);
		return result;
	}

	public void change(final MessageEmail messageEmail, final boolean archived) {
		Assert.notNull(messageEmail);
		Assert.isTrue((messageEmail.getSender().equals(this.actorService.findByPrincipal()) && messageEmail.getDeletedForSender() == false)
			|| (messageEmail.getRecipient().equals(this.actorService.findByPrincipal()) && messageEmail.getDeletedForRecipient() == false));

		if (messageEmail.getSender().equals(this.actorService.findByPrincipal()) && messageEmail.getDeletedForSender() == false) {
			messageEmail.setArchivedForSender(archived);
			this.messageEmailRepository.save(messageEmail);
		} else if (messageEmail.getRecipient().equals(this.actorService.findByPrincipal()) && messageEmail.getDeletedForRecipient() == false) {
			messageEmail.setArchivedForRecipient(archived);
			this.messageEmailRepository.save(messageEmail);
		}
	}

}
