
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Taste;

@Repository
public interface TasteRepository extends JpaRepository<Taste, Integer> {

	@Query("select t from Taste t where t.wateringArea=?1 and t.gardener=?1")
	Taste findTasteWithWateringAreaAndGardener(Integer wateringAreaId, Integer gardenerId);

}
