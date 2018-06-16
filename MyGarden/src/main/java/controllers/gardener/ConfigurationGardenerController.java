
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
import services.ConfigurationService;
import services.EventService;
import services.GardenerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Event;
import domain.Gardener;

@Controller
@RequestMapping("/configuration/gardener")
public class ConfigurationGardenerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private EventService			eventService;


	// Constructors -----------------------------------------------------------

	public ConfigurationGardenerController() {
		super();
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Boolean isOwner = false;

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		final Configuration configuration = gardener.getConfiguration();
		if (gardener != null)
			isOwner = true;

		result = new ModelAndView("configuration/display");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("configuration", configuration);
		result.addObject("isOwner", isOwner);
		result.addObject("requestURI", "configuration/display.do");

		return result;
	}

	// Display Arduino----------------------------------------------------------------
	@RequestMapping(value = "/manualWateringCondition", method = RequestMethod.GET)
	public ModelAndView manualWateringCondition() {
		ModelAndView result;
		Boolean isOwner = false;

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		final Configuration configuration = gardener.getConfiguration();
		if (gardener != null)
			isOwner = true;

		result = new ModelAndView("configuration/manualWateringCondition");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("configuration", configuration);
		result.addObject("isOwner", isOwner);
		result.addObject("requestURI", "configuration/manualWateringCondition.do");

		return result;
	}

	//Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int configurationId) {
		ModelAndView result;
		Configuration configuration;
		Actor actor;
		Gardener gardener;
		configuration = this.configurationService.findOne(configurationId);

		actor = this.actorService.findByPrincipal();
		gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());

		if (gardener == null || !gardener.getConfiguration().equals(configuration))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		result = this.editModelAndView(configuration);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(configuration);
		else
			try {
				if (configuration.getId() != 0)
					configuration = this.configurationService.save(configuration);
				result = new ModelAndView("redirect:../../configuration/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(configuration, "configuration.commit.error");
			}

		return result;
	}

	// Activate manual watering ---------------------------------------------------------------

	@RequestMapping(value = "/activateManualWatering", method = RequestMethod.GET)
	public ModelAndView activateManualWatering() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.activateManualWatering(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	@RequestMapping(value = "/deactivateManualWatering", method = RequestMethod.GET)
	public ModelAndView deactivateManualWatering() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.deactivateManualWatering(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	// Activate/Deactivate intrusion warning ---------------------------------------------------------------

	@RequestMapping(value = "/activateIntrusionWarningEmail", method = RequestMethod.GET)
	public ModelAndView activateIntrusionWarningEmail() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.activateIntrusionWarningEmail(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	@RequestMapping(value = "/deactivateIntrusionWarningEmail", method = RequestMethod.GET)
	public ModelAndView deactivateIntrusionWarningEmail() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.deactivateIntrusionWarningEmail(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	// Activate/Deactivate fertilizer warning ---------------------------------------------------------------

	@RequestMapping(value = "/activateFertilizerWarningEmail", method = RequestMethod.GET)
	public ModelAndView activateFertilizerWarningEmail() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.activateFertilizerWarningEmail(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	@RequestMapping(value = "/deactivateFertilizerWarningEmail", method = RequestMethod.GET)
	public ModelAndView deactivateFertilizerWarningEmail() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.deactivateFertilizerWarningEmail(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	// Activate/Deactivate tank warning ---------------------------------------------------------------

	@RequestMapping(value = "/activateTankWarningEmail", method = RequestMethod.GET)
	public ModelAndView activateTankWarningEmail() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.activateTankWarningEmail(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	@RequestMapping(value = "/deactivateTankWarningEmail", method = RequestMethod.GET)
	public ModelAndView deactivateTankWarningEmail() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.deactivateTankWarningEmail(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	// Activate automatic watering ---------------------------------------------------------------

	@RequestMapping(value = "/activateAutomaticWatering", method = RequestMethod.GET)
	public ModelAndView activateAutomaticWatering() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.activateAutomaticWatering(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	@RequestMapping(value = "/deactivateAutomaticWatering", method = RequestMethod.GET)
	public ModelAndView deactivateAutomaticWatering() {
		ModelAndView result;
		Configuration configuration;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		configuration = gardener.getConfiguration();

		this.configurationService.deactivateAutomaticWatering(configuration);

		result = new ModelAndView("redirect:/configuration/gardener/display.do");
		return result;

	}

	// Ancillary methods ------------------------------------------------------

	//Edit
	protected ModelAndView editModelAndView(final Configuration configuration) {
		ModelAndView result;

		result = this.editModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Configuration configuration, final String message) {
		ModelAndView result;

		final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();

		result = new ModelAndView("configuration/edit");
		result.addObject("eventsNotReaded", eventsNotReaded.size());
		result.addObject("requestURI", "configuration/gardener/edit.do");
		result.addObject("configuration", configuration);
		result.addObject("message", message);

		return result;
	}
}
