package rw.ac.auca.ecommerce.core.customer.service;

import rw.ac.auca.ecommerce.core.customer.model.Address;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;

public interface IAddressService {
    List<Address> getAddressesForCustomer(Customer customer);
    Address getAddress(Long id);
    Address saveAddress(Address address);
    void deleteAddress(Long id);
    Address setDefaultAddress(Customer customer, Long addressId);
} 