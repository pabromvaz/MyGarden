
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Fertilizer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FertilizerTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FertilizerService		fertilizerService;

	@Autowired
	private PlantService			plantService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como cliente debe ser capaz de:
	//	Añadir comentarios a los juegos.

	//El primer test negativo es causado porque no nos hemos logueado correctamente como customer, el segundo de
	//ellos se produce porque le ponemos un score fuera del rango 0-10 y el tercero es provocado porque le
	//pasamos un id de game que no existe.
	@Test
	public void driverCreateFertilizer() {
		final Object testingData[][] = {
			{
				"admin", "fertilizerName1", "Description1", 7.0, 20.0, 20.0, 20.0, null
			}, {
				"admin", "fertilizerName1", "Description2", 7.0, 20.0, 20.0, 20.0, null
			}, {
				"admin", "fertilizerName1", "Description3", 7.0, 20.0, 20.0, 20.0, null
			}, {
				"adminNoExist", "fertilizerName1", "Description4", 7.0, 20.0, 20.0, 20.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateFertilizer((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	protected void CreateFertilizer(final String username, final String name, final String description, final Double pH, final Double nitrogen, final Double phosphorus, final Double potassium, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Fertilizer fertilizer = this.fertilizerService.create();
			fertilizer.setName(name);
			fertilizer.setDescription(description);
			fertilizer.setPH(pH);
			fertilizer.setNitrogen(nitrogen);
			fertilizer.setPhosphorus(phosphorus);
			fertilizer.setPotassium(potassium);
			this.fertilizerService.save(fertilizer);

			this.fertilizerService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditFertilizer() {
		final Object testingData[][] = {
			{
				"admin", 15, "fertilizerName1", "Description1", 7.0, 20.0, 20.0, 20.0, null
			}, {
				"admin", 16, "fertilizerName1", "Description2", 7.0, 20.0, 20.0, 20.0, null
			}, {
				"adminNoExist", 16, "fertilizerName1", "Description4", 7.0, 20.0, 20.0, 20.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditFertilizer((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6],
				(Double) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void EditFertilizer(final String username, final Integer fertilizerId, final String name, final String description, final Double pH, final Double nitrogen, final Double phosphorus, final Double potassium, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Fertilizer fertilizer = this.fertilizerService.findOne(fertilizerId);
			fertilizer.setName(name);
			fertilizer.setDescription(description);
			fertilizer.setPH(pH);
			fertilizer.setNitrogen(nitrogen);
			fertilizer.setPhosphorus(phosphorus);
			fertilizer.setPotassium(potassium);
			this.fertilizerService.save(fertilizer);

			this.fertilizerService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverDeleteFertilizer() {
		final Object testingData[][] = {
			{
				"admin", 15, null
			}, {
				"admin", 16, null
			}, {
				"adminNoExist", 15, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteFertilizer((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeleteFertilizer(final String username, final Integer fertilizerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Fertilizer fertilizer = this.fertilizerService.findOne(fertilizerId);

			this.fertilizerService.delete(fertilizer);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
