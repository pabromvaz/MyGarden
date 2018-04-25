
package controllers.gardener;

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
import services.GardenerService;
import services.PlantService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Actor;
import domain.Gardener;
import domain.Plant;
import domain.WateringArea;

@Controller
@RequestMapping("/gardener/wateringArea")
public class WateringAreaGardenerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private PlantService		plantService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GardenerService		gardenerService;


	// Constructors -----------------------------------------------------------

	public WateringAreaGardenerController() {
		super();
	}

	// Create, Edit and Delete ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		WateringArea wateringArea;
		Actor actor;
		Gardener gardener;

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		wateringArea = this.wateringAreaService.create();

		result = this.createEditModelAndView(wateringArea);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		Actor actor;
		Gardener gardener;
		wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null || !wateringArea.getGardener().equals(gardener))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		result = this.createEditModelAndView(wateringArea);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid WateringArea wateringArea, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(wateringArea);
		else
			try {
				if (wateringArea.getId() != 0)
					//this.plantService.select(wateringArea.getPlant(), wateringArea);
					wateringArea = this.wateringAreaService.save(wateringArea);
				else
					wateringArea = this.wateringAreaService.save(wateringArea);
				//this.plantService.select(wateringArea.getCategories(), wateringArea);
				result = new ModelAndView("redirect:../../wateringArea/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(wateringArea, "wateringArea.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		Actor actor;
		Gardener gardener;
		wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null || !wateringArea.getGardener().equals(gardener))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		this.wateringAreaService.delete(wateringArea);
		result = new ModelAndView("redirect:../../wateringArea/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	//Create
	protected ModelAndView createEditModelAndView(final WateringArea wateringArea) {
		ModelAndView result;

		result = this.createEditModelAndView(wateringArea, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final WateringArea wateringArea, final String message) {
		ModelAndView result;

		Collection<Plant> plants;
		plants = this.plantService.findAll();
		if (wateringArea.getId() != 0)
			result = new ModelAndView("wateringArea/edit");
		else
			result = new ModelAndView("wateringArea/create");

		result.addObject("wateringArea", wateringArea);
		result.addObject("plants", plants);
		result.addObject("requestURI", "wateringArea/gardener/edit.do");
		result.addObject("message", message);

		return result;
	}
}
