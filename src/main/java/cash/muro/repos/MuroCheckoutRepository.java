package cash.muro.repos;

import org.springframework.data.repository.CrudRepository;

import cash.muro.entities.MuroCheckout;

public interface MuroCheckoutRepository extends CrudRepository<MuroCheckout, String> {

}
