package cash.muro.services;

import cash.muro.entities.MuroProduct;

public interface MuroProductService {

	Object findAll();

	MuroProduct save(MuroProduct product);

	void delete(String productCode);

}
