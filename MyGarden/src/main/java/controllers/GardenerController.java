
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.GardenerService;
import domain.Event;
import domain.Gardener;
import forms.CreateGardenerForm;

@Controller
@RequestMapping("/gardener")
public class GardenerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private GardenerService	gardenerService;

	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public GardenerController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateGardenerForm createGardenerForm;

		createGardenerForm = new CreateGardenerForm();
		result = this.createEditModelAndView(createGardenerForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateGardenerForm createGardenerForm, final BindingResult binding) {

		ModelAndView result;
		Gardener gardener;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createGardenerForm);
		else
			try {
				gardener = this.gardenerService.reconstructProfile(createGardenerForm, "create");
				this.gardenerService.saveRegister(gardener);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				if (!createGardenerForm.getPassword().equals(createGardenerForm.getConfirmPassword()))
					result = this.createEditModelAndView(createGardenerForm, "gardener.commit.error.password");
				else if (createGardenerForm.getIsAgree().equals(false))
					result = this.createEditModelAndView(createGardenerForm, "gardener.commit.error.isAgree");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.createEditModelAndView(createGardenerForm, "gardener.commit.error.duplicate");
				else
					result = this.createEditModelAndView(createGardenerForm, "gardener.commit.error");

			}
		return result;
	}
	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreateGardenerForm createGardenerForm;
		Gardener gardener;

		gardener = this.gardenerService.findByPrincipal();
		createGardenerForm = this.gardenerService.constructProfile(gardener);
		result = this.editionEditModelAndView(createGardenerForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateGardenerForm createGardenerForm, final BindingResult binding) {

		ModelAndView result;
		Gardener gardener;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createGardenerForm);
		else
			try {
				gardener = this.gardenerService.reconstructProfile(createGardenerForm, "edit");
				this.gardenerService.save(gardener);
				result = new ModelAndView("redirect:/profile/myProfile.do");

			} catch (final Throwable oops) {
				if (!createGardenerForm.getPassword().equals(createGardenerForm.getConfirmPassword()))
					result = this.editionEditModelAndView(createGardenerForm, "gardener.commit.error.password");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.editionEditModelAndView(createGardenerForm, "gardener.commit.error.duplicate");
				else
					result = this.editionEditModelAndView(createGardenerForm, "gardener.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateGardenerForm createGardenerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createGardenerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateGardenerForm createGardenerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("gardener/create");
		result.addObject("createGardenerForm", createGardenerForm);
		result.addObject("requestURI", "gardener/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateGardenerForm createGardenerForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createGardenerForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateGardenerForm createGardenerForm, final String message) {
		ModelAndView result;

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("gardener/edit");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("createGardenerForm", createGardenerForm);
		result.addObject("requestURI", "gardener/edit.do");
		result.addObject("message", message);

		return result;
	}

}
