package cash.muro.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import cash.muro.entities.MuroPayment;

public interface MuroPaymentRepository extends CrudRepository<MuroPayment, String> {
	
	List<MuroPayment> findAllByCheckout_senderOrderByDateTimeDesc(String sender, Pageable pageable);
	
	long countByCheckout_sender(String sender);

}
