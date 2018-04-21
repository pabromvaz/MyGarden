
package services;

import java.util.Calendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.TimeTable;
import domain.WateringArea;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TimeTableTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private TimeTableService	timeTableService;

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
	public void driverAddTimeTableToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 17, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener2", 19, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener3", 20, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardenerNoExist", 17, "11/10/2018 19:00", "11/10/2018 21:00", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AddTimeTableToAWateringArea((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void AddTimeTableToAWateringArea(final String username, final Integer wateringAreaId, final String activateMoment, final String deactivateMoment, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		String[] date1 = null;
		String[] time1 = null;
		String[] date2 = null;
		String[] time2 = null;
		final Calendar calendar1 = Calendar.getInstance();
		final Calendar calendar2 = Calendar.getInstance();
		try {
			this.authenticate(username);

			date1 = activateMoment.split(" ");
			time1 = date1[1].split(":");
			date1 = date1[0].split("/");
			calendar1.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]), Integer.parseInt(time1[0]), Integer.parseInt(time1[1]));

			date2 = deactivateMoment.split(" ");
			time2 = date2[1].split(":");
			date2 = date2[0].split("/");
			calendar2.set(Integer.parseInt(date2[2]), Integer.parseInt(date2[1]), Integer.parseInt(date2[0]), Integer.parseInt(time2[0]), Integer.parseInt(time2[1]));

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			final TimeTable timeTable = this.timeTableService.create(wateringArea/* , calendar1.getTime(), calendar2.getTime() */);
			timeTable.setActivationMoment(calendar1.getTime());
			timeTable.setDeactivationMoment(calendar2.getTime());
			this.timeTableService.save(timeTable);

			this.timeTableService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditTimeTable() {
		final Object testingData[][] = {
			{
				"gardener1", 25, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener2", 26, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener3", 27, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardenerNoExist", 17, "11/10/2018 19:00", "11/10/2018 21:00", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditTimeTable((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void EditTimeTable(final String username, final Integer timeTableId, final String activateMoment, final String deactivateMoment, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		String[] date1 = null;
		String[] time1 = null;
		String[] date2 = null;
		String[] time2 = null;
		final Calendar calendar1 = Calendar.getInstance();
		final Calendar calendar2 = Calendar.getInstance();
		try {
			this.authenticate(username);

			date1 = activateMoment.split(" ");
			time1 = date1[1].split(":");
			date1 = date1[0].split("/");
			calendar1.set(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]), Integer.parseInt(time1[0]), Integer.parseInt(time1[1]));

			date2 = deactivateMoment.split(" ");
			time2 = date2[1].split(":");
			date2 = date2[0].split("/");
			calendar2.set(Integer.parseInt(date2[2]), Integer.parseInt(date2[1]), Integer.parseInt(date2[0]), Integer.parseInt(time2[0]), Integer.parseInt(time2[1]));

			final TimeTable timeTable = this.timeTableService.findOne(timeTableId);
			timeTable.setActivationMoment(calendar1.getTime());
			timeTable.setDeactivationMoment(calendar2.getTime());
			this.timeTableService.save(timeTable);

			this.timeTableService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverDeleteTimeTable() {
		final Object testingData[][] = {
			{
				"gardener1", 25, null
			}, {
				"gardener2", 26, null
			}, {
				"gardener3", 27, null
			}, {
				"gardenerNoExist", 17, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteTimeTable((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeleteTimeTable(final String username, final Integer timeTableId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final TimeTable timeTable = this.timeTableService.findOne(timeTableId);

			this.timeTableService.delete(timeTable);

			this.timeTableService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
