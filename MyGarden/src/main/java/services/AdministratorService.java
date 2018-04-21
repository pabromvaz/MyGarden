
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.MessageEmail;

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

}
