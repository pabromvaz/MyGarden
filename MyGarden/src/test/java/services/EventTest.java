
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Event;
import domain.WateringArea;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EventTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private EventService		eventService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como gardener debe ser capaz de:
	//	Ver las incidencias de sus zonas de riego.
	//  Listar las incidencias

	//El primer test negativo es causado porque no nos hemos logueado correctamente como customer, el segundo de
	//ellos se produce porque le ponemos un score fuera del rango 0-10 y el tercero es provocado porque le
	//pasamos un id de game que no existe.
	@Test
	public void driverAddEventToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 20, "eventName1", "Description1", "Intrusion", null
			}, {
				"gardener2", 22, "eventName2", "Description2", "Fertilizer", null
			}, {
				"gardener3", 23, "eventName3", "Description3", "Tank", null
			}, {
				"gardenerNoExist", 20, "eventName4", "Description4", "Intrusion", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AddEventToAWateringArea((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void AddEventToAWateringArea(final String username, final int wateringAreaId, final String name, final String description, final String type, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			final Event event = this.eventService.create(wateringArea, name, description, type);
			this.eventService.save(event);

			this.eventService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
