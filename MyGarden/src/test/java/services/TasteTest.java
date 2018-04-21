
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
	//-	Un actor autenticado como cliente debe ser capaz de:
	//	Darle "me gusta/no me gusta" a los juegos.

	//El primer caso negativos se produce porque intentamos darle like a un juego sin
	//estar logueados, en el segundo nos logueamos como un developer que tampoco puede darle
	//like a un juego y por ultimo introducimos un id que no pertenece a ningun juego
	@Test
	public void driverLikeToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 19, null
			}, {
				"gardener2", 17, null
			}, {
				"gardener3", 18, null
			}, {
				"gardenerNoExist", 20, IllegalArgumentException.class
			}, {
				"admin", 20, IllegalArgumentException.class
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

	//El primer caso negativos se produce porque intentamos darle dislike a un juego sin
	//estar logueados, en el segundo nos logueamos como un developer que tampoco puede darle
	//dislike a un juego y por ultimo introducimos un id que no pertenece a ningun juego
	@Test
	public void driverDislikeToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 20, null
			}, {
				"gardener2", 17, null
			}, {
				"gardener3", 19, null
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

	@Test
	public void driverCambiarLikeODislike() {
		final Object testingData[][] = {
			{
				"gardener1", 21, false, null
			}, {
				"gardener1", 22, true, null
			}, {
				"gardenerNoExist", 21, false, IllegalArgumentException.class
			}, {
				"admin", 22, true, IllegalArgumentException.class
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
