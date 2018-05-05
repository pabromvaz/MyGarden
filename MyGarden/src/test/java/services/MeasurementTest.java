
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Measurement;
import domain.WateringArea;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MeasurementTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private MeasurementService	measurementService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como cliente debe ser capaz de:
	//	Añadir comentarios a los juegos.

	//El primer test negativo es causado porque no nos hemos logueado correctamente como customer, el segundo de
	//ellos se produce porque le ponemos un score fuera del rango 0-10 y el tercero es provocado porque le
	//pasamos un id de game que no existe.
	@Test
	public void driverAddEventToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 20, 10.0, 10.0, 20.0, 40.0, 7.0, 20.0, 20.0, 20.0, null
			}, {
				"gardener2", 22, 10.0, 10.0, 20.0, 40.0, 7.0, 20.0, 20.0, 20.0, null
			}, {
				"gardener3", 23, 10.0, 10.0, 20.0, 40.0, 7.0, 20.0, 20.0, 20.0, null
			}, {
				"gardenerNoExist", 20, 10.0, 10.0, 20.0, 40.0, 7.0, 20.0, 20.0, 20.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AddEventToAWateringArea((String) testingData[i][0], (Integer) testingData[i][1], (Double) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6],
				(Double) testingData[i][7], (Double) testingData[i][8], (Double) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void AddEventToAWateringArea(final String username, final Integer wateringAreaId, final Double moisture, final Double humidity, final Double temperature, final Double light, final Double pH, final Double nitrogen, final Double phosphorus,
		final Double potassium, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			final Measurement event = this.measurementService.create(wateringArea, moisture, humidity, temperature, light, pH, nitrogen, phosphorus, potassium);
			this.measurementService.save(event);

			this.measurementService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
