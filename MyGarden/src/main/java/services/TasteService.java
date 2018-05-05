
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TasteRepository;
import domain.Gardener;
import domain.Taste;
import domain.WateringArea;

@Service
@Transactional
public class TasteService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TasteRepository		tasteRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Constructors -----------------------------------------------------------
	public TasteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Taste findOne(final int tasteId) {
		Taste result;

		result = this.tasteRepository.findOne(tasteId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Taste> findAll() {
		Collection<Taste> result;

		result = this.tasteRepository.findAll();

		return result;
	}

	public Taste createLike(final WateringArea wateringArea) {
		final Gardener gardener = this.gardenerService.findByPrincipal();

		Assert.notNull(wateringArea);
		Assert.notNull(gardener);

		final Taste result = new Taste();

		result.setLike(true);
		result.setGardener(gardener);
		result.setWateringArea(wateringArea);

		return result;
	}

	public Taste createDislike(final WateringArea wateringArea) {
		final Gardener gardener = this.gardenerService.findByPrincipal();

		Assert.notNull(wateringArea);
		Assert.notNull(gardener);

		final Taste result = new Taste();

		result.setLike(false);
		result.setGardener(gardener);
		result.setWateringArea(wateringArea);

		return result;
	}

	public Taste save(final Taste taste) {
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(taste.getGardener()));
		Assert.notNull(taste);

		Taste result;

		result = this.tasteRepository.save(taste);

		return result;
	}

	public Taste change(final Taste taste, final boolean li) {
		Assert.notNull(taste);
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(taste.getGardener()));

		taste.setLike(li);
		Taste result;
		result = this.tasteRepository.save(taste);
		return result;
	}

	public void delete(final Taste taste) {
		Assert.notNull(taste);
		Assert.notNull(this.gardenerService.findByPrincipal());
		Assert.isTrue(this.gardenerService.findByPrincipal().equals(taste.getGardener()));
		Assert.isTrue(taste.getId() != 0);

		this.tasteRepository.delete(taste);
	}

	public void deleteWithWateringArea(final Taste taste) {
		Assert.notNull(taste);
		Assert.isTrue(taste.getId() != 0);

		this.tasteRepository.delete(taste);
	}

	public Taste findTasteWithWateringAreaAndGardener(final Integer wateringAreaId, final Integer gardenerId) {
		final Taste result = this.tasteRepository.findTasteWithWateringAreaAndGardener(wateringAreaId, gardenerId);
		return result;
	}

	// Other business methods -------------------------------------------------

}
