
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FertilizerRepository;
import domain.Administrator;
import domain.Fertilizer;

@Service
@Transactional
public class FertilizerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FertilizerRepository	fertilizerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public FertilizerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Fertilizer findOne(final int fertilizerId) {
		Fertilizer result;

		result = this.fertilizerRepository.findOne(fertilizerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Fertilizer> findAll() {
		Collection<Fertilizer> result;

		result = this.fertilizerRepository.findAll();

		return result;
	}

	public Fertilizer create() {
		Fertilizer result;

		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Fertilizer();
		return result;
	}

	public Fertilizer save(final Fertilizer fertilizer) {
		Assert.notNull(fertilizer);
		Fertilizer result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fertilizerRepository.save(fertilizer);
		return result;
	}

	public void delete(final Fertilizer fertilizer) {
		Assert.notNull(fertilizer);
		Assert.notNull(this.administratorService.findByPrincipal());

		this.fertilizerRepository.delete(fertilizer);
	}

	// Other business methods -------------------------------------------------
}
