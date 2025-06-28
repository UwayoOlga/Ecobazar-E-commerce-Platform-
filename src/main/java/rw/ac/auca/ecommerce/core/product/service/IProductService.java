package rw.ac.auca.ecommerce.core.product.service;

import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

import java.util.List;
import java.util.UUID;

/**
 * The class IProductService.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
public interface IProductService {
    Product createProduct(Product theProduct);
    Product updateProduct(Product theProduct);
    Product deleteProduct(Product theProduct);
    Product findProductByIdAndState(UUID id , Boolean active);
    List<Product> findProductsByState(Boolean active);
    List<Product> findProductsByStockStateAndState(EStockState stockState , Boolean active);
    List<Product> findProductsByStockStatesAndState(List<EStockState> stockStates , Boolean active);

}
