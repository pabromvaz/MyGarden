
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.GardenerService;
import services.PlantService;
import domain.Actor;
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
		result.addObject("plants", plants);
		//		if (gardener != null)
		//			result.addObject("principal", gardener);
		//		else if (administrator != null)
		//			result.addObject("principal", administrator);
		return result;
	}

	// ListMyPlants ----------------------------------------------------------------
	//	@RequestMapping(value = "/listMyPlants", method = RequestMethod.GET)
	//	public ModelAndView listMyPlants() {
	//		ModelAndView result;
	//		Collection<Plant> plants;
	//
	//		final Actor actor = this.actorService.findByPrincipal();
	//
	//		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
	//		plants = this.plantService.findByGardenerId(gardener.getId());
	//
	//		result = new ModelAndView("plant/list");
	//		result.addObject("plants", plants);
	//		result.addObject("principal", actor);
	//
	//		return result;
	//	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int plantId) {
		ModelAndView result;
		Plant plant;

		final Actor actor = this.actorService.findByPrincipal();
		plant = this.plantService.findOne(plantId);

		result = new ModelAndView("plant/display");
		result.addObject("plant", plant);

		return result;
	}

}
