
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
	//-	Un actor autenticado como responsable del huerto debe ser capaz de:
	//	Ver las mediciones

	//	El test negativo se produce porque se intenta asignar una medición a una zona de riego sin 
	//  autenticarse el sistema arduino como el responsable del huerto propietario de ella 
	@Test
	public void driverAddMeasurementToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 20, 90, 12, 22, null
			}, {
				"gardener2", 22, 10, 10, 20, null
			}, {
				"gardener3", 23, 10, 10, 20, null
			}, {
				"gardenerNoExist", 20, 10, 10, 20, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AddMeasurementToAWateringArea((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Integer) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void AddMeasurementToAWateringArea(final String username, final Integer wateringAreaId, final Integer moisture, final Integer humidity, final Integer temperature, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			final Measurement measurement = this.measurementService.create(wateringArea, moisture, humidity, temperature);
			this.measurementService.save(measurement);

			this.measurementService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
