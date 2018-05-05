
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TimeTableRepository;
import domain.Gardener;
import domain.TimeTable;
import domain.WateringArea;

@Service
@Transactional
public class TimeTableService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TimeTableRepository	timeTableRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public TimeTableService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public TimeTable findOne(final int timeTableId) {
		TimeTable result;

		result = this.timeTableRepository.findOne(timeTableId);
		Assert.notNull(result);

		return result;
	}

	public Collection<TimeTable> findAll() {
		Collection<TimeTable> result;

		result = this.timeTableRepository.findAll();

		return result;
	}

	public TimeTable create(final WateringArea wateringArea/* , final Date activation, final Date deactivation */) {
		Assert.notNull(wateringArea);
		Gardener gardener;
		TimeTable result;
		Calendar calendar;

		gardener = this.gardenerService.findByPrincipal();
		Assert.notNull(gardener);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new TimeTable();
		//result.setActivationMoment(activation);
		//result.setDeactivationMoment(deactivation);
		result.setWateringArea(wateringArea);

		return result;
	}

	public TimeTable save(final TimeTable timeTable) {
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));
		Assert.notNull(timeTable);
		Assert.isTrue(timeTable.getActivationMoment().before(timeTable.getDeactivationMoment()));

		TimeTable result;

		result = this.timeTableRepository.save(timeTable);

		return result;
	}

	public void delete(final TimeTable timeTable) {
		Assert.notNull(timeTable);
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));

		this.timeTableRepository.delete(timeTable);
	}

	public void deleteWithWateringArea(final TimeTable timeTable) {
		Assert.notNull(timeTable);

		this.timeTableRepository.delete(timeTable);
	}

	// Other business methods -------------------------------------------------
}
