
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
	//  Borrar las incidencias

	//Crear evento por parte del sistema. El primer test negativo es causado porque no existe el usuario del huerto.
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

	//Listar incidencias de un responsable del huerto. El primer test negativo es causado porque no existe el usuario del huerto.
	@Test
	public void listEvents() {
		final Object testingData[][] = {
			{
				"gardener1", null
			}, {
				"gardenerNoExist", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listEvents((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void listEvents(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			this.eventService.findAllFromGardener();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * Borrar incidencias de un responsable del huerto.
	 * El primer test negativo es causado porque no existe el usuario del huerto.
	 * El segundo test negativo es causado porque al usuario logueado no le corresponde ese evento
	 */
	@Test
	public void driverDeleteEvent() {
		final Object testingData[][] = {
			{
				"gardener1", 56, null
			}, {
				"gardener1NoExist", 56, IllegalArgumentException.class
			}, {
				"admin", 56, IllegalArgumentException.class
			}, {
				"gardener2", 56, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteEvent((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeleteEvent(final String username, final Integer eventId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Event event = this.eventService.findOne(eventId);

			this.eventService.delete(event);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
