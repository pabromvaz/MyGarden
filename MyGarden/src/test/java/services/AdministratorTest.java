
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
import domain.Administrator;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	// Crear y editar un administrador

	//Crear administrador
	//El test negativo es porque la contraseña y su confirmación son distintas
	@Test
	public void driverCreateAdministrator() {
		final Object testingData[][] = {
			{
				"admin3", "admin3", "admin3", "nameAdmin3", "surnameAdmin3", "admin3@gmail.com", null
			}, {
				"admin3", "admin3", "admin4", "nameAdmin3", "surnameAdmin3", "admin3@gmail.com", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateAdministrator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void CreateAdministrator(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final Class<?> expected) {

		Class<?> caught = null;

		Md5PasswordEncoder encoder;
		String passwordEncoded;
		this.authenticate("admin");
		try {
			Administrator administrator = null;

			administrator = this.administratorService.create();
			administrator.getUserAccount().setUsername(username);

			Assert.isTrue(password.equals(confirmPassword));
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			administrator.getUserAccount().setPassword(passwordEncoded);

			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setEmail(email);

			this.administratorService.save(administrator);
			this.administratorService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	//Editar el perfil de un administrador
	//El test negativo es porque la contraseña y su confirmación son distintas
	@Test
	public void driverEditAdministrator() {
		final Object testingData[][] = {
			{
				"admin1", "admin1", "admin1", "nameAdmin1", "surnameAdmin1", "admin1@gmail.com", null
			}, {
				"admin1", "admin1", "admin4", "nameAdmin1", "surnameAdmin1", "admin31gmail.com", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editAdministrator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void editAdministrator(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final Class<?> expected) {

		Class<?> caught = null;
		Md5PasswordEncoder encoder;
		String passwordEncoded;
		this.authenticate("admin");
		try {

			final Administrator administrator = this.administratorService.findByPrincipal();

			Assert.isTrue(password.equals(confirmPassword));
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			administrator.getUserAccount().setPassword(passwordEncoded);

			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setEmail(email);

			this.administratorService.save(administrator);
			this.administratorService.findAll();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

}
