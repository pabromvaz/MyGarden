
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WateringAreaRepository;
import domain.Comment;
import domain.Event;
import domain.Gardener;
import domain.Measurement;
import domain.Prediction;
import domain.Taste;
import domain.TimeTable;
import domain.WateringArea;

@Service
@Transactional
public class WateringAreaService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private WateringAreaRepository	wateringAreaRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private TasteService			tasteService;

	@Autowired
	private TimeTableService		timeTableService;

	@Autowired
	private EventService			eventService;

	@Autowired
	private MeasurementService		measurementService;

	@Autowired
	private PredictionService		predictionService;


	// Constructors -----------------------------------------------------------
	public WateringAreaService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public WateringArea findOne(final int wateringAreaId) {
		WateringArea result;

		result = this.wateringAreaRepository.findOne(wateringAreaId);
		Assert.notNull(result);

		return result;
	}

	public Collection<WateringArea> findAll() {
		Collection<WateringArea> result;

		result = this.wateringAreaRepository.findAll();

		return result;
	}

	public Collection<WateringArea> findAllVisible() {
		final Collection<WateringArea> result = new ArrayList<WateringArea>();
		List<WateringArea> aux;

		aux = this.wateringAreaRepository.findAllVisible();
		/*
		 * if (aux.size() > 10)
		 * for (int i = 0; i < 11; i++) {
		 * final int random = (int) (Math.random() * 10 + 1);
		 * result.add(aux.get(random));
		 * aux.remove(random);
		 * }
		 * else
		 * result.addAll(aux);
		 */
		return aux;
	}

	public WateringArea create() {

		Gardener gardener;
		WateringArea result;
		Calendar calendar;

		gardener = this.gardenerService.findByPrincipal();
		Assert.notNull(gardener);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new WateringArea();
		result.setGardener(gardener);

		final Collection<TimeTable> timeTables = new ArrayList<TimeTable>();
		final Collection<Measurement> measurements = new ArrayList<Measurement>();
		final Collection<Taste> tastes = new ArrayList<Taste>();
		final Collection<Comment> comments = new ArrayList<Comment>();
		final Collection<Event> events = new ArrayList<Event>();
		final Collection<Prediction> predictions = new ArrayList<Prediction>();

		result.setComments(comments);
		result.setEvents(events);
		result.setMeasurements(measurements);
		result.setTastes(tastes);
		result.setTimeTables(timeTables);
		result.setPredictions(predictions);
		result.setValveActivated(false);
		return result;
	}

	public WateringArea save(final WateringArea wateringArea) {
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(wateringArea.getGardener()));
		Assert.notNull(wateringArea);

		WateringArea result;

		result = this.wateringAreaRepository.save(wateringArea);

		return result;
	}

	public void delete(final WateringArea wateringArea) {
		Assert.notNull(wateringArea);
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(wateringArea.getGardener()));

		Collection<Taste> tastes;
		Collection<Comment> comments;
		Collection<TimeTable> timeTables;
		Collection<Measurement> measurements;
		Collection<Event> events;
		Collection<Prediction> predictions;

		/* Borramos los gustos */
		tastes = wateringArea.getTastes();
		if (!tastes.isEmpty())
			for (final Taste taste : tastes)
				this.tasteService.deleteWithWateringArea(taste);
		/* Borramos los comentarios */
		comments = wateringArea.getComments();
		if (!comments.isEmpty())
			for (final Comment comment : comments)
				this.commentService.deleteWithWateringArea(comment);
		/* Borramos los horarios */
		timeTables = wateringArea.getTimeTables();
		if (!timeTables.isEmpty())
			for (final TimeTable timeTable : timeTables)
				this.timeTableService.deleteWithWateringArea(timeTable);
		/* Borramos las medidas */
		measurements = wateringArea.getMeasurements();
		if (!measurements.isEmpty())
			for (final Measurement measurement : measurements)
				this.measurementService.delete(measurement);
		/* Borramos los eventos */
		events = wateringArea.getEvents();
		if (!events.isEmpty())
			for (final Event event : events)
				this.eventService.delete(event);
		predictions = wateringArea.getPredictions();
		for (final Prediction prediction : predictions)
			this.predictionService.delete(prediction);
		this.wateringAreaRepository.delete(wateringArea);
	}

	// Other business methods -------------------------------------------------

	public Collection<WateringArea> findByGardenerId(final Integer gardenerId) {
		Collection<WateringArea> result;
		result = this.wateringAreaRepository.findByGardenerId(gardenerId);
		return result;
	}

	public void activateValve(final WateringArea wateringArea) {
		Assert.notNull(wateringArea);
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(wateringArea.getGardener()));

		wateringArea.setValveActivated(true);
		this.wateringAreaRepository.save(wateringArea);

	}

	public void deactivateValve(final WateringArea wateringArea) {
		Assert.notNull(wateringArea);
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(wateringArea.getGardener()));

		wateringArea.setValveActivated(false);
		this.wateringAreaRepository.save(wateringArea);

	}

	public void deactivateVisibility(final WateringArea wateringArea) {
		Assert.notNull(wateringArea);
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(wateringArea.getGardener()));

		wateringArea.setVisible(false);
		this.wateringAreaRepository.save(wateringArea);

	}

	public void activateVisibility(final WateringArea wateringArea) {
		Assert.notNull(wateringArea);
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(wateringArea.getGardener()));

		wateringArea.setVisible(true);
		this.wateringAreaRepository.save(wateringArea);

	}

	public Collection<WateringArea> findByKey(final String key) {
		Collection<WateringArea> result = new ArrayList<WateringArea>();
		result = this.wateringAreaRepository.findByKey(key);
		return result;
	}

}
