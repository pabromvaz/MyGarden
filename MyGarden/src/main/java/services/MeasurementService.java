
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MeasurementRepository;
import domain.Gardener;
import domain.Measurement;
import domain.WateringArea;

@Service
@Transactional
public class MeasurementService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MeasurementRepository	measurementRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private WateringAreaService		wateringAreaService;


	// Constructors -----------------------------------------------------------
	public MeasurementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Measurement findOne(final int measurementId) {
		Measurement result;

		result = this.measurementRepository.findOne(measurementId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Measurement> findAll() {
		Collection<Measurement> result;

		result = this.measurementRepository.findAll();

		return result;
	}

	public Measurement create(final WateringArea wateringArea, final Integer moisture, final Integer humidity, final Integer temperature) {
		Assert.notNull(wateringArea);
		Gardener gardener;
		Measurement result;
		Calendar calendar;

		gardener = this.gardenerService.findByPrincipal();
		Assert.notNull(gardener);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Measurement();
		result.setMoisture(moisture);
		result.setHumidity(humidity);
		result.setTemperature(temperature);
		result.setMoment(calendar.getTime());
		result.setWateringArea(wateringArea);

		return result;
	}

	public Measurement save(final Measurement measurement) {
		Assert.notNull(measurement);

		Measurement result;

		result = this.measurementRepository.save(measurement);

		return result;
	}

	public void delete(final Measurement measurement) {
		Assert.notNull(measurement);

		this.measurementRepository.delete(measurement);
	}

	public void deleteWithWateringArea(final Measurement measurement) {
		Assert.notNull(measurement);

		this.measurementRepository.delete(measurement);
	}

	// Other business methods -------------------------------------------------

	public List<Measurement> showMeasurements(final WateringArea wateringArea) {
		List<Measurement> result = new ArrayList<Measurement>();
		result = this.measurementRepository.measurementsOfWateringAreaOrderedByMoment(wateringArea.getId());
		return result;
	}
}
