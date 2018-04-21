
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageEmail;

@Repository
public interface MessageEmailRepository extends JpaRepository<MessageEmail, Integer> {

	@Query("select me from MessageEmail me where me.sender.id=?1 and me.deletedForSender=false")
	Collection<MessageEmail> findMessagesSentByActorId(int actorId);

	@Query("select me from MessageEmail me where me.recipient.id=?1 and me.deletedForRecipient=false")
	Collection<MessageEmail> findMessagesReceivedByActorId(int actorId);

}
