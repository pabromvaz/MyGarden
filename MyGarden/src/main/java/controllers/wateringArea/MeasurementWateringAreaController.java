
package controllers.wateringArea;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.GardenerService;
import services.MeasurementService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Actor;
import domain.Gardener;
import domain.Measurement;
import domain.WateringArea;

@Controller
@RequestMapping("measurement/wateringArea")
public class MeasurementWateringAreaController extends AbstractController {

	// Service ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private MeasurementService	measurementService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public MeasurementWateringAreaController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int wateringAreaId) {
		ModelAndView result;
		Collection<Measurement> measurements;
		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		measurements = wateringArea.getMeasurements();

		result = new ModelAndView("measurement/list");
		result.addObject("measurements", measurements);
		result.addObject("wateringArea", wateringArea);
		result.addObject("requestURI", "measurement/wateringArea/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int measurementId) {
		ModelAndView result;
		Measurement measurement;

		measurement = this.measurementService.findOne(measurementId);

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		Boolean isOwner = false;
		if (measurement.getWateringArea().getGardener().equals(gardener))
			isOwner = true;

		result = new ModelAndView("measurement/display");
		result.addObject("measurement", measurement);
		result.addObject("isOwner", isOwner);
		result.addObject("requestURI", "measurement/wateringArea/display.do");

		return result;
	}

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer wateringAreaId, final Double moisture, final Double humidity, final Double temperature, final Double light, final Double pH, final Double nitrogen, final Double phosphorus, final Double potassium) {
		ModelAndView result;
		WateringArea wateringArea;
		Measurement measurement;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		measurement = this.measurementService.create(wateringArea, moisture, humidity, temperature, light, pH, nitrogen, phosphorus, potassium);

		result = this.createEditModelAndView(measurement);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Measurement measurement, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (measurement.getWateringArea() == null)
				result = this.createEditModelAndView(measurement, "measurement.commit.error.not.wateringArea");
			else
				result = this.createEditModelAndView(measurement);
		else
			try {
				this.measurementService.save(measurement);
				result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + measurement.getWateringArea().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(measurement, "measurement.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Measurement measurement, final BindingResult binding) {
		ModelAndView result;
		Actor actor;
		Gardener gardener;

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null || !measurement.getWateringArea().getGardener().equals(gardener))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		this.measurementService.delete(measurement);
		result = new ModelAndView("redirect:../../measurement/wateringArea/list.do?wateringAreaId=" + measurement.getWateringArea().getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Measurement measurement) {
		ModelAndView result;

		result = this.createEditModelAndView(measurement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Measurement measurement, final String message) {
		ModelAndView result;

		result = new ModelAndView("measurement/create");
		result.addObject("measurement", measurement);
		result.addObject("message", message);

		return result;
	}
}
