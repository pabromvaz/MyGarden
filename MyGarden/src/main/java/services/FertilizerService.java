
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FertilizerRepository;
import security.Authority;
import domain.Administrator;
import domain.Fertilizer;
import domain.Plant;

@Service
@Transactional
public class FertilizerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FertilizerRepository	fertilizerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PlantService			plantService;


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
		final Collection<Plant> plants = new ArrayList<Plant>();
		result.setPlants(plants);
		return result;
	}

	public Fertilizer save(final Fertilizer fertilizer) {
		Assert.notNull(fertilizer);
		Fertilizer result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		if (this.actorService.checkAuthority(principal, Authority.ADMIN))
			Assert.isTrue(fertilizer.getPlants().isEmpty());
		else
			Assert.isTrue(this.fertilizerRepository.findOne(fertilizer.getId()).getName().equals(fertilizer.getName()));

		result = this.fertilizerRepository.save(fertilizer);
		return result;
	}

	public void delete(final Fertilizer fertilizer) {
		Assert.notNull(fertilizer);
		Assert.isTrue(fertilizer.getId() != 0);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(fertilizer.getPlants().size() == 0);

		this.fertilizerRepository.delete(fertilizer);
	}

	// Other business methods -------------------------------------------------

	//Este método no realiza un save por lo que se tendrá que hacer en el controlador
	//Además se deberá realizar otro save para cada fertilizante
	//No se realiza por si se añade un fertilizante al crear una planta
	public Plant addFertilizerWoS(final Fertilizer fertilizer, final Plant plant) {
		Collection<Fertilizer> fertilizers;

		Assert.notNull(fertilizer);
		Assert.notNull(plant);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(!plant.getFertilizers().contains(fertilizer));

		fertilizers = plant.getFertilizers();
		fertilizers.add(fertilizer);
		plant.setFertilizers(fertilizers);

		return plant;
	}

	//Leer las notaciones del método addFertilizerWoS
	public Plant deleteFertilizerWoS(final Fertilizer fertilizer, final Plant plant) {
		Collection<Fertilizer> categories;

		Assert.notNull(fertilizer);
		Assert.notNull(plant);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(plant.getFertilizers().contains(fertilizer));

		categories = plant.getFertilizers();
		categories.remove(fertilizer);
		plant.setFertilizers(categories);

		return plant;
	}

	public void addFertilizer(final Fertilizer fertilizer, final Plant plant) {
		Collection<Fertilizer> categories;
		Collection<Plant> plants;

		Assert.notNull(fertilizer);
		Assert.notNull(plant);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(!plant.getFertilizers().contains(fertilizer));

		categories = plant.getFertilizers();
		categories.add(fertilizer);
		plant.setFertilizers(categories);
		this.plantService.save(plant);

		Assert.isTrue(!fertilizer.getPlants().contains(plant));

		plants = fertilizer.getPlants();
		plants.add(plant);
		fertilizer.setPlants(plants);
		this.save(fertilizer);
	}

	public void deleteFertilizer(final Fertilizer fertilizer, final Plant plant) {
		Collection<Fertilizer> categories;
		Collection<Plant> plants;

		Assert.notNull(fertilizer);
		Assert.notNull(plant);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(plant.getFertilizers().contains(fertilizer));

		categories = plant.getFertilizers();
		categories.remove(fertilizer);
		plant.setFertilizers(categories);
		this.plantService.save(plant);

		Assert.isTrue(fertilizer.getPlants().contains(plant));

		plants = fertilizer.getPlants();
		plants.remove(plant);
		fertilizer.setPlants(plants);
		this.save(fertilizer);
	}

	public void deletePlant(final Fertilizer fertilizer, final Plant plant) {
		Assert.notNull(fertilizer);
		Assert.notNull(plant);
		fertilizer.getPlants().remove(plant);
		this.fertilizerRepository.save(fertilizer);
	}

	public Collection<Fertilizer> select(final Collection<Fertilizer> fertilizers, final Plant plant) {
		Assert.notNull(plant);

		Administrator administrator;
		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		final Collection<Fertilizer> allFertilizers = this.fertilizerRepository.findAll();
		Collection<Plant> plants;
		for (final Fertilizer c : allFertilizers) {
			plants = c.getPlants();
			if (plants.contains(plant)) {
				plants.remove(plant);
				c.setPlants(plants);
				this.fertilizerRepository.save(c);
			}
		}

		for (Fertilizer fertilizer : fertilizers) {
			Assert.notNull(fertilizer);
			fertilizer.addPlant(plant);
			fertilizer = this.fertilizerRepository.save(fertilizer);
		}

		return fertilizers;
	}
}
