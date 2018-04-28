
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
import services.FertilizerService;
import services.GardenerService;
import domain.Actor;
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
		result.addObject("fertilizers", fertilizers);
		//		if (gardener != null)
		//			result.addObject("principal", gardener);
		//		else if (administrator != null)
		//			result.addObject("principal", administrator);
		return result;
	}

	// ListMyFertilizers ----------------------------------------------------------------
	//	@RequestMapping(value = "/listMyFertilizers", method = RequestMethod.GET)
	//	public ModelAndView listMyFertilizers() {
	//		ModelAndView result;
	//		Collection<Fertilizer> fertilizers;
	//
	//		final Actor actor = this.actorService.findByPrincipal();
	//
	//		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
	//		fertilizers = this.fertilizerService.findByGardenerId(gardener.getId());
	//
	//		result = new ModelAndView("fertilizer/list");
	//		result.addObject("fertilizers", fertilizers);
	//		result.addObject("principal", actor);
	//
	//		return result;
	//	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int fertilizerId) {
		ModelAndView result;
		Fertilizer fertilizer;

		final Actor actor = this.actorService.findByPrincipal();

		fertilizer = this.fertilizerService.findOne(fertilizerId);

		result = new ModelAndView("fertilizer/display");
		result.addObject("fertilizer", fertilizer);

		return result;
	}

}
