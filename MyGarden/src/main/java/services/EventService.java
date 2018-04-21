
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EventRepository;
import domain.Event;
import domain.Gardener;
import domain.WateringArea;

@Service
@Transactional
public class EventService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private EventRepository		eventRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public EventService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Event findOne(final int eventId) {
		Event result;

		result = this.eventRepository.findOne(eventId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Event> findAll() {
		Collection<Event> result;

		result = this.eventRepository.findAll();

		return result;
	}

	public Event create(final WateringArea wateringArea, final String name, final String description) {
		Assert.notNull(wateringArea);
		Gardener gardener;
		Event result;
		Calendar calendar;

		gardener = this.gardenerService.findByPrincipal();
		Assert.notNull(gardener);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Event();
		result.setName(name);
		result.setDescription(description);
		result.setMoment(calendar.getTime());
		result.setWateringArea(wateringArea);

		return result;
	}

	public Event save(final Event event) {
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));
		Assert.notNull(event);

		Event result;

		result = this.eventRepository.save(event);

		return result;
	}

	public void delete(final Event event) {
		Assert.notNull(event);
		//Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));

		this.eventRepository.delete(event);
	}

	// Other business methods -------------------------------------------------
}
