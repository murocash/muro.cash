package cash.muro.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deleted_muro_product")
public class DeletedMuroProduct extends DeletedEntity<MuroProduct> {
	
	public DeletedMuroProduct(MuroProduct product) {
		super(product);
	}
	
	public DeletedMuroProduct(MuroProduct product, boolean isDeleted) {
		super(product, isDeleted);
	}

}
