package rw.ac.auca.ecommerce.core.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.customer.model.Address;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomer(Customer customer);
    Optional<Address> findByCustomerAndIsDefaultTrue(Customer customer);
} 