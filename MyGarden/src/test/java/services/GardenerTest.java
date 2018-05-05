
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Gardener;
import forms.CreateGardenerForm;

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
				"gardener5", "gardener5", "gardener5", "nameGardener5", "surnameGardener5", "gardener5@gmail.com", "http://web.com/imagen.jpg", true, null
			}, {
				"gardener6", "gardener7", "gardener6", "nameGardener6", "surnameGardener6", "gardener6@gmail.com", "http://web.com/imagen.jpg", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.RegisterGardener((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void RegisterGardener(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String picture, final Boolean isAgree, final Class<?> expected) {

		Class<?> caught = null;

		try {

			final CreateGardenerForm createGardenerForm = new CreateGardenerForm();

			createGardenerForm.setUsername(username);
			createGardenerForm.setPassword(password);
			createGardenerForm.setConfirmPassword(confirmPassword);
			createGardenerForm.setIsAgree(true);

			createGardenerForm.setName(name);
			createGardenerForm.setSurname(surname);
			createGardenerForm.setEmail(email);
			createGardenerForm.setPicture(picture);
			final Gardener gardener = this.gardenerService.reconstructProfile(createGardenerForm, "create");

			this.gardenerService.saveRegister(gardener);
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
				"gardener1", "gardener1", "gardener1", "nameGardener1", "surnameGardener1", "gardener1@gmail.com", "http://web.com/imagen.jpg", null
			}, {
				"gardener1", "gardener1", "gardener4", "nameGardener1", "surnameGardener1", "gardener1@gmail.com", "http://web.com/imagen.jpg", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editGardener((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);
	}

	protected void editGardener(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String picture, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate("gardener1");

			Gardener gardener = this.gardenerService.findByPrincipal();
			final CreateGardenerForm createGardenerForm = this.gardenerService.constructProfile(gardener);

			createGardenerForm.setUsername(username);
			createGardenerForm.setPassword(password);
			createGardenerForm.setConfirmPassword(confirmPassword);

			createGardenerForm.setName(name);
			createGardenerForm.setSurname(surname);
			createGardenerForm.setEmail(email);

			gardener = this.gardenerService.reconstructProfile(createGardenerForm, "edit");

			this.gardenerService.save(gardener);
			this.gardenerService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
