package cash.muro.repos;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cash.muro.entities.AccessedResource;
import cash.muro.entities.AccessedResourceId;

@Repository
public interface AccessedResourceRepository extends JpaRepository<AccessedResource, AccessedResourceId> {
	
	public boolean existsByUserIdAndUriAndBought(String userId, URI uri, boolean bought);
	
	@Query ("SELECT (SELECT freePages FROM MuroSettings) - COUNT(*) "
			+ " FROM AccessedResource "
			+ " WHERE userId = ?1 "
			+ " AND DATEDIFF('DAY', accessedTime, CURRENT_TIMESTAMP()) < (SELECT daysFree FROM MuroSettings) AND bought = false " )
	public int getFreeBalance(String userId);
	
	@Query ("SELECT NVL(SUM(mpr.amount), 0) - (SELECT COUNT(*) FROM AccessedResource WHERE userId = ?1 AND bought = true ) "
			+ " FROM MuroPayment mpy "
			+ " INNER JOIN MuroCheckout mch ON  mpy.receiver = mch.receiver "
			+ " INNER JOIN MuroProduct mpr ON mpr.code = mch.product "
			+ " WHERE sender = ?1 ")
	public int getBoughtBalance(String userId);

	public List<AccessedResource> findAllByUserIdOrderByAccessedTimeDesc(String userId, Pageable pageable);

	public long countByUserId(String userId);
}
