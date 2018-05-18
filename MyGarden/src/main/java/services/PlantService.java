
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PlantRepository;
import domain.Administrator;
import domain.Fertilizer;
import domain.Measurement;
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

	@Autowired
	private MeasurementService		measurementService;

	@Autowired
	private FertilizerService		fertilizerService;


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
		final Collection<Fertilizer> fertilizers = new ArrayList<Fertilizer>();

		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Plant();
		result.setWateringAreas(wateringAreas);
		result.setFertilizers(fertilizers);
		return result;
	}

	public Plant save(final Plant plant) {
		Assert.notNull(plant);
		Plant result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(plant.getMaxTemperature() >= plant.getMinTemperature());

		result = this.plantRepository.save(plant);
		return result;
	}

	public Collection<Plant> findRecommendedPlants(final WateringArea wateringArea) {
		Collection<Plant> result = new ArrayList<Plant>();
		final Collection<Measurement> measurements = wateringArea.getMeasurements();
		Double moistureAverage = 0.0;
		//Double humidityAverage = 0.0;
		Double temperatureAverage = 0.0;
		//Double lightAverage = 0.0;
		Double phAverage = 0.0;
		//Double nitrogenAverage = 0.0;
		//Double phosphorusAverage = 0.0;
		//Double potassiumAverage = 0.0;

		for (final Measurement me : measurements) {
			moistureAverage = moistureAverage + me.getMoisture();
			//humidityAverage = humidityAverage + me.getHumidity();
			temperatureAverage = temperatureAverage + me.getTemperature();
			//lightAverage = lightAverage + me.getLight();
			phAverage = phAverage + me.getPh();
			//nitrogenAverage = nitrogenAverage + me.getNitrogen();
			//phosphorusAverage = phosphorusAverage + me.getPhosphorus();
			//potassiumAverage = potassiumAverage + me.getPotassium();
		}

		moistureAverage = moistureAverage / measurements.size();
		//humidityAverage = humidityAverage / measurements.size();
		temperatureAverage = temperatureAverage / measurements.size();
		//lightAverage = lightAverage / measurements.size();
		phAverage = phAverage / measurements.size();
		//nitrogenAverage = nitrogenAverage / measurements.size();
		//phosphorusAverage = phosphorusAverage / measurements.size();
		//potassiumAverage = potassiumAverage / measurements.size();

		result = this.plantRepository.findAll();

		return result;
	}

	public void delete(final Plant plant) {
		Assert.notNull(plant);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(plant.getWateringAreas().isEmpty());

		final Collection<Fertilizer> fertilizers = plant.getFertilizers();
		for (final Fertilizer fe : fertilizers)
			this.fertilizerService.deletePlant(fe, plant);

		this.plantRepository.delete(plant);
	}

	// Other business methods -------------------------------------------------

}
