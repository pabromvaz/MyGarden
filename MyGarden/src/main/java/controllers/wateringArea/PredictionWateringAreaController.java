
package controllers.wateringArea;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.EventService;
import services.GardenerService;
import services.PredictionService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Actor;
import domain.Event;
import domain.Gardener;
import domain.Prediction;
import domain.WateringArea;

@Controller
@RequestMapping("/prediction/wateringArea")
public class PredictionWateringAreaController extends AbstractController {

	// Service ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private PredictionService	predictionService;

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private EventService		eventService;


	// Constructors -----------------------------------------------------------
	public PredictionWateringAreaController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int wateringAreaId) {
		ModelAndView result;
		Collection<Prediction> predictions;
		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		predictions = wateringArea.getPredictions();
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("prediction/list");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("predictions", predictions);
		result.addObject("wateringArea", wateringArea);
		result.addObject("requestURI", "prediction/wateringArea/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int predictionId) {
		ModelAndView result;
		Prediction prediction;

		prediction = this.predictionService.findOne(predictionId);
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		Boolean isOwner = false;
		if (prediction.getWateringArea().getGardener().equals(gardener))
			isOwner = true;

		result = new ModelAndView("prediction/display");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("prediction", prediction);
		result.addObject("isOwner", isOwner);
		result.addObject("requestURI", "prediction/wateringArea/display.do");

		return result;
	}

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer wateringAreaId, final Date moment, final Double precipitation) {
		ModelAndView result;
		WateringArea wateringArea;
		Prediction prediction;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		prediction = this.predictionService.create(wateringArea, moment, precipitation);

		result = this.createEditModelAndView(prediction);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Prediction prediction, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (prediction.getWateringArea() == null)
				result = this.createEditModelAndView(prediction, "prediction.commit.error.not.wateringArea");
			else
				result = this.createEditModelAndView(prediction);
		else
			try {
				this.predictionService.save(prediction);
				result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + prediction.getWateringArea().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(prediction, "prediction.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int predictionId) {
		ModelAndView result;
		final Prediction prediction;
		final WateringArea wateringArea;

		prediction = this.predictionService.findOne(predictionId);
		wateringArea = prediction.getWateringArea();

		this.predictionService.delete(prediction);
		result = new ModelAndView("redirect:../../prediction/wateringArea/list.do?wateringAreaId=" + prediction.getWateringArea().getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Prediction prediction) {
		ModelAndView result;

		result = this.createEditModelAndView(prediction, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Prediction prediction, final String message) {
		ModelAndView result;

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("prediction/create");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("prediction", prediction);
		result.addObject("message", message);

		return result;
	}

}
