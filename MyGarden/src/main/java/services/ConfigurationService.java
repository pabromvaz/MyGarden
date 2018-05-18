
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;
import domain.Event;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private EventService			eventService;


	// Constructors -----------------------------------------------------------
	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Configuration findOne(final int configurationId) {
		Configuration result;

		result = this.configurationRepository.findOne(configurationId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> result;

		result = this.configurationRepository.findAll();

		return result;
	}

	public Configuration create() {
		final Configuration result = new Configuration();

		/* Events */
		final Collection<Event> events = new ArrayList<Event>();
		result.setEvents(events);

		return result;

	}

	public Configuration saveRegister(final Configuration configuration) {
		Assert.notNull(configuration);
		configuration.setAutomaticWatering(false);
		configuration.setManualWatering(true);
		configuration.setIntrusionWarningActivated(false);
		configuration.setFertilizerWarningActivated(false);
		configuration.setTankWarningActivated(false);
		return this.configurationRepository.save(configuration);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(this.gardenerService.findByPrincipal().getConfiguration().getId() == configuration.getId());
		return this.configurationRepository.save(configuration);
	}

	// Other business methods -------------------------------------------------
	public Configuration addEventToConfiguration(final Event event) {
		Assert.notNull(event);
		Configuration configuration;
		Collection<Event> events;
		configuration = this.gardenerService.findByPrincipal().getConfiguration();

		events = configuration.getEvents();

		if (!events.contains(event))
			events.add(event);
		configuration.setEvents(events);
		configuration = this.save(configuration);

		return configuration;

	}
	public Configuration removeEventToConfiguration(final Event event) {
		Assert.notNull(event);
		Configuration configuration;
		Collection<Event> events;
		configuration = this.gardenerService.findByPrincipal().getConfiguration();

		events = configuration.getEvents();

		if (events.contains(event))
			events.remove(event);
		configuration.setEvents(events);
		configuration = this.save(configuration);

		return configuration;

	}

	public void activateManualWatering(final Configuration configuration) {

		//final Configuration configuration = this.gardenerService.findByPrincipal().getConfiguration();
		configuration.setManualWatering(true);
		configuration.setAutomaticWatering(false);
		this.configurationRepository.save(configuration);

	}

	public void activateAutomaticWatering(final Configuration configuration) {

		//final Configuration configuration = this.gardenerService.findByPrincipal().getConfiguration();
		configuration.setManualWatering(false);
		configuration.setAutomaticWatering(true);
		this.configurationRepository.save(configuration);

	}

	public void deactivateManualWatering(final Configuration configuration) {

		//final Configuration configuration = this.gardenerService.findByPrincipal().getConfiguration();
		configuration.setManualWatering(false);
		configuration.setAutomaticWatering(true);
		this.configurationRepository.save(configuration);

	}

	public void deactivateAutomaticWatering(final Configuration configuration) {

		//final Configuration configuration = this.gardenerService.findByPrincipal().getConfiguration();
		configuration.setManualWatering(true);
		configuration.setAutomaticWatering(false);
		this.configurationRepository.save(configuration);

	}

}
