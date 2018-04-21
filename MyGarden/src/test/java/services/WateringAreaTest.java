
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Plant;
import domain.WateringArea;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WateringAreaTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private PlantService		plantService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como developer debe ser capaz de:
	//	Crear, editar y borrar juegos.

	//El primer test negativo es causado porque no nos hemos logueado developer, el segundo de
	//ellos se produce porque introducimos un campo vacio (descripcion) y el tercero es provocado porque le
	//pasamos un id de categoria que no existe.

	@Test
	public void driverCreateWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", "Name1", "Place1", "Description1", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, null
			}, {
				"gardener2", "Name2", "Place2", "Description2", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, null
			}, {
				"gardener3", "Name3", "Place3", "Description3", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, null
			}, {
				"admin", "Name4", "Place1", "Descripcion4", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, IllegalArgumentException.class
			}, {
				"gardener1", "Name5", "Place4", "", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createWateringArea((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Integer) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void createWateringArea(final String username, final String name, final String place, final String description, final String picture, final Integer plantId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			WateringArea wateringArea;
			final WateringArea auxWateringArea;

			wateringArea = this.wateringAreaService.create();
			wateringArea.setName(name);
			wateringArea.setPlace(place);
			wateringArea.setDescription(description);
			final Collection<String> pictures = new ArrayList<String>();
			pictures.add(picture);
			wateringArea.setPictures(pictures);

			final Plant plant = this.plantService.findOne(plantId);
			//categories.add(category);
			//aux = this.gameService.save(game);
			//this.categoryService.select(categories, aux);
			wateringArea.setPlant(plant);
			this.wateringAreaService.save(wateringArea);

			this.wateringAreaService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", "Name1", "Place1", "Description1", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 17, null
			}, {
				"gardener2", "Name2", "Place2", "Description2", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 19, null
			}, {
				"gardener3", "Name3", "Place3", "Description3", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 20, null
			}, {
				"admin", "Name4", "Place1", "Descripcion4", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 17, IllegalArgumentException.class
			}, {
				"gardener1", "Name5", "Place4", "", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 17, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editWateringArea((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	protected void editWateringArea(final String username, final String name, final String place, final String description, final String picture, final Integer plantId, final Integer wateringAreaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			WateringArea wateringArea;
			final WateringArea auxWateringArea;

			wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			wateringArea.setName(name);
			wateringArea.setPlace(place);
			wateringArea.setDescription(description);
			final Collection<String> pictures = new ArrayList<String>();
			pictures.add(picture);
			wateringArea.setPictures(pictures);

			final Plant plant = this.plantService.findOne(plantId);
			//categories.add(category);
			//aux = this.gameService.save(game);
			//this.categoryService.select(categories, aux);
			wateringArea.setPlant(plant);
			this.wateringAreaService.save(wateringArea);

			this.wateringAreaService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverDeleteWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 17, null
			}, {
				"gardener2", 19, null
			}, {
				"gardener3", 20, null
			}, {
				"admin", 17, IllegalArgumentException.class
			}, {
				"gardener1", 17, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteWateringArea((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteWateringArea(final String username, final Integer wateringAreaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);

			this.wateringAreaService.delete(wateringArea);

			this.wateringAreaService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
