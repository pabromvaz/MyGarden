
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
import services.FertilizerService;
import services.GardenerService;
import domain.Actor;
import domain.Event;
import domain.Fertilizer;

@Controller
@RequestMapping("/fertilizer")
public class FertilizerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FertilizerService		fertilizerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private EventService			eventService;


	// Constructors -----------------------------------------------------------

	public FertilizerController() {
		super();
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Fertilizer> fertilizers;

		final Actor actor = this.actorService.findByPrincipal();

		fertilizers = this.fertilizerService.findAll();

		result = new ModelAndView("fertilizer/list");

		final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("fertilizers", fertilizers);
		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int fertilizerId) {
		ModelAndView result;
		Fertilizer fertilizer;

		final Actor actor = this.actorService.findByPrincipal();

		fertilizer = this.fertilizerService.findOne(fertilizerId);

		result = new ModelAndView("fertilizer/display");

		final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("fertilizer", fertilizer);

		return result;
	}

}
