
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {

	@Query("select ti from TimeTable ti where ti.wateringArea.id = ?1 order by ti.activationMoment DESC")
	public List<TimeTable> timeTablesOfWateringAreaOrderedByMoment(int wateringAreaId);

}
