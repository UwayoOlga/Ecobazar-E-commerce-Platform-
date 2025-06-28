package rw.ac.auca.ecommerce.core.customer.service;

import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;
import java.util.UUID;

/**
 * The class ICustomerService.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
public interface ICustomerService {
    // method signature or definition
    Customer registerCustomer(Customer theCustomer);
    Customer updateCustomer(Customer theCustomer);
    Customer deleteCustomer(Customer theCustomer);
    Customer findCustomerByIdAndState(UUID id , Boolean state);
    Customer findCustomerByEmailAndState(String email , Boolean state);
    List<Customer> findCustomersByState(Boolean state);
}
