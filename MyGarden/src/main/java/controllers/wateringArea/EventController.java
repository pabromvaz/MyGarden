
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

import services.EventService;
import services.GardenerService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Event;
import domain.WateringArea;

@Controller
@RequestMapping("/gardener/event")
public class EventController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private EventService		eventService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public EventController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int wateringAreaId) {
		ModelAndView result;
		Collection<Event> events;
		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		events = wateringArea.getEvents();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("requestURI", "gardener/event/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);

		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("requestURI", "gardener/event/display.do");

		return result;
	}

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer wateringAreaId, final String name, final String description) {
		ModelAndView result;
		WateringArea wateringArea;
		Event event;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		event = this.eventService.create(wateringArea, name, description);

		result = this.createEditModelAndView(event);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Event event, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (event.getWateringArea() == null)
				result = this.createEditModelAndView(event, "event.commit.error.not.wateringArea");
			else
				result = this.createEditModelAndView(event);
		else
			try {
				this.eventService.save(event);
				result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + event.getWateringArea().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(event, "event.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int eventId) {
		ModelAndView result;
		final Event event;
		final WateringArea wateringArea;

		event = this.eventService.findOne(eventId);
		wateringArea = event.getWateringArea();

		this.eventService.delete(event);
		result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Event event) {
		ModelAndView result;

		result = this.createEditModelAndView(event, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Event event, final String message) {
		ModelAndView result;

		result = new ModelAndView("event/create");
		result.addObject("event", event);
		result.addObject("message", message);

		return result;
	}

}
