
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

}
