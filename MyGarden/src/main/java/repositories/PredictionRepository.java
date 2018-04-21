
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Prediction;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Integer> {

}
