
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
import services.CommentService;
import services.ConfigurationService;
import services.GardenerService;
import services.PlantService;
import services.TasteService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.Gardener;
import domain.Plant;
import domain.Taste;
import domain.WateringArea;

@Controller
@RequestMapping("/wateringArea/gardener")
public class WateringAreaGardenerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private WateringAreaService		wateringAreaService;

	@Autowired
	private PlantService			plantService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private TasteService			tasteService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CommentService			commentService;


	// Constructors -----------------------------------------------------------

	public WateringAreaGardenerController() {
		super();
	}

	// Activate valve ---------------------------------------------------------------

	@RequestMapping(value = "/activateValve", method = RequestMethod.GET)
	public ModelAndView activateValve(@RequestParam final Integer wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		Collection<Comment> comments;
		Boolean isOwner = false;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		final Collection<Taste> tastes = this.tasteService.findAll();

		if (gardener != null && wateringArea.getGardener().equals(gardener))
			isOwner = true;

		comments = this.commentService.findAllOfAWateringArea(wateringAreaId);

		this.wateringAreaService.activateValve(wateringArea);

		result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());
		return result;

	}

	// Deactivate valve ---------------------------------------------------------------

	@RequestMapping(value = "/deactivateValve", method = RequestMethod.GET)
	public ModelAndView deactivateValve(@RequestParam final Integer wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		Collection<Comment> comments;
		Boolean isOwner = false;
		final Actor actor = this.actorService.findByPrincipal();
		final Gardener gardener = this.gardenerService.findByUserAccount(actor.getUserAccount());
		wateringArea = this.wateringAreaService.findOne(wateringAreaId);

		final Collection<Taste> tastes = this.tasteService.findAll();

		if (gardener != null && wateringArea.getGardener().equals(gardener))
			isOwner = true;

		comments = this.commentService.findAllOfAWateringArea(wateringAreaId);

		this.wateringAreaService.deactivateValve(wateringArea);

		result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());

		return result;

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

		result = this.createModelAndView(wateringArea);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final WateringArea wateringArea, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(wateringArea);
		else
			try {
				this.wateringAreaService.save(wateringArea);
				result = new ModelAndView("redirect:../../wateringArea/list.do");

			} catch (final Throwable oops) {
				result = this.createModelAndView(wateringArea, "wateringArea.commit.error");

			}
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

		result = this.editModelAndView(wateringArea);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid WateringArea wateringArea, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(wateringArea);
		else
			try {
				if (wateringArea.getId() != 0)
					wateringArea = this.wateringAreaService.save(wateringArea);
				result = new ModelAndView("redirect:../../wateringArea/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(wateringArea, "wateringArea.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final WateringArea wateringArea, final BindingResult binding) {
		ModelAndView result;
		Actor actor;
		Gardener gardener;

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
	protected ModelAndView createModelAndView(final WateringArea wateringArea) {
		ModelAndView result;

		result = this.createModelAndView(wateringArea, null);

		return result;
	}

	protected ModelAndView createModelAndView(final WateringArea wateringArea, final String message) {
		ModelAndView result;

		Collection<Plant> plants;
		plants = this.plantService.findAll();

		result = new ModelAndView("wateringArea/create");
		result.addObject("requestURI", "wateringArea/gardener/create.do");
		result.addObject("wateringArea", wateringArea);
		result.addObject("plants", plants);
		result.addObject("message", message);

		return result;
	}

	//Edit
	protected ModelAndView editModelAndView(final WateringArea wateringArea) {
		ModelAndView result;

		result = this.editModelAndView(wateringArea, null);

		return result;
	}

	protected ModelAndView editModelAndView(final WateringArea wateringArea, final String message) {
		ModelAndView result;

		Collection<Plant> plants;
		plants = this.plantService.findAll();

		result = new ModelAndView("wateringArea/edit");
		result.addObject("requestURI", "wateringArea/gardener/edit.do");
		result.addObject("wateringArea", wateringArea);
		result.addObject("plants", plants);
		result.addObject("message", message);

		return result;
	}
}
