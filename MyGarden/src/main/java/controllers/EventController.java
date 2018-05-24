
package controllers;

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
import services.WateringAreaService;
import domain.Actor;
import domain.Event;
import domain.Gardener;
import domain.WateringArea;

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

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public void create(@RequestParam final Integer wateringAreaId, final String name, final String description, final String type) {
		final ModelAndView result;
		WateringArea wateringArea;
		Event event;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		event = this.eventService.create(wateringArea, name, description, type);
		this.eventService.save(event);
		//result = this.createEditModelAndView(event);

		//return result;
	}

	// Create EventDetection-------------------------------------------------------------------
	@RequestMapping(value = "/createDetectionWarning", method = RequestMethod.GET)
	public ModelAndView createDetectionWarning(@RequestParam final Integer wateringAreaId) {
		final ModelAndView result;
		WateringArea wateringArea;
		Event event;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		event = this.eventService.create(wateringArea, "Intrusión en" + wateringArea.getName(), "Se ha detectado una intrusión en la zona de riego" + wateringArea.getName(), "Intrusion");
		this.eventService.save(event);
		result = new ModelAndView("redirect:../../welcome/index.do");

		return result;
	}

	// Create EventTankWarning-------------------------------------------------------------------
	@RequestMapping(value = "/createTankWarning", method = RequestMethod.POST)
	public void createTankWarning(@RequestParam final Integer wateringAreaId) {
		final ModelAndView result;
		WateringArea wateringArea;
		Event event;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		event = this.eventService.create(wateringArea, "Agua insuficiente en" + wateringArea.getName(), "La zona de riego" + wateringArea.getName() + "tiene insuficiente agua en el depósito", "Tank");
		this.eventService.save(event);
		//result = this.createEditModelAndView(event);

		//return result;
	}

	// Create EventFertilizerWarning-------------------------------------------------------------------
	@RequestMapping(value = "/createFertilizerWarning", method = RequestMethod.POST)
	public void createFertilizerWarning(@RequestParam final Integer wateringAreaId) {
		final ModelAndView result;
		WateringArea wateringArea;
		Event event;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		event = this.eventService.create(wateringArea, "Usar fertilizante en" + wateringArea.getName(), "La zona de riego" + wateringArea.getName() + "requiere fertilizante", "Fertilizer");
		this.eventService.save(event);
		//result = this.createEditModelAndView(event);

		//return result;
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

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Event event, final BindingResult binding) {
		ModelAndView result;
		Actor actor;
		Gardener gardener;

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null || !event.getWateringArea().getGardener().equals(gardener))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		this.eventService.delete(event);
		//result = new ModelAndView("redirect:../../event/wateringArea/list.do?wateringAreaId=" + event.getWateringArea().getId());
		result = new ModelAndView("redirect:../../event/list.do");

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
