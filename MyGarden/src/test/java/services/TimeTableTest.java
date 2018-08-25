
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
	//-	Un actor autenticado como responsable del huerto debe ser capaz de:
	//	Añadir horarios a las zonas de riego propias
	//  Visualizar los horarios de una zona de riego propia
	//  Borrar los horarios de una zona de riego propia
	//  Editar los horarios de una zona de riego propia

	//El primer test negativo es causado porque no nos hemos logueado correctamente

	@Test
	public void driverAddTimeTableToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 20, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener2", 22, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener3", 23, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardenerNoExist", 20, "11/10/2018 19:00", "11/10/2018 21:00", IllegalArgumentException.class
			}, {
				"gardener1", 20, "11/10/2018 19:00", "11/09/2018 21:00", IllegalArgumentException.class
			}, {
				"admin", 20, "11/10/2018 19:00", "11/10/2018 21:00", IllegalArgumentException.class
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

	//El primer test negativo es causado porque no nos hemos logueado correctamente

	@Test
	public void driverEditTimeTable() {
		final Object testingData[][] = {
			{
				"gardener1", 51, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener2", 52, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardener3", 53, "11/10/2018 19:00", "11/10/2018 21:00", null
			}, {
				"gardenerNoExist", 51, "11/10/2018 19:00", "11/10/2018 21:00", IllegalArgumentException.class
			}, {
				"gardener1", 51, "11/10/2018 19:00", "11/09/2018 21:00", IllegalArgumentException.class
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

	//El primer test negativo es causado porque no nos hemos logueado correctamente

	@Test
	public void driverDeleteTimeTable() {
		final Object testingData[][] = {
			{
				"gardener1", 51, null
			}, {
				"gardener2", 52, null
			}, {
				"gardener3", 53, null
			}, {
				"gardenerNoExist", 51, IllegalArgumentException.class
			}, {
				"gardener1", 0, IllegalArgumentException.class
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
