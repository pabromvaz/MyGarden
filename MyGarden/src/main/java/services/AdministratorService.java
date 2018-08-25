
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.MessageEmail;
import forms.CreateAdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	public Administrator create() {
		Administrator result;
		UserAccount userAccount;
		Authority authority;
		Collection<MessageEmail> sentMessages;
		Collection<MessageEmail> receivedMessages;

		userAccount = new UserAccount();
		authority = new Authority();

		authority.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(authority);
		sentMessages = new ArrayList<MessageEmail>();
		receivedMessages = new ArrayList<MessageEmail>();

		result = new Administrator();
		result.setUserAccount(userAccount);
		result.setSentMessages(sentMessages);
		result.setReceivedMessages(receivedMessages);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(this.findByPrincipal());
		Assert.notNull(administrator);
		Administrator result;

		result = this.administratorRepository.save(administrator);
		return result;
	}
	// Other business methods -------------------------------------------------
	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Administrator reconstructProfile(final CreateAdministratorForm createAdministratorForm, final String type) {
		Assert.notNull(createAdministratorForm);
		Administrator administrator = null;
		Md5PasswordEncoder encoder;
		String password;

		Assert.isTrue(createAdministratorForm.getPassword().equals(createAdministratorForm.getConfirmPassword()));

		//Creo uno nuevo vacio para meterle los datos del formulario a dicho administrador
		if (type.equals("create"))
			administrator = this.create();
		else if (type.equals("edit"))
			administrator = this.findByPrincipal();

		password = createAdministratorForm.getPassword();

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		administrator.getUserAccount().setUsername(createAdministratorForm.getUsername());
		administrator.getUserAccount().setPassword(password);
		administrator.setName(createAdministratorForm.getName());
		administrator.setSurname(createAdministratorForm.getSurname());
		administrator.setEmail(createAdministratorForm.getEmail());

		return administrator;
	}
	public CreateAdministratorForm constructProfile(final Administrator administrator) {
		Assert.notNull(administrator);
		CreateAdministratorForm createAdministratorForm;

		createAdministratorForm = new CreateAdministratorForm();
		createAdministratorForm.setUsername(administrator.getUserAccount().getUsername());
		createAdministratorForm.setPassword(administrator.getUserAccount().getPassword());
		createAdministratorForm.setName(administrator.getName());
		createAdministratorForm.setSurname(administrator.getSurname());
		createAdministratorForm.setEmail(administrator.getEmail());

		return createAdministratorForm;
	}

}
