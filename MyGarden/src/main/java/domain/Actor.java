
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Actor() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}


	// Relationships ----------------------------------------------------------
	private UserAccount					userAccount;
	private Collection<MessageEmail>	sentMessages;
	private Collection<MessageEmail>	receivedMessages;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<MessageEmail> getSentMessages() {
		return this.sentMessages;
	}
	public void setSentMessages(final Collection<MessageEmail> sentMessages) {
		this.sentMessages = sentMessages;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipient")
	public Collection<MessageEmail> getReceivedMessages() {
		return this.receivedMessages;
	}
	public void setReceivedMessages(final Collection<MessageEmail> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String maskEmail(final String string) {

		final String masked = string.replaceAll("[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]", "***");

		return masked;
	}
}
