
package controllers.gardener;

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
import services.PlantService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Actor;
import domain.Event;
import domain.Gardener;
import domain.Plant;
import domain.WateringArea;

@Controller
@RequestMapping("/plant/gardener")
public class PlantGardenerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PlantService		plantService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private EventService		eventService;


	// Constructors -----------------------------------------------------------

	public PlantGardenerController() {
		super();
	}

	//ListMyPlants ----------------------------------------------------------------
	@RequestMapping(value = "/listRecommendedPlants", method = RequestMethod.GET)
	public ModelAndView listRecommendedPlants(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		Collection<Plant> plants;

		final Actor actor = this.actorService.findByPrincipal();

		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		plants = this.plantService.findRecommendedPlants(wateringArea);

		result = new ModelAndView("plant/list");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("plants", plants);
		result.addObject("principal", actor);

		return result;
	}
}
