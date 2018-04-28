
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PlantRepository;
import domain.Administrator;
import domain.Plant;
import domain.WateringArea;

@Service
@Transactional
public class PlantService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PlantRepository			plantRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public PlantService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Plant findOne(final int plantId) {
		Plant result;

		result = this.plantRepository.findOne(plantId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Plant> findAll() {
		Collection<Plant> result;

		result = this.plantRepository.findAll();

		return result;
	}

	public Plant create() {
		Plant result;

		final Collection<WateringArea> wateringAreas = new ArrayList<WateringArea>();

		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Plant();
		result.setWateringAreas(wateringAreas);
		return result;
	}

	public Plant save(final Plant plant) {
		Assert.notNull(plant);
		Plant result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.plantRepository.save(plant);
		return result;
	}

	public void delete(final Plant plant) {
		Assert.notNull(plant);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(plant.getWateringAreas().isEmpty());
		this.plantRepository.delete(plant);
	}

	// Other business methods -------------------------------------------------

}
