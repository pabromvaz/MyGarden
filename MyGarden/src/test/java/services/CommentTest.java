
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Comment;
import domain.WateringArea;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private GardenerService		gardenerService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private WateringAreaService	wateringAreaService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como cliente debe ser capaz de:
	//	Añadir comentarios a los juegos.

	//El primer test negativo es causado porque no nos hemos logueado correctamente como customer, el segundo de
	//ellos se produce porque le ponemos un score fuera del rango 0-10 y el tercero es provocado porque le
	//pasamos un id de game que no existe.
	@Test
	public void driverAddCommentToAWateringArea() {
		final Object testingData[][] = {
			{
				"gardener1", 17, "Titulo", "Descripcion", null
			}, {
				"gardener2", 19, "Titulo", "Descripcion", null
			}, {
				"gardener3", 20, "Titulo", "Descripcion", null
			}, {
				"gardenerNoExist", 20, "Titulo", "Descripcion", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AddCommentToAWateringArea((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void AddCommentToAWateringArea(final String username, final int wateringAreaId, final String title, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final WateringArea wateringArea = this.wateringAreaService.findOne(wateringAreaId);
			final Comment comment = this.commentService.create(wateringArea);
			comment.setTitle(title);
			comment.setDescription(description);
			this.commentService.save(comment);

			this.commentService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
