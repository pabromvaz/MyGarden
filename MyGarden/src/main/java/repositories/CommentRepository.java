
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select co from Comment co where co.wateringArea.id=?1")
	Collection<Comment> findAllOfAWateringArea(Integer wateringAreaId);

}
