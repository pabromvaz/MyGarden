
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GardenerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Gardener;
import domain.MessageEmail;
import domain.Taste;
import domain.WateringArea;
import forms.CreateGardenerForm;

@Service
@Transactional
public class GardenerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private GardenerRepository	gardenerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors------------------------------------------------------------
	public GardenerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Gardener findOne(final int gardenerId) {
		Gardener result;

		result = this.gardenerRepository.findOne(gardenerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Gardener> findAll() {
		Collection<Gardener> result;

		result = this.gardenerRepository.findAll();

		return result;
	}

	public Gardener create() {
		Gardener result;
		UserAccount userAccount;
		Authority authority;
		Collection<Taste> tastes;
		Collection<MessageEmail> sentMessages;
		Collection<MessageEmail> receivedMessages;
		Collection<Comment> comments;
		Collection<WateringArea> wateringAreas;

		userAccount = new UserAccount();
		authority = new Authority();
		tastes = new ArrayList<Taste>();
		sentMessages = new ArrayList<MessageEmail>();
		receivedMessages = new ArrayList<MessageEmail>();
		comments = new ArrayList<Comment>();
		wateringAreas = new ArrayList<WateringArea>();

		authority.setAuthority(Authority.GARDENER);
		userAccount.addAuthority(authority);

		result = new Gardener();
		result.setUserAccount(userAccount);
		result.setTastes(tastes);
		result.setComments(comments);
		result.setSentMessages(sentMessages);
		result.setReceivedMessages(receivedMessages);
		result.setWateringAreas(wateringAreas);
		result.setAnimalDetectionEventActivated(true);
		result.setUseOfFertilizerEventActivated(true);
		result.setWaterTankEventActivated(true);

		return result;
	}

	public Gardener save(final Gardener gardener) {
		Assert.notNull(gardener);

		Gardener result;

		result = this.gardenerRepository.save(gardener);
		return result;
	}

	public Gardener saveRegister(final Gardener gardener) {
		Assert.notNull(gardener);
		Gardener result;

		result = this.gardenerRepository.save(gardener);
		return result;
	}

	// Other business methods -------------------------------------------------
	public Gardener findByPrincipal() {
		Gardener result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Gardener findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Gardener result;

		result = this.gardenerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Gardener reconstructProfile(final CreateGardenerForm createGardenerForm, final String type) {
		Assert.notNull(createGardenerForm);
		Gardener gardener = null;
		Md5PasswordEncoder encoder;
		String password;

		Assert.isTrue(createGardenerForm.getPassword().equals(createGardenerForm.getConfirmPassword()));

		//Creo uno nuevo vacio para meterle los datos del formulario a dicho responsable del huerto
		if (type.equals("create")) {
			gardener = this.create();
			Assert.isTrue(createGardenerForm.getIsAgree());
		} else if (type.equals("edit"))
			gardener = this.findByPrincipal();

		password = createGardenerForm.getPassword();

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		gardener.getUserAccount().setUsername(createGardenerForm.getUsername());
		gardener.getUserAccount().setPassword(password);
		gardener.setName(createGardenerForm.getName());
		gardener.setSurname(createGardenerForm.getSurname());
		gardener.setEmail(createGardenerForm.getEmail());
		gardener.setPicture(createGardenerForm.getPicture());

		return gardener;
	}
	public CreateGardenerForm constructProfile(final Gardener gardener) {
		Assert.notNull(gardener);
		CreateGardenerForm createGardenerForm;

		createGardenerForm = new CreateGardenerForm();
		createGardenerForm.setUsername(gardener.getUserAccount().getUsername());
		createGardenerForm.setPassword(gardener.getUserAccount().getPassword());
		createGardenerForm.setName(gardener.getName());
		createGardenerForm.setSurname(gardener.getSurname());
		createGardenerForm.setEmail(gardener.getEmail());
		createGardenerForm.setPicture(gardener.getPicture());

		return createGardenerForm;
	}

}
