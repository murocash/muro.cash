package cash.muro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cash.muro.entities.DeletedMuroProduct;
import cash.muro.entities.MuroProduct;
import cash.muro.repos.DeletedMuroProductRepository;
import cash.muro.repos.MuroProductRepository;

@Service
public class MuroProductServiceImpl implements MuroProductService {

	@Autowired
	private MuroProductRepository productRepository;
	
	@Autowired
	private DeletedMuroProductRepository deletedProductRepository;
	
	@Override
	public Iterable<MuroProduct> findAll() {
		return productRepository.findAllNotDeleted();
	}

	@Override
	public MuroProduct save(MuroProduct product) {
		if (!productRepository.existsById(product.getCode())) {
			return productRepository.save(product);
		}
		if (deletedProductRepository.isDeleted(product.getCode())) {
			DeletedMuroProduct restoredProduct = new DeletedMuroProduct(product, false);
			deletedProductRepository.save(restoredProduct);
		}
		return product;
	}

	@Override
	public void delete(String productCode) {
		productRepository.findById(productCode).ifPresent(p -> deletedProductRepository.save(new DeletedMuroProduct(p)));
	}

}
