
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.EventService;
import services.MessageEmailService;
import domain.Actor;
import domain.Event;
import domain.MessageEmail;

@Controller
@RequestMapping("/messageEmail")
public class MessageEmailController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageEmailService	messageEmailService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private EventService		eventService;


	// Constructors -----------------------------------------------------------

	public MessageEmailController() {
		super();
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/listOut", method = RequestMethod.GET)
	public ModelAndView listIn() {
		ModelAndView result;
		Collection<MessageEmail> messageEmails;
		final Actor actor = this.actorService.findByPrincipal();

		messageEmails = this.messageEmailService.findMessageEmailsSentByActorId(actor.getId());

		result = new ModelAndView("messageEmail/listOut");

		final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("messageEmails", messageEmails);

		return result;
	}

	@RequestMapping(value = "/listIn", method = RequestMethod.GET)
	public ModelAndView listOut() {
		ModelAndView result;
		Collection<MessageEmail> messageEmails;
		final Actor actor = this.actorService.findByPrincipal();

		messageEmails = this.messageEmailService.findMessageEmailsReceivedByActorId(actor.getId());

		result = new ModelAndView("messageEmail/listIn");

		final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("messageEmails", messageEmails);

		return result;
	}

	@RequestMapping(value = "/listArchived", method = RequestMethod.GET)
	public ModelAndView listArchived() {
		ModelAndView result;
		Collection<MessageEmail> messageEmails;

		final Actor actor = this.actorService.findByPrincipal();
		messageEmails = this.messageEmailService.findArchivedMessageEmailsByActorId(actor.getId());

		result = new ModelAndView("messageEmail/listArchived");

		final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("messageEmails", messageEmails);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageEmailId) {
		ModelAndView result;
		MessageEmail messageEmail;
		Boolean isRecipient = false;
		Boolean canBeArchived = false;
		final Actor actor = this.actorService.findByPrincipal();

		messageEmail = this.messageEmailService.findOne(messageEmailId);

		/* Seguridad */
		if (!messageEmail.getRecipient().equals(actor) && !messageEmail.getSender().equals(actor))
			return result = new ModelAndView("redirect:../welcome/index.do");
		else if (messageEmail.getSender().equals(actor) && messageEmail.getDeletedForSender() == true)
			return result = new ModelAndView("redirect:../welcome/index.do");
		else if (messageEmail.getRecipient().equals(actor) && messageEmail.getDeletedForRecipient() == true)
			return result = new ModelAndView("redirect:../welcome/index.do");
		else {
			if (messageEmail.getRecipient().equals(actor))
				isRecipient = true;
			//Compruebo si se puede archivar.
			if ((messageEmail.getRecipient().equals(actor) && messageEmail.getArchivedForRecipient() == false) || (messageEmail.getSender().equals(actor) && messageEmail.getArchivedForSender() == false))
				canBeArchived = true;
			result = new ModelAndView("messageEmail/display");

			final Boolean isGardener = this.actorService.checkAuthority(actor, Authority.GARDENER);
			if (isGardener) {
				final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
				result.addObject("eventsNotReaded", eventsNotReaded.size());
			}

			result.addObject("messageEmail", messageEmail);
			result.addObject("isRecipient", isRecipient);
			result.addObject("canBeArchived", canBeArchived);
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int actorId) {
		ModelAndView result;
		final Actor recipient = this.actorService.findOne(actorId);

		final MessageEmail messageEmail = this.messageEmailService.create(recipient);
		result = this.createEditModelAndView(messageEmail);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveResponse(@Valid final MessageEmail messageEmail, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageEmail);
		else
			try {
				this.messageEmailService.save(messageEmail);
				result = new ModelAndView("redirect:../messageEmail/listOut.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageEmail, "messageEmail.commit.error");
			}

		return result;
	}

	//Reply ------------------------------------------------------------------------
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int messageEmailId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final MessageEmail messageEmailRequest = this.messageEmailService.findOne(messageEmailId);

		/* Seguridad */
		if (!messageEmailRequest.getRecipient().equals(actor) && !messageEmailRequest.getSender().equals(actor))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		/*----*/
		else {
			final MessageEmail messageEmail = this.messageEmailService.reply(messageEmailRequest);
			result = this.createEditModelAndView(messageEmail);
		}

		return result;
	}

	//Archive ------------------------------------------------------------------------
	@RequestMapping(value = "/archive", method = RequestMethod.POST, params = "archive")
	public ModelAndView archive(final MessageEmail messageEmail, final BindingResult binding) {
		ModelAndView result;

		//final MessageEmail messageEmail = this.messageEmailService.findOne(messageEmailId);
		final Actor actor = this.actorService.findByPrincipal();

		try {
			//for (final Taste t : this.tasteService.findAll())
			if ((messageEmail.getSender().equals(actor) && messageEmail.getDeletedForSender() == false) || (messageEmail.getRecipient().equals(actor) && messageEmail.getDeletedForRecipient() == false))
				this.messageEmailService.change(messageEmail, true);
			result = new ModelAndView("redirect:/messageEmail/listArchived.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/messageEmail/listArchived.do");
		}

		return result;
	}

	//Delete ------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MessageEmail messageEmail, final BindingResult binding) {
		ModelAndView result;

		//final MessageEmail messageEmail = this.messageEmailService.findOne(messageEmailId);
		final Actor actor = this.actorService.findByPrincipal();

		/* Seguridad */
		if (!messageEmail.getRecipient().equals(actor) && !messageEmail.getSender().equals(actor))
			return result = new ModelAndView("redirect:../welcome/index.do");

		else
			try {
				this.messageEmailService.delete(messageEmail);
				if (messageEmail.getRecipient().equals(actor))
					result = new ModelAndView("redirect:../messageEmail/listIn.do");
				else
					result = new ModelAndView("redirect:../messageEmail/listOut.do");
			} catch (final Throwable oops) {
				if (messageEmail.getRecipient().equals(actor))
					result = new ModelAndView("redirect:../messageEmail/listIn.do");
				else
					result = new ModelAndView("redirect:../messageEmail/listOut.do");
			}
		return result;
	}

	//Ancillary methods---------------------------------------------------------

	//Create --------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final MessageEmail messageEmail) {
		final ModelAndView result = this.createEditModelAndView(messageEmail, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageEmail messageEmail, final String message) {
		ModelAndView result;

		//final Collection<Actor> recipients;

		result = new ModelAndView("messageEmail/create");

		final Boolean isGardener = this.actorService.checkAuthority(this.actorService.findByPrincipal(), Authority.GARDENER);
		if (isGardener) {
			final Collection<Event> eventsNotReaded = this.eventService.findAllNotReadedFromGardener();
			result.addObject("eventsNotReaded", eventsNotReaded.size());
		}

		result.addObject("messageEmail", messageEmail);

		result.addObject("message", message);
		return result;
	}

}
