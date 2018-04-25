
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.Gardener;
import domain.WateringArea;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Comment findOne(final int commentId) {
		Comment result;

		result = this.commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();

		return result;
	}

	public Comment create(final WateringArea wateringArea) {
		Assert.notNull(wateringArea);
		Gardener gardener;
		Comment result;
		Calendar calendar;

		gardener = this.gardenerService.findByPrincipal();
		Assert.notNull(gardener);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Comment();
		result.setMoment(calendar.getTime());
		result.setGardener(gardener);
		result.setWateringArea(wateringArea);

		return result;
	}

	public Comment save(final Comment comment) {
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));
		Assert.notNull(comment);

		Comment result;

		result = this.commentRepository.save(comment);

		return result;
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(comment.getGardener()));

		this.commentRepository.delete(comment);
	}

	public void deleteWithWateringArea(final Comment comment) {
		Assert.notNull(comment);

		this.commentRepository.delete(comment);
	}

	// Other business methods -------------------------------------------------

	public Collection<Comment> findAllOfAWateringArea(final Integer wateringAreaId) {
		Collection<Comment> result;
		result = this.commentRepository.findAllOfAWateringArea(wateringAreaId);
		return result;
	}
}
