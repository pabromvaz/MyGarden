/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.EventService;
import services.WateringAreaService;
import domain.Actor;
import domain.Event;
import domain.WateringArea;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private EventService		eventService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Boolean isGardener = false;
		try {
			final Actor actor = this.actorService.findByPrincipal();
			isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);

		} catch (final Throwable oops) {
			//result = this.createEditModelAndView(messageEmail, "messageEmail.commit.error");
		}

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		final Collection<WateringArea> wateringAreas = this.wateringAreaService.findAllVisible();
		result = new ModelAndView("welcome/index");

		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("name", name);
		result.addObject("wateringAreas", wateringAreas);
		result.addObject("moment", moment);

		return result;
	}
}
