package rw.ac.auca.ecommerce.core.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.customer.model.PaymentMethod;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByCustomer(Customer customer);
    Optional<PaymentMethod> findByCustomerAndIsDefaultTrue(Customer customer);
} 