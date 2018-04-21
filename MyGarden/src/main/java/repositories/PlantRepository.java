
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer> {

}
