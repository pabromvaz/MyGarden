
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.GardenerService;
import services.PlantService;
import services.TasteService;
import services.WateringAreaService;
import domain.Actor;
import domain.Comment;
import domain.Configuration;
import domain.Gardener;
import domain.Taste;
import domain.WateringArea;

@Controller
@RequestMapping("/wateringArea")
public class WateringAreaController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private PlantService		plantService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private TasteService		tasteService;

	@Autowired
	private CommentService		commentService;


	// Constructors -----------------------------------------------------------

	public WateringAreaController() {
		super();
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<WateringArea> wateringAreas;

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		wateringAreas = this.wateringAreaService.findAll();

		result = new ModelAndView("wateringArea/list");
		result.addObject("wateringAreas", wateringAreas);
		result.addObject("principal", actor);

		return result;
	}

	// ListMyWateringAreas ----------------------------------------------------------------
	@RequestMapping(value = "/listMyWateringAreas", method = RequestMethod.GET)
	public ModelAndView listMyWateringAreas() {
		ModelAndView result;
		Collection<WateringArea> wateringAreas;

		final Actor actor = this.actorService.findByPrincipal();

		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		wateringAreas = this.wateringAreaService.findByGardenerId(gardener.getId());

		result = new ModelAndView("wateringArea/list");
		result.addObject("wateringAreas", wateringAreas);
		result.addObject("principal", actor);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		Collection<Comment> comments;
		Configuration configuration;
		Boolean isOwner = false;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		final Collection<Taste> tastes = this.tasteService.findAll();

		if (gardener != null && wateringArea.getGardener().equals(gardener))
			isOwner = true;

		comments = this.commentService.findAllOfAWateringArea(wateringAreaId);
		configuration = gardener.getConfiguration();

		result = new ModelAndView("wateringArea/display");
		result.addObject("wateringArea", wateringArea);
		result.addObject("comments", comments);
		result.addObject("isOwner", isOwner);
		result.addObject("actor", actor);
		result.addObject("tastes", tastes);
		result.addObject("configuration", configuration);
		result.addObject("requestURI", "wateringArea/display.do");

		return result;
	}

	// Search ----------------------------------------------------------------
	//	@RequestMapping(value = "/search", method = RequestMethod.GET)
	//	public ModelAndView searchButton(@RequestParam final String key) {
	//
	//		ModelAndView result;
	//		Collection<WateringArea> wateringAreas;
	//		Collection<Taste> tastes;
	//		Collection<Plant> categories;
	//		Collection<Object[]> auxWateringAreas;
	//
	//		final Actor actor = this.actorService.findByPrincipal();
	//		tastes = this.tasteService.findAll();
	//
	//		categories = this.plantService.findAll();
	//
	//		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
	//		if (gardener != null)
	//			wateringAreas = this.wateringAreaService.findByKeyWithAge(key);
	//		else
	//			wateringAreas = this.wateringAreaService.findByKey(key);
	//
	//		auxWateringAreas = this.wateringAreaService.avgGreaterthanEight(wateringAreas);
	//
	//		result = new ModelAndView("wateringArea/list");
	//		result.addObject("wateringAreas", auxWateringAreas);
	//		result.addObject("principal", actor);
	//		result.addObject("tasteList", tastes);
	//		result.addObject("categories", categories);
	//
	//		return result;
	//	}

	// Search ----------------------------------------------------------------
	//	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	//	public ModelAndView filterButton(@RequestParam(required = false) final String key, @RequestParam(required = false) final Integer minPrice, @RequestParam(required = false) final Integer maxPrice) {
	//
	//		ModelAndView result;
	//		Collection<WateringArea> wateringAreas;
	//		Collection<Taste> tastes;
	//		Collection<Plant> categories;
	//		Collection<Object[]> auxWateringAreas;
	//
	//		Double minPrice2 = 0.0;
	//		Double maxPrice2 = 0.0;
	//		final Actor actor = this.actorService.findByPrincipal();
	//		tastes = this.tasteService.findAll();
	//
	//		categories = this.plantService.findAll();
	//
	//		if (minPrice != null)
	//			minPrice2 = (double) minPrice;
	//
	//		if (maxPrice != null)
	//			maxPrice2 = (double) maxPrice;
	//		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
	//		if (gardener != null)
	//			wateringAreas = this.wateringAreaService.findByPlantOrPriceWithAge(key, minPrice2, maxPrice2);
	//		else
	//			wateringAreas = this.wateringAreaService.findByPlantOrPrice(key, minPrice2, maxPrice2);
	//
	//		auxWateringAreas = this.wateringAreaService.avgGreaterthanEight(wateringAreas);
	//
	//		result = new ModelAndView("wateringArea/list");
	//		result.addObject("wateringAreas", auxWateringAreas);
	//		result.addObject("principal", actor);
	//		result.addObject("tasteList", tastes);
	//		result.addObject("categories", categories);
	//
	//		return result;
	//	}

}
