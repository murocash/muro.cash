package cash.muro.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cash.muro.entities.MuroProduct;

public interface MuroProductRepository extends PagingAndSortingRepository<MuroProduct, String> {
	
	@Query("SELECT mp FROM MuroProduct mp "
			+ " WHERE (SELECT isDeleted FROM DeletedMuroProduct dp "
			+ "			WHERE mp.code = dp.entity.code "
			+ "			AND dp.when = (SELECT max(dmp.when) FROM DeletedMuroProduct dmp WHERE dmp.entity.code = mp.code)) IS FALSE "
			+ "			OR (SELECT max(dmpr.when) FROM DeletedMuroProduct dmpr WHERE dmpr.entity.code = mp.code) IS NULL")	
	public Iterable<MuroProduct> findAllNotDeleted();
}
