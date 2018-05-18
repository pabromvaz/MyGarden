
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
import services.FertilizerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Fertilizer;

@Controller
@RequestMapping("/fertilizer/administrator")
public class FertilizerAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FertilizerService		fertilizerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public FertilizerAdministratorController() {
		super();
	}

	// Create, Edit and Delete ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Fertilizer fertilizer;
		Actor actor;
		Administrator administrator;

		actor = this.actorService.findByPrincipal();
		administrator = this.administratorService.findByUserAccount(actor.getUserAccount());

		if (administrator == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		fertilizer = this.fertilizerService.create();

		result = this.createEditModelAndView(fertilizer);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Fertilizer fertilizer, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(fertilizer);
		else
			try {
				this.fertilizerService.save(fertilizer);
				result = new ModelAndView("redirect:../../fertilizer/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fertilizer, "fertilizer.commit.error");

			}
		return result;
	}

	// Edit ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fertilizerId) {
		ModelAndView result;
		Fertilizer fertilizer;
		Actor actor;
		Administrator administrator;
		fertilizer = this.fertilizerService.findOne(fertilizerId);

		actor = this.actorService.findByPrincipal();
		administrator = this.administratorService.findByUserAccount(actor.getUserAccount());

		if (administrator == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		result = this.createEditModelAndView(fertilizer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Fertilizer fertilizer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(fertilizer);
		else
			try {
				if (fertilizer.getId() != 0)
					//this.fertilizerService.select(fertilizer.getFertilizer(), fertilizer);
					fertilizer = this.fertilizerService.save(fertilizer);
				else
					fertilizer = this.fertilizerService.save(fertilizer);
				//this.fertilizerService.select(fertilizer.getCategories(), fertilizer);
				result = new ModelAndView("redirect:../../fertilizer/display.do?fertilizerId=" + fertilizer.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fertilizer, "fertilizer.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Fertilizer fertilizer, final BindingResult binding) {
		ModelAndView result;
		Actor actor;
		Administrator administrator;

		actor = this.actorService.findByPrincipal();
		administrator = this.administratorService.findByUserAccount(actor.getUserAccount());

		if (administrator == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		try {
			this.fertilizerService.delete(fertilizer);
			result = new ModelAndView("redirect:../../fertilizer/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../fertilizer/list.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	//Create
	protected ModelAndView createEditModelAndView(final Fertilizer fertilizer) {
		ModelAndView result;

		result = this.createEditModelAndView(fertilizer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Fertilizer fertilizer, final String message) {
		ModelAndView result;

		if (fertilizer.getId() != 0) {
			result = new ModelAndView("fertilizer/edit");
			result.addObject("requestURI", "fertilizer/administrator/edit.do");
		} else {
			result = new ModelAndView("fertilizer/create");
			result.addObject("requestURI", "fertilizer/administrator/create.do");
		}

		result.addObject("fertilizer", fertilizer);
		result.addObject("message", message);

		return result;
	}

}
