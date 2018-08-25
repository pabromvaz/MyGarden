
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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
	//-	Un actor autenticado como administrador debe ser capaz de:
	//	Añadir fertilizantes
	//  Editar fetilizantes
	//  Borrar fertilizantes

	//- Un actor autenticado debe ser capaz de:
	// Listar fertilizantes
	// Ver fertilizantes

	//El primer test negativo es causado porque el administrador no existe
	@Test
	public void driverCreateFertilizer() {
		final Object testingData[][] = {
			{
				"admin", "fertilizerName1", "Description1", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", "fertilizerName1", "Description2", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"admin", "fertilizerName1", "Description3", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", null
			}, {
				"adminNoExist", "fertilizerName1", "Description4", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"gardener1", "fertilizerName1", "Description4", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"admin", " ", "Description4", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateFertilizer((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	protected void CreateFertilizer(final String username, final String name, final String description, final Double nitrogen, final Double phosphorus, final Double potassium, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Fertilizer fertilizer = this.fertilizerService.create();
			fertilizer.setName(name);
			fertilizer.setPicture(picture);
			fertilizer.setDescription(description);
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

	// Los dos primeros tests negativos se deben a que no se puede editar un fertilizante que tiene plantas asignadas
	//El tercer test negativo es causado porque el administrador no existe
	@Test
	public void driverEditFertilizer() {
		final Object testingData[][] = {
			{
				"admin", 14, "fertilizerName1", "Description1", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"admin", 15, "fertilizerName1", "Description2", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"adminNoExist", 15, "fertilizerName1", "Description4", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"gardener1", 15, "fertilizerName1", "Description4", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}, {
				"admin", 15, "", "Description4", 20.0, 20.0, 20.0, "https://www.como-podar.com/wp-content/uploads/2017/04/narango-2.jpg", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditFertilizer((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void EditFertilizer(final String username, final Integer fertilizerId, final String name, final String description, final Double nitrogen, final Double phosphorus, final Double potassium, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Fertilizer fertilizer = this.fertilizerService.findOne(fertilizerId);
			fertilizer.setName(name);
			fertilizer.setPicture(picture);
			fertilizer.setDescription(description);
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

	// Los tests negativos se deben a que no se puede eliminar un fertilizante que tiene plantas asignadas
	// y que el administrador no existe (segundo caso)
	@Test
	public void driverDeleteFertilizer() {
		final Object testingData[][] = {
			{
				"admin", 14, IllegalArgumentException.class
			}, {
				"adminNoExist", 15, IllegalArgumentException.class
			}, {
				"gardener1", 15, IllegalArgumentException.class
			}, {
				"admin", 2100, IllegalArgumentException.class
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
