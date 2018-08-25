
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Taste;
import domain.WateringArea;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TasteTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private TasteService		tasteService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como responsable del huerto debe ser capaz de:
	//	Darle "me gusta/no me gusta" a las zonas de riego.

	//El primer test negativo se produce porque el responsable del huerto no existe
	//El segundo test negativo se produce porque es un administrador el que intenta dar un me gusta/no me gusta
	//El tercer test negativo se produce porque la zona de riego no existe
	@Test
	public void driverLikeToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 22, null
			}, {
				"gardener2", 20, null
			}, {
				"gardener3", 21, null
			}, {
				"gardenerNoExist", 23, IllegalArgumentException.class
			}, {
				"admin", 23, IllegalArgumentException.class
			}, {
				"gardener3", 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.likeToAWateringArea((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void likeToAWateringArea(final String username, final int wateringAreaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			this.tasteService.createDislike(wateringArea);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//El primer test negativo se produce porque el responsable del huerto no existe
	//El segundo test negativo se produce porque es un administrador el que intenta dar un me gusta/no me gusta
	//El tercer test negativo se produce porque la zona de riego no existe
	@Test
	public void driverDislikeToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 23, null
			}, {
				"gardener2", 20, null
			}, {
				"gardener3", 22, null
			}, {
				"gardenerNoExist", 94, IllegalArgumentException.class
			}, {
				"admin", 94, IllegalArgumentException.class
			}, {
				"gardener3", 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dislikeToAWateringArea((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void dislikeToAWateringArea(final String username, final int wateringAreaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			this.tasteService.createDislike(wateringArea);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//El primer test negativo se produce porque el responsable del huerto no existe
	//El segundo test negativo se produce porque es un administrador el que intenta cambiar algo que no tiene
	//El tercer test negativo se produce porque no existe el "taste"
	@Test
	public void driverCambiarLikeODislike() {
		final Object testingData[][] = {
			{
				"gardener1", 47, false, null
			}, {
				"gardener1", 48, true, null
			}, {
				"gardenerNoExist", 24, false, IllegalArgumentException.class
			}, {
				"admin", 25, true, IllegalArgumentException.class
			}, {
				"gardener2", 0, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.cambiarLikeODislike((String) testingData[i][0], (int) testingData[i][1], (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void cambiarLikeODislike(final String username, final int tasteId, final Boolean likeOrDislike, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Taste taste = this.tasteService.findOne(tasteId);
			this.tasteService.change(taste, likeOrDislike);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
