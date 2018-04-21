
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PredictionRepository;
import domain.Gardener;
import domain.Prediction;
import domain.WateringArea;

@Service
@Transactional
public class PredictionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PredictionRepository	predictionRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private WateringAreaService		wateringAreaService;


	// Constructors -----------------------------------------------------------
	public PredictionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Prediction findOne(final int predictionId) {
		Prediction result;

		result = this.predictionRepository.findOne(predictionId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Prediction> findAll() {
		Collection<Prediction> result;

		result = this.predictionRepository.findAll();

		return result;
	}

	public Prediction create(final WateringArea wateringArea, final Date moment, final Double precipitation) {

		Gardener gardener;
		Prediction result;
		Calendar calendar;

		gardener = this.gardenerService.findByPrincipal();
		Assert.notNull(gardener);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Prediction();
		result.setPlace(wateringArea.getPlace());
		result.setMoment(moment);
		result.setPrecipitation(precipitation);
		result.setWateringArea(wateringArea);

		return result;
	}

	public Prediction save(final Prediction prediction) {
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));
		Assert.notNull(prediction);

		Prediction result;

		result = this.predictionRepository.save(prediction);

		return result;
	}

	public void delete(final Prediction prediction) {
		Assert.notNull(prediction);
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));

		this.predictionRepository.delete(prediction);
	}

	public void deleteWithWateringArea(final Prediction prediction) {
		Assert.notNull(prediction);
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));

		this.predictionRepository.delete(prediction);
	}

	// Other business methods -------------------------------------------------

}
