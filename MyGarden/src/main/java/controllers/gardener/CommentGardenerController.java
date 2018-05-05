
package controllers.gardener;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.GardenerService;
import services.WateringAreaService;
import controllers.AbstractController;
import domain.Comment;
import domain.WateringArea;

@Controller
@RequestMapping("/comment/gardener")
public class CommentGardenerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CommentService		commentService;

	@Autowired
	private WateringAreaService	wateringAreaService;

	@Autowired
	private GardenerService		gardenerService;


	// Constructors -----------------------------------------------------------
	public CommentGardenerController() {
		super();
	}

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int wateringAreaId) {
		ModelAndView result;
		WateringArea wateringArea;
		Comment comment;

		wateringArea = this.wateringAreaService.findOne(wateringAreaId);
		comment = this.commentService.create(wateringArea);

		result = this.createEditModelAndView(comment);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (comment.getWateringArea() == null)
				result = this.createEditModelAndView(comment, "comment.commit.error.not.wateringArea");
			else
				result = this.createEditModelAndView(comment);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + comment.getWateringArea().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int commentId) {
		ModelAndView result;
		final Comment comment;
		final WateringArea wateringArea;

		comment = this.commentService.findOne(commentId);
		wateringArea = comment.getWateringArea();

		this.commentService.delete(comment);
		result = new ModelAndView("redirect:/wateringArea/display.do?wateringAreaId=" + wateringArea.getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("message", message);

		return result;
	}

}
