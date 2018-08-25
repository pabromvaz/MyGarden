
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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
	//-	Un actor autenticado debe ser capaz de:
	//	Listar las plantas del sistema
	//  Ver información de la planta 

	//- Un actor autenticado como administrador debe ser capaz de:
	//  Crear una planta
	//  Editar una planta
	//  Borrar un planta

	//El test negativo es causado porque no nos hemos logueado correctamente como administrador
	@Test
	public void driverCreatePlant() {
		final Object testingData[][] = {
			{
				"admin", "plantName", "Description1", 7.0, 20.0, 20.0, 7.0, 30.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", "plantName", "Description2", 7.0, 20.0, 20.0, 7.0, 21.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", "plantName", "Description3", 7.0, 20.0, 20.0, 7.0, 12.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"adminNoExist", "plantName", "Description4", 7.0, 20.0, 20.0, 7.0, 60.3, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"admin", "", "Description4", 7.0, 20.0, 20.0, 7.0, 60.3, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", ConstraintViolationException.class
			}, {
				"gardener1", "plantName", "Description4", 7.0, 20.0, 20.0, 7.0, 60.3, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreatePlant((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void CreatePlant(final String username, final String name, final String description, final Double minTemperature, final Double maxTemperature, final Double moisture, final Double pH, final Double humidity, final String picture,
		final Class<?> expected) {
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
			plant.setHumidity(humidity);
			this.plantService.save(plant);

			this.plantService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//El test negativo es causado porque no nos hemos logueado correctamente como administrador
	@Test
	public void driverEditPlant() {
		final Object testingData[][] = {
			{
				"admin", 16, "plantName1", "Description1", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", 17, "plantName1", "Description2", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"adminNoExist", 16, "plantName1", "Description4", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"admin", 16, "", "Description4", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", ConstraintViolationException.class
			}, {
				"gardener1", 16, "plantName1", "Description4", 20.0, 25.0, 20.0, 7.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
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

	//El test negativo es causado porque no nos hemos logueado correctamente como administrador y porque
	// las plantas tienen asignadas zonas de riego

	@Test
	public void driverDeletePlant() {
		final Object testingData[][] = {
			{
				"admin", 19, IllegalArgumentException.class
			}, {
				"admin", 18, IllegalArgumentException.class
			}, {
				"adminNoExist", 19, IllegalArgumentException.class
			}, {
				"gardener1", 19, IllegalArgumentException.class
			}, {
				"admin", 2134, IllegalArgumentException.class
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
