
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
import services.EventService;
import services.GardenerService;
import services.TimeTableService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Actor;
import domain.Event;
import domain.Gardener;
import domain.TimeTable;
import domain.WateringArea;

@Controller
@RequestMapping("/timeTable/wateringArea")
public class TimeTableWateringAreaController extends AbstractController {

	// Service ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private TimeTableService	timeTableService;

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private EventService		eventService;


	// Constructors -----------------------------------------------------------
	public TimeTableWateringAreaController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int wateringAreaId) {
		ModelAndView result;
		Collection<TimeTable> timeTables;
		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		timeTables = wateringArea.getTimeTables();
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("timeTable/list");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("timeTables", timeTables);
		result.addObject("wateringArea", wateringArea);
		result.addObject("requestURI", "timeTable/wateringArea/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int timeTableId) {
		ModelAndView result;
		TimeTable timeTable;

		timeTable = this.timeTableService.findOne(timeTableId);
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		Boolean isOwner = false;
		if (timeTable.getWateringArea().getGardener().equals(gardener))
			isOwner = true;

		result = new ModelAndView("timeTable/display");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("timeTable", timeTable);
		result.addObject("isOwner", isOwner);
		result.addObject("requestURI", "timeTable/wateringArea/display.do");

		return result;
	}

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		TimeTable timeTable;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		timeTable = this.timeTableService.create(wateringArea);

		result = this.createModelAndView(timeTable);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final TimeTable timeTable, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (timeTable.getWateringArea() == null)
				result = this.createModelAndView(timeTable, "timeTable.commit.error.not.wateringArea");
			else
				result = this.createModelAndView(timeTable);
		else
			try {
				this.timeTableService.save(timeTable);
				result = new ModelAndView("redirect:/timeTable/wateringArea/list.do?wateringAreaId=" + timeTable.getWateringArea().getId());
			} catch (final Throwable oops) {
				if (!(timeTable.getActivationMoment().before(timeTable.getDeactivationMoment())))
					result = this.editModelAndView(timeTable, "timeTable.commit.error.moment");
				else
					result = this.createModelAndView(timeTable, "timeTable.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int timeTableId) {
		ModelAndView result;
		TimeTable timeTable;
		Actor actor;
		Gardener gardener;
		timeTable = this.timeTableService.findOne(timeTableId);

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null || !timeTable.getWateringArea().getGardener().equals(gardener))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		result = this.editModelAndView(timeTable);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TimeTable timeTable, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (timeTable.getWateringArea() == null)
				result = this.createModelAndView(timeTable, "timeTable.commit.error.not.wateringArea");
			else
				result = this.createModelAndView(timeTable);
		else
			try {
				this.timeTableService.save(timeTable);
				result = new ModelAndView("redirect:/timeTable/wateringArea/display.do?timeTableId=" + timeTable.getId());
			} catch (final Throwable oops) {
				if (!(timeTable.getActivationMoment().before(timeTable.getDeactivationMoment())))
					result = this.editModelAndView(timeTable, "timeTable.commit.error.moment");
				else
					result = this.createModelAndView(timeTable, "timeTable.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final TimeTable timeTable, final BindingResult binding) {
		ModelAndView result;
		Actor actor;
		Gardener gardener;

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null || !timeTable.getWateringArea().getGardener().equals(gardener))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		this.timeTableService.delete(timeTable);
		result = new ModelAndView("redirect:../../timeTable/wateringArea/list.do?wateringAreaId=" + timeTable.getWateringArea().getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	//Create
	protected ModelAndView createModelAndView(final TimeTable timeTable) {
		ModelAndView result;

		result = this.createModelAndView(timeTable, null);

		return result;
	}

	protected ModelAndView createModelAndView(final TimeTable timeTable, final String message) {
		ModelAndView result;

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("timeTable/create");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("timeTable", timeTable);
		result.addObject("message", message);
		result.addObject("requestURI", "timeTable/wateringArea/create.do");

		return result;
	}

	//Edit
	protected ModelAndView editModelAndView(final TimeTable timeTable) {
		ModelAndView result;

		result = this.editModelAndView(timeTable, null);

		return result;
	}

	protected ModelAndView editModelAndView(final TimeTable timeTable, final String message) {
		ModelAndView result;

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("timeTable/edit");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("timeTable", timeTable);
		result.addObject("message", message);
		result.addObject("requestURI", "timeTable/wateringArea/edit.do");
		return result;
	}

}
