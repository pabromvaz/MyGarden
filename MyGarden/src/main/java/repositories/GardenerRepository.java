
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Gardener;

@Repository
public interface GardenerRepository extends JpaRepository<Gardener, Integer> {

	@Query("select g from Gardener g where g.userAccount.id = ?1")
	Gardener findByUserAccountId(int userAccountId);
}
