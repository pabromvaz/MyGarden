
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.EventService;
import services.GardenerService;
import services.MeasurementService;
import services.PlantService;
import services.PredictionService;
import services.TasteService;
import services.TimeTableService;
import services.WateringAreaService;
import domain.Actor;
import domain.Comment;
import domain.Configuration;
import domain.Event;
import domain.Gardener;
import domain.Measurement;
import domain.Prediction;
import domain.Taste;
import domain.TimeTable;
import domain.WateringArea;

@Controller
@RequestMapping("/wateringArea")
public class WateringAreaController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private PlantService		plantService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private TasteService		tasteService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private EventService		eventService;

	@Autowired
	private MeasurementService	measurementService;

	@Autowired
	private TimeTableService	timeTableService;

	@Autowired
	private PredictionService	predictionService;


	// Constructors -----------------------------------------------------------

	public WateringAreaController() {
		super();
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<WateringArea> wateringAreas;

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		wateringAreas = this.wateringAreaService.findAllVisible();
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("wateringArea/list");
		result.addObject("wateringAreas", wateringAreas);
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("principal", actor);

		return result;
	}

	// ListMyWateringAreas ----------------------------------------------------------------
	@RequestMapping(value = "/listMyWateringAreas", method = RequestMethod.GET)
	public ModelAndView listMyWateringAreas() {
		ModelAndView result;
		Collection<WateringArea> wateringAreas;

		final Actor actor = this.actorService.findByPrincipal();

		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		wateringAreas = this.wateringAreaService.findByGardenerId(gardener.getId());
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("wateringArea/list");
		result.addObject("wateringAreas", wateringAreas);
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("principal", actor);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		Collection<Comment> comments;
		Configuration configuration;
		Boolean isOwner = false;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final Collection<Taste> tastes = this.tasteService.findAll();

		if (gardener != null && wateringArea.getGardener().equals(gardener))
			isOwner = true;

		final List<Measurement> measurements = this.measurementService.showMeasurements(wateringArea);
		final List<TimeTable> timeTables = this.timeTableService.showTimeTables(wateringArea);
		final List<Prediction> predictions = this.predictionService.showPredictions(wateringArea);
		comments = this.commentService.findAllOfAWateringArea(wateringAreaId);
		configuration = gardener.getConfiguration();

		result = new ModelAndView("wateringArea/display");
		result.addObject("wateringArea", wateringArea);
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("comments", comments);
		result.addObject("isOwner", isOwner);
		result.addObject("actor", actor);
		result.addObject("tastes", tastes);

		if (!measurements.isEmpty()) {
			final Measurement measurement = measurements.get(0);
			result.addObject("measurement", measurement);
		}
		result.addObject("measurementSize", measurements.size());

		if (!timeTables.isEmpty()) {
			final TimeTable timeTable = timeTables.get(0);
			result.addObject("timeTable", timeTable);
		}
		result.addObject("timeTableSize", timeTables.size());

		if (!predictions.isEmpty()) {
			final Prediction prediction = predictions.get(0);
			result.addObject("prediction", prediction);
		}
		result.addObject("predictionSize", predictions.size());

		result.addObject("configuration", configuration);
		result.addObject("requestURI", "wateringArea/display.do");
		return result;
	}

	// Display for arduino ----------------------------------------------------------------
	@RequestMapping(value = "/manualWateringIsActivated", method = RequestMethod.GET)
	public ModelAndView manualWateringIsActivated(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		//Collection<Comment> comments;
		Configuration configuration;
		Boolean isOwner = false;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		//final Collection<Taste> tastes = this.tasteService.findAll();

		if (gardener != null && wateringArea.getGardener().equals(gardener))
			isOwner = true;

		//final List<Measurement> measurements = this.measurementService.showMeasurements(wateringArea);
		//final List<TimeTable> timeTables = this.timeTableService.showTimeTables(wateringArea);
		//final List<Prediction> predictions = this.predictionService.showPredictions(wateringArea);
		//comments = this.commentService.findAllOfAWateringArea(wateringAreaId);
		configuration = gardener.getConfiguration();

		result = new ModelAndView("wateringArea/manualWateringIsActivated");
		result.addObject("wateringArea", wateringArea);
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		//result.addObject("comments", comments);
		result.addObject("isOwner", isOwner);
		//result.addObject("actor", actor);
		//result.addObject("tastes", tastes);

		//	if (!measurements.isEmpty()) {
		//		final Measurement measurement = measurements.get(0);
		//		result.addObject("measurement", measurement);
		//	}
		//	result.addObject("measurementSize", measurements.size());

		//	if (!timeTables.isEmpty()) {
		//		final TimeTable timeTable = timeTables.get(0);
		//		result.addObject("timeTable", timeTable);
		//	}
		//	result.addObject("timeTableSize", timeTables.size());

		//	if (!predictions.isEmpty()) {
		//		final Prediction prediction = predictions.get(0);
		//		result.addObject("prediction", prediction);
		//	}
		//	result.addObject("predictionSize", predictions.size());

		result.addObject("configuration", configuration);
		result.addObject("requestURI", "wateringArea/manualWateringIsActivated.do");
		return result;
	}
	// Search ----------------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchButton(@RequestParam final String key) {

		ModelAndView result;
		Collection<WateringArea> wateringAreas;
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final Actor actor = this.actorService.findByPrincipal();

		wateringAreas = this.wateringAreaService.findByKey(key);

		result = new ModelAndView("wateringArea/list");
		result.addObject("wateringAreas", wateringAreas);
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("principal", actor);

		return result;
	}

}
