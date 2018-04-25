
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MessageEmail extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public MessageEmail() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	subject;
	private String	text;
	private Date	moment;
	private Boolean	deletedForSender;
	private Boolean	deletedForRecipient;
	private Boolean	archivedForSender;
	private Boolean	archivedForRecipient;


	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public Boolean getDeletedForSender() {
		return this.deletedForSender;
	}

	public void setDeletedForSender(final Boolean deletedForSender) {
		this.deletedForSender = deletedForSender;
	}

	public Boolean getDeletedForRecipient() {
		return this.deletedForRecipient;
	}

	public void setDeletedForRecipient(final Boolean deletedForRecipient) {
		this.deletedForRecipient = deletedForRecipient;
	}

	public Boolean getArchivedForSender() {
		return this.archivedForSender;
	}

	public void setArchivedForSender(final Boolean archivedForSender) {
		this.archivedForSender = archivedForSender;
	}

	public Boolean getArchivedForRecipient() {
		return this.archivedForRecipient;
	}

	public void setArchivedForRecipient(final Boolean archivedForRecipient) {
		this.archivedForRecipient = archivedForRecipient;
	}


	// Relationships ----------------------------------------------------------
	private Actor	sender;
	private Actor	recipient;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

}
