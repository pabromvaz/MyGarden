
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Plant;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PlantTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

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
	public void driverCreatePlant() {
		final Object testingData[][] = {
			{
				"admin", "plantName", "Description1", 7.0, 20.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", "plantName", "Description2", 7.0, 20.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", "plantName", "Description3", 7.0, 20.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"adminNoExist", "plantName", "Description4", 7.0, 20.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreatePlant((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}

	protected void CreatePlant(final String username, final String name, final String description, final Double minTemperature, final Double maxTemperature, final Double moisture, final Double pH, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Plant plant = this.plantService.create();
			plant.setName(name);
			plant.setPicture(picture);
			plant.setDescription(description);
			plant.setPh(pH);
			plant.setMoisture(moisture);
			plant.setMinTemperature(minTemperature);
			plant.setMaxTemperature(maxTemperature);
			this.plantService.save(plant);

			this.plantService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditPlant() {
		final Object testingData[][] = {
			{
				"admin", 14, "plantName1", "Description1", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", 15, "plantName1", "Description2", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"adminNoExist", 16, "plantName1", "Description4", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditPlant((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void EditPlant(final String username, final Integer plantId, final String name, final String description, final Double minTemperature, final Double maxTemperature, final Double moisture, final Double pH, final String picture,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Plant plant = this.plantService.findOne(plantId);
			plant.setName(name);
			plant.setPicture(picture);
			plant.setDescription(description);
			plant.setPh(pH);
			plant.setMinTemperature(minTemperature);
			plant.setMaxTemperature(maxTemperature);
			plant.setMoisture(moisture);
			this.plantService.save(plant);

			this.plantService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverDeletePlant() {
		final Object testingData[][] = {
			{
				"admin", 14, null
			}, {
				"admin", 15, null
			}, {
				"adminNoExist", 15, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeletePlant((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeletePlant(final String username, final Integer plantId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Plant plant = this.plantService.findOne(plantId);

			this.plantService.delete(plant);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
