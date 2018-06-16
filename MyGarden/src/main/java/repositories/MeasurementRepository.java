
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

	@Query("select me from Measurement me where me.wateringArea.id = ?1 order by me.moment DESC")
	public List<Measurement> measurementsOfWateringAreaOrderedByMoment(int wateringAreaId);
}
