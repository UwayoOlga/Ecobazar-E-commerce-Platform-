package rw.ac.auca.ecommerce.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.service.IProductService;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

import java.util.List;
import java.util.UUID;

 
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findProductsByState(Boolean.TRUE);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID id) {
        return productService.findProductByIdAndState(id, Boolean.TRUE);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product theProduct) {
        return productService.createProduct(theProduct);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product theProduct) {
        theProduct.setId(id);
        return productService.updateProduct(theProduct);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable UUID id) {
        Product theProduct = productService.findProductByIdAndState(id, Boolean.TRUE);
        return productService.deleteProduct(theProduct);
    }

    @GetMapping("/stock/{state}")
    public List<Product> getProductsByStockState(@PathVariable EStockState state) {
        return productService.findProductsByStockStateAndState(state, Boolean.TRUE);
    }
}
