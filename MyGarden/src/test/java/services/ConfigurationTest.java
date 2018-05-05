
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private GardenerService			gardenerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private EventService			eventService;

	// Tests ------------------------------------------------------------------

}
