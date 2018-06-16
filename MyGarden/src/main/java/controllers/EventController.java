
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.EventService;
import services.GardenerService;
import services.WateringAreaService;
import domain.Actor;
import domain.Event;
import domain.Gardener;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	// Service ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

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
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> events;

		events = this.eventService.findAllFromGardener();

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("requestURI", "event/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		Boolean isOwner = false;
		if (event.getWateringArea().getGardener().equals(gardener))
			isOwner = true;

		if (event.getReaded() == false) {
			event.setReaded(true);
			this.eventService.save(event);
		}
		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("isOwner", isOwner);
		result.addObject("requestURI", "event/display.do");

		return result;
	}
}
