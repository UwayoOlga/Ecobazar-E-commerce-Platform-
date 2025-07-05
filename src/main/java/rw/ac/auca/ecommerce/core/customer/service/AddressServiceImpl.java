package rw.ac.auca.ecommerce.core.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.auca.ecommerce.core.customer.model.Address;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.repository.IAddressRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
    private final IAddressRepository addressRepository;

    @Override
    public List<Address> getAddressesForCustomer(Customer customer) {
        return addressRepository.findByCustomer(customer);
    }

    @Override
    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address saveAddress(Address address) {
        // If setting as default, unset previous default
        if (address.isDefault()) {
            List<Address> addresses = addressRepository.findByCustomer(address.getCustomer());
            for (Address addr : addresses) {
                if (addr.isDefault() && !addr.getId().equals(address.getId())) {
                    addr.setDefault(false);
                    addressRepository.save(addr);
                }
            }
        }
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Address setDefaultAddress(Customer customer, Long addressId) {
        List<Address> addresses = addressRepository.findByCustomer(customer);
        Address newDefault = null;
        for (Address addr : addresses) {
            if (addr.getId().equals(addressId)) {
                addr.setDefault(true);
                newDefault = addr;
            } else {
                addr.setDefault(false);
            }
            addressRepository.save(addr);
        }
        return newDefault;
    }
} 