
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Prediction;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Integer> {

	@Query("select pre from Prediction pre where pre.wateringArea.id = ?1 order by pre.moment DESC")
	public List<Prediction> predictionsOfWateringAreaOrderedByMoment(int wateringAreaId);

}
