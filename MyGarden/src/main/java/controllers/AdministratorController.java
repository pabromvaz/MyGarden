/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Administrator;
import forms.CreateAdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateAdministratorForm createAdministratorForm;

		createAdministratorForm = new CreateAdministratorForm();
		result = this.createEditModelAndView(createAdministratorForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateAdministratorForm createAdministratorForm, final BindingResult binding) {

		ModelAndView result;
		Administrator administrator;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createAdministratorForm);
		else
			try {
				administrator = this.administratorService.reconstructProfile(createAdministratorForm, "create");
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				if (!createAdministratorForm.getPassword().equals(createAdministratorForm.getConfirmPassword()))
					result = this.createEditModelAndView(createAdministratorForm, "administrator.commit.error.password");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.createEditModelAndView(createAdministratorForm, "administrator.commit.error.duplicate");
				else
					result = this.createEditModelAndView(createAdministratorForm, "administrator.commit.error");

			}
		return result;
	}
	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreateAdministratorForm createAdministratorForm;
		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		createAdministratorForm = this.administratorService.constructProfile(administrator);
		result = this.editionEditModelAndView(createAdministratorForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateAdministratorForm createAdministratorForm, final BindingResult binding) {

		ModelAndView result;
		Administrator administrator;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createAdministratorForm);
		else
			try {
				administrator = this.administratorService.reconstructProfile(createAdministratorForm, "edit");
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/profile/myProfile.do");

			} catch (final Throwable oops) {
				if (!createAdministratorForm.getPassword().equals(createAdministratorForm.getConfirmPassword()))
					result = this.editionEditModelAndView(createAdministratorForm, "administrator.commit.error.password");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.editionEditModelAndView(createAdministratorForm, "administrator.commit.error.duplicate");
				else
					result = this.editionEditModelAndView(createAdministratorForm, "administrator.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateAdministratorForm createAdministratorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createAdministratorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateAdministratorForm createAdministratorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("createAdministratorForm", createAdministratorForm);
		result.addObject("requestURI", "administrator/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateAdministratorForm createAdministratorForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createAdministratorForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateAdministratorForm createAdministratorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("createAdministratorForm", createAdministratorForm);
		result.addObject("requestURI", "administrator/edit.do");
		result.addObject("message", message);

		return result;
	}

}
