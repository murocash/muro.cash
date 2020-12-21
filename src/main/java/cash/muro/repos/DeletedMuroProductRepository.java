package cash.muro.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cash.muro.entities.DeletedMuroProduct;

public interface DeletedMuroProductRepository extends CrudRepository<DeletedMuroProduct, Long> {

	@Query("SELECT COALESCE(dp.isDeleted, FALSE) FROM DeletedMuroProduct dp "
			+ " WHERE dp.when = (SELECT Max(dmp.when) FROM DeletedMuroProduct dmp WHERE dmp.entity.code = :productCode) "
			+ " AND dp.entity.code = :productCode")
	boolean isDeleted(@Param("productCode") String productCode);
}
