package rw.ac.auca.ecommerce.core.product.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.repository.IProductRepository;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

import java.util.List;
import java.util.UUID;

/**
 * The class ProductServiceImpl.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{

    private final IProductRepository productRepository;

    @Override
    public Product createProduct(Product theProduct) {
        return productRepository.save(theProduct);
    }

    @Override
    public Product updateProduct(Product theProduct) {
        return null;
    }

    @Override
    public Product deleteProduct(Product theProduct) {
        return null;
    }

    @Override
    public Product findProductByIdAndState(UUID id, Boolean active) {
        Product theProduct = productRepository.findByIdAndActive(id , active)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class , "Product not Found"));
        return theProduct;
    }

    @Override
    public List<Product> findProductsByState(Boolean active) {
        return productRepository.findAllByActive(active);
    }

    @Override
    public List<Product> findProductsByStockStateAndState(EStockState stockState, Boolean active) {
        return productRepository.findAllByStockStateAndActive(stockState,active);
    }

    @Override
    public List<Product> findProductsByStockStatesAndState(List<EStockState> stockStates, Boolean active) {
        return productRepository.findALlByStockStateInAndActive(stockStates,active);
    }
}
