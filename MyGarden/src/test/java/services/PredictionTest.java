
package services;

import java.util.Calendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Prediction;
import domain.WateringArea;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PredictionTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PredictionService	predictionService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como cliente debe ser capaz de:
	//  Listar las predicciones
	//	Visualizar los datos de las predicciones.
	//  Borrar las predicciones

	//El primer test negativo es causado porque no nos hemos logueado correctamente (el responsable
	// del huerto no existe)
	@Test
	public void driverCreatePrediction() {
		final Object testingData[][] = {
			{
				"gardener1", "01/02/2018 19:00", 32.0, 20, null
			}, {
				"gardener2", "01/02/2018 19:00", 32.0, 21, null
			}, {
				"gardener3", "01/02/2018 19:00", 32.0, 23, null
			}, {
				"gardenerNoExist", "01/02/2018 19:00", 32.0, 17, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.CreatePrediction((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void CreatePrediction(final String username, final String moment, final Double precipitation, final Integer wateringAreaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		String[] date1 = null;
		String[] time1 = null;
		final Calendar calendar = Calendar.getInstance();
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);

			date1 = moment.split(" ");
			time1 = date1[1].split(":");
			date1 = date1[0].split("/");
			calendar.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]), Integer.parseInt(time1[0]), Integer.parseInt(time1[1]));

			final Prediction prediction = this.predictionService.create(wateringArea, calendar.getTime(), precipitation);

			this.predictionService.save(prediction);

			this.predictionService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//El primer test negativo es causado porque no nos hemos logueado correctamente (el responsable
	// del huerto no existe)

	@Test
	public void driverDeletePrediction() {
		final Object testingData[][] = {
			{
				"gardener1", 54, null
			}, {
				"gardener2", 55, null
			}, {
				"gardenerNoExist", 55, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeletePrediction((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeletePrediction(final String username, final Integer predictionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Prediction prediction = this.predictionService.findOne(predictionId);

			this.predictionService.delete(prediction);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
