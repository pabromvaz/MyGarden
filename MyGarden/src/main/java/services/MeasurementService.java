
package services;

import java.util.Calendar;
import java.util.Collection;

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

	public Measurement create(final WateringArea wateringArea, final Double moisture, final Double humidity, final Double temperature, final Double light, final Double pH, final Double nitrogen, final Double phosphorus, final Double potassium) {
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
		result.setLight(light);
		result.setNitrogen(nitrogen);
		result.setPh(pH);
		result.setPhosphorus(phosphorus);
		result.setPotassium(potassium);
		result.setMoment(calendar.getTime());
		result.setWateringArea(wateringArea);

		return result;
	}

	public Measurement save(final Measurement measurement) {
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));
		Assert.notNull(measurement);

		Measurement result;

		result = this.measurementRepository.save(measurement);

		return result;
	}

	public void delete(final Measurement measurement) {
		Assert.notNull(measurement);
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));

		this.measurementRepository.delete(measurement);
	}

	public void deleteWithWateringArea(final Measurement measurement) {
		Assert.notNull(measurement);

		this.measurementRepository.delete(measurement);
	}

	// Other business methods -------------------------------------------------
}
