package rw.ac.auca.ecommerce.core.customer.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.repository.ICustomerRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Collections;
 
@Service
@RequiredArgsConstructor
@Slf4j
//@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService, UserDetailsService {

    private final ICustomerRepository customerRepository;

 

    @Override
    public Customer registerCustomer(Customer theCustomer) {
        return customerRepository.save(theCustomer);
    }

    @Override
    public Customer updateCustomer(Customer theCustomer) {
        Customer found = findCustomerByIdAndState(theCustomer.getId() , Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setFirstName(theCustomer.getFirstName());
            ///....provide all attributes
            return customerRepository.save(found);
        }
        throw new ObjectNotFoundException(Customer.class , "Customer Object not found");
    }

    @Override
    public Customer deleteCustomer(Customer theCustomer) {
        Customer found = findCustomerByIdAndState(theCustomer.getId() , Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setActive(Boolean.FALSE);
            return customerRepository.save(found);
        }
        throw new ObjectNotFoundException(Customer.class , "Customer Object not found");
    }

    @Override
    public Customer findCustomerByIdAndState(UUID id, Boolean state) {
        Customer theCustomer = customerRepository.findByIdAndActive(id ,state)
                .orElseThrow(() -> new ObjectNotFoundException(Customer.class , "Customer not found"));
        return theCustomer;
    }

    @Override
    public Customer findCustomerByEmailAndState(String email, Boolean state) {
        Customer theCustomer = customerRepository.findByEmailAndActive(email ,state)
                .orElseThrow(() -> new ObjectNotFoundException(Customer.class , "Customer not found"));
        return theCustomer;
    }

    @Override
    public List<Customer> findCustomersByState(Boolean state) {
        return customerRepository.findAllByActive(state);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmailAndActive(email, true)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(customer.getEmail(), customer.getPassword(), Collections.emptyList());
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmailAndActive(email, Boolean.TRUE).orElse(null);
    }
}
