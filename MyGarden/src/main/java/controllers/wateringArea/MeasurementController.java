
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

import services.GardenerService;
import services.MeasurementService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Measurement;
import domain.WateringArea;

@Controller
@RequestMapping("/gardener/measurement")
public class MeasurementController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private MeasurementService	measurementService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public MeasurementController() {
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
		result.addObject("requestURI", "gardener/measurement/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int measurementId) {
		ModelAndView result;
		Measurement measurement;

		measurement = this.measurementService.findOne(measurementId);

		result = new ModelAndView("measurement/display");
		result.addObject("measurement", measurement);
		result.addObject("requestURI", "gardener/measurement/display.do");

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

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int measurementId) {
		ModelAndView result;
		final Measurement measurement;
		final WateringArea wateringArea;

		measurement = this.measurementService.findOne(measurementId);
		wateringArea = measurement.getWateringArea();

		this.measurementService.delete(measurement);
		result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());

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
