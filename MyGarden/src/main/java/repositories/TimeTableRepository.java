
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {

}