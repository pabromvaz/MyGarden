
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
import services.TimeTableService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.TimeTable;
import domain.WateringArea;

@Controller
@RequestMapping("/gardener/timeTable")
public class TimeTableController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private TimeTableService	timeTableService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public TimeTableController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int wateringAreaId) {
		ModelAndView result;
		Collection<TimeTable> timeTables;
		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		timeTables = wateringArea.getTimeTables();

		result = new ModelAndView("timeTable/list");
		result.addObject("timeTables", timeTables);
		result.addObject("requestURI", "gardener/timeTable/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int timeTableId) {
		ModelAndView result;
		TimeTable timeTable;

		timeTable = this.timeTableService.findOne(timeTableId);

		result = new ModelAndView("timeTable/display");
		result.addObject("timeTable", timeTable);
		result.addObject("requestURI", "gardener/timeTable/display.do");

		return result;
	}

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer wateringAreaId/* , final Date moment, final Double precipitation */) {
		ModelAndView result;
		WateringArea wateringArea;
		TimeTable timeTable;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		timeTable = this.timeTableService.create(wateringArea);

		result = this.createEditModelAndView(timeTable);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TimeTable timeTable, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (timeTable.getWateringArea() == null)
				result = this.createEditModelAndView(timeTable, "timeTable.commit.error.not.wateringArea");
			else
				result = this.createEditModelAndView(timeTable);
		else
			try {
				this.timeTableService.save(timeTable);
				result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + timeTable.getWateringArea().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(timeTable, "timeTable.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int timeTableId) {
		ModelAndView result;
		final TimeTable timeTable;
		final WateringArea wateringArea;

		timeTable = this.timeTableService.findOne(timeTableId);
		wateringArea = timeTable.getWateringArea();

		this.timeTableService.delete(timeTable);
		result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final TimeTable timeTable) {
		ModelAndView result;

		result = this.createEditModelAndView(timeTable, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final TimeTable timeTable, final String message) {
		ModelAndView result;

		result = new ModelAndView("timeTable/create");
		result.addObject("timeTable", timeTable);
		result.addObject("message", message);

		return result;
	}

}
