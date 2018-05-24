
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query("select event from Event event where event.wateringArea.gardener.id=?1")
	Collection<Event> findAllFromGardener(int gardenerId);

	@Query("select event from Event event where event.wateringArea.gardener.id=?1 and event.readed=false")
	Collection<Event> findAllNotReadedFromGardener(int gardenerId);

}
