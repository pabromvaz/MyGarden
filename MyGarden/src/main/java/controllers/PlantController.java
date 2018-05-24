
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.EventService;
import services.GardenerService;
import services.PlantService;
import services.WateringAreaService;
import domain.Actor;
import domain.Event;
import domain.Plant;

@Controller
@RequestMapping("/plant")
public class PlantController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PlantService			plantService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private WateringAreaService		wateringAreaService;

	@Autowired
	private EventService			eventService;


	// Constructors -----------------------------------------------------------

	public PlantController() {
		super();
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Plant> plants;

		final Actor actor = this.actorService.findByPrincipal();

		plants = this.plantService.findAll();

		result = new ModelAndView("plant/list");

		final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("plants", plants);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int plantId) {
		ModelAndView result;
		Plant plant;

		final Actor actor = this.actorService.findByPrincipal();
		plant = this.plantService.findOne(plantId);

		result = new ModelAndView("plant/display");

		final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("plant", plant);

		return result;
	}

}
