
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Gardener;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GardenerTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private GardenerService	gardenerService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	// Crear un administrador

	//Registrarse como responsable del huerto
	@Test
	public void driverRegisterGardener() {
		final Object testingData[][] = {
			{
				"gardener5", "gardener5", "gardener5", "nameGardener5", "surnameGardener5", "gardener5@gmail.com", "http://web.com/imagen.jpg", true, true, true, null
			}, {
				"gardener5", "gardener5", "gardener4", "nameGardener5", "surnameGardener5", "gardener5@gmail.com", "http://web.com/imagen.jpg", true, true, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.RegisterGardener((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Boolean) testingData[i][7], (Boolean) testingData[i][8], (Boolean) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void RegisterGardener(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String picture, final Boolean animalDetectionEventActivated,
		final Boolean useOfFertilizerEventActivated, final Boolean waterTankEventActivated, final Class<?> expected) {

		Class<?> caught = null;

		Md5PasswordEncoder encoder;
		String passwordEncoded;

		try {
			final Gardener gardener = this.gardenerService.create();
			gardener.getUserAccount().setUsername(username);

			Assert.isTrue(password.equals(confirmPassword));
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			gardener.getUserAccount().setPassword(passwordEncoded);

			gardener.setName(name);
			gardener.setSurname(surname);
			gardener.setEmail(email);
			gardener.setPicture(picture);
			gardener.setAnimalDetectionEventActivated(animalDetectionEventActivated);
			gardener.setWaterTankEventActivated(waterTankEventActivated);
			gardener.setUseOfFertilizerEventActivated(useOfFertilizerEventActivated);

			this.gardenerService.save(gardener);
			this.gardenerService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Editar el perfil de un responsable del huerto
	@Test
	public void driverEditGardener() {
		final Object testingData[][] = {
			{
				"gardener1", "gardener1", "gardener1", "nameGardener1", "surnameGardener1", "gardener1@gmail.com", "http://web.com/imagen.jpg", true, true, true, null
			}, {
				"gardener1", "gardener1", "gardener4", "nameGardener1", "surnameGardener1", "gardener1@gmail.com", "http://web.com/imagen.jpg", true, true, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editGardener((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Boolean) testingData[i][7],
				(Boolean) testingData[i][8], (Boolean) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void editGardener(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String picture, final Boolean animalDetectionEventActivated,
		final Boolean useOfFertilizerEventActivated, final Boolean waterTankEventActivated, final Class<?> expected) {

		Class<?> caught = null;
		Md5PasswordEncoder encoder;
		String passwordEncoded;

		try {
			this.authenticate("gardener1");

			final Gardener gardener = this.gardenerService.findByPrincipal();

			Assert.isTrue(password.equals(confirmPassword));
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			gardener.getUserAccount().setPassword(passwordEncoded);

			gardener.setName(name);
			gardener.setSurname(surname);
			gardener.setEmail(email);
			gardener.setPicture(picture);
			gardener.setAnimalDetectionEventActivated(animalDetectionEventActivated);
			gardener.setWaterTankEventActivated(waterTankEventActivated);
			gardener.setUseOfFertilizerEventActivated(useOfFertilizerEventActivated);

			this.gardenerService.save(gardener);
			this.gardenerService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
