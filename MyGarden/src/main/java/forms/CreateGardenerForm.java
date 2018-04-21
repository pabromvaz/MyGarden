
package forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

public class CreateGardenerForm {

	//Solo meto los datos que quiero que el usuario tenga que rellenar
	//Las relaciones tambien se pueden meter
	//Cada atributo tendra su get y set con las mismas restricciones que en el dominio
	//Con SafeHtml es para seguridad que tambien se le puede meter en el dominio

	private String	username;
	private String	password;
	private String	confirmPassword;
	private String	name;
	private String	surname;
	private String	email;
	private String	picture;
	private Boolean	isAgree;


	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Boolean getIsAgree() {
		return this.isAgree;
	}
	public void setIsAgree(final Boolean isAgree) {
		this.isAgree = isAgree;
	}

}
