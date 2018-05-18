
package controllers.administrator;

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
import services.AdministratorService;
import services.FertilizerService;
import services.PlantService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Fertilizer;
import domain.Plant;

@Controller
@RequestMapping("/plant/administrator")
public class PlantAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PlantService			plantService;

	@Autowired
	private FertilizerService		fertilizerService;

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

		result = this.createModelAndView(plant);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Plant plant, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(plant);
		else
			try {

				plant = this.plantService.save(plant);
				this.fertilizerService.select(plant.getFertilizers(), plant);
				result = new ModelAndView("redirect:../../plant/list.do");

			} catch (final Throwable oops) {
				if (!(plant.getMinTemperature() <= plant.getMaxTemperature()))
					result = this.editModelAndView(plant, "plant.commit.error.temperature");
				else
					result = this.createModelAndView(plant, "plant.commit.error");

			}
		return result;
	}

	// Edit ----------------------------------------------------------------
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

		result = this.editModelAndView(plant);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Plant plant, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(plant);
		else
			try {
				if (plant.getId() != 0)
					this.fertilizerService.select(plant.getFertilizers(), plant);
				plant = this.plantService.save(plant);
				result = new ModelAndView("redirect:../../plant/display.do?plantId=" + plant.getId());
			} catch (final Throwable oops) {
				if (!(plant.getMinTemperature() <= plant.getMaxTemperature()))
					result = this.editModelAndView(plant, "plant.commit.error.temperature");
				else
					result = this.editModelAndView(plant, "plant.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Plant plant, final BindingResult binding) {
		ModelAndView result;

		Actor actor;
		Administrator administrator;

		actor = this.actorService.findByPrincipal();
		administrator = this.administratorService.findByUserAccount(actor.getUserAccount());

		if (administrator == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		try {

			this.plantService.delete(plant);
			result = new ModelAndView("redirect:../../plant/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../plant/list.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createModelAndView(final Plant plant) {
		ModelAndView result;

		result = this.createModelAndView(plant, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Plant plant, final String message) {
		ModelAndView result;

		final Collection<Fertilizer> fertilizers = this.fertilizerService.findAll();

		result = new ModelAndView("plant/create");
		result.addObject("plant", plant);
		result.addObject("fertilizers", fertilizers);
		result.addObject("requestURI", "plant/administrator/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(final Plant plant) {
		ModelAndView result;

		result = this.editModelAndView(plant, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Plant plant, final String message) {
		ModelAndView result;

		result = new ModelAndView("plant/edit");
		result.addObject("plant", plant);
		result.addObject("requestURI", "plant/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}
}
