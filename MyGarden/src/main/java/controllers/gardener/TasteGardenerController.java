
package controllers.gardener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.GardenerService;
import services.TasteService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Gardener;
import domain.Taste;
import domain.WateringArea;

@Controller
@RequestMapping("/taste/gardener")
public class TasteGardenerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private TasteService		tasteService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public TasteGardenerController() {
		super();
	}

	// Like ----------------------------------------------------------------
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public ModelAndView like(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		final Taste taste;
		boolean exist = false;
		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		final Gardener gardener = this.gardenerService.findByPrincipal();

		try {
			for (final Taste t : this.tasteService.findAll())
				if (t.getGardener().equals(gardener) && t.getWateringArea().equals(wateringArea)) {
					this.tasteService.change(t, true);
					exist = true;
					break;
				}
			if (exist == false) {
				taste = this.tasteService.createLike(wateringArea);
				this.tasteService.save(taste);
			}
			result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/wateringArea/list.do");
		}

		return result;
	}
	// Dislike ----------------------------------------------------------------
	@RequestMapping(value = "/dislike", method = RequestMethod.GET)
	public ModelAndView dislike(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		final Taste taste;
		boolean exist = false;
		final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		final Gardener gardener = this.gardenerService.findByPrincipal();

		try {
			for (final Taste t : this.tasteService.findAll())
				if (t.getGardener().equals(gardener) && t.getWateringArea().equals(wateringArea)) {
					this.tasteService.change(t, false);
					exist = true;
					break;
				}
			if (exist == false) {
				taste = this.tasteService.createDislike(wateringArea);
				this.tasteService.save(taste);
			}
			result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/wateringArea/list.do");
		}

		return result;
	}
}
