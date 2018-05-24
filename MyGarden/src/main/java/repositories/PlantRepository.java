
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer> {

	@Query("select plant from Plant plant where plant.humidity>:humidity and plant.minTemperature<:temperature and plant.maxTemperature>:temperature")
	Collection<Plant> findRecommendedPlant(@Param("humidity") Double humidityAverage, @Param("temperature") Double temperatureAverage);

}
