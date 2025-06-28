package rw.ac.auca.ecommerce.core.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The class IProductRepository.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@Repository
public interface IProductRepository extends JpaRepository<Product , UUID> {
    Optional<Product> findByIdAndActive(UUID uuid , Boolean active);
    List<Product> findAllByActive(Boolean active);
    List<Product> findAllByStockStateAndActive(EStockState state , Boolean active);
    List<Product> findALlByStockStateInAndActive(List<EStockState> states , Boolean active);


}
