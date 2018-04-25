
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.PlantService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Plant;

@Controller
@RequestMapping("/administrator/plant")
public class PlantAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PlantService			plantService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public PlantAdministratorController() {
		super();
	}

	// Create, Edit and Delete ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Plant plant;
		Actor actor;
		Administrator administrator;

		actor = this.actorService.findByPrincipal();
		administrator = this.administratorService.findByUserAccount(actor.getUserAccount());

		if (administrator == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		plant = this.plantService.create();

		result = this.createEditModelAndView(plant);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int plantId) {
		ModelAndView result;
		Plant plant;
		Actor actor;
		Administrator administrator;
		plant = this.plantService.findOne(plantId);

		actor = this.actorService.findByPrincipal();
		administrator = this.administratorService.findByUserAccount(actor.getUserAccount());

		if (administrator == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		result = this.createEditModelAndView(plant);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Plant plant, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(plant);
		else
			try {
				if (plant.getId() != 0)
					//this.plantService.select(plant.getPlant(), plant);
					plant = this.plantService.save(plant);
				else
					plant = this.plantService.save(plant);
				//this.plantService.select(plant.getCategories(), plant);
				result = new ModelAndView("redirect:../../plant/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(plant, "plant.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int plantId) {
		ModelAndView result;
		Plant plant;
		Actor actor;
		Administrator administrator;
		plant = this.plantService.findOne(plantId);

		actor = this.actorService.findByPrincipal();
		administrator = this.administratorService.findByUserAccount(actor.getUserAccount());

		if (administrator == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		this.plantService.delete(plant);
		result = new ModelAndView("redirect:../../plant/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	//Create
	protected ModelAndView createEditModelAndView(final Plant plant) {
		ModelAndView result;

		result = this.createEditModelAndView(plant, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Plant plant, final String message) {
		ModelAndView result;

		if (plant.getId() != 0)
			result = new ModelAndView("plant/edit");
		else
			result = new ModelAndView("plant/create");

		result.addObject("plant", plant);
		result.addObject("requestURI", "plant/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}

}
