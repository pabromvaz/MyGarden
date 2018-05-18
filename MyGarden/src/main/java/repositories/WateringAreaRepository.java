
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WateringArea;

@Repository
public interface WateringAreaRepository extends JpaRepository<WateringArea, Integer> {

	@Query("select wa from WateringArea wa where wa.gardener.id=?1")
	Collection<WateringArea> findByGardenerId(Integer gardenerId);

	@Query("select wa from WateringArea wa where wa.visible=true")
	Collection<WateringArea> findAllVisible();

}
