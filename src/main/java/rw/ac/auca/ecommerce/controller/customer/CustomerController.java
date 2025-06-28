package rw.ac.auca.ecommerce.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findCustomersByState(Boolean.TRUE);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable UUID id) {
        return customerService.findCustomerByIdAndState(id, Boolean.TRUE);
    }

    @PostMapping
    public Customer registerCustomer(@RequestBody Customer theCustomer) {
        return customerService.registerCustomer(theCustomer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable UUID id, @RequestBody Customer theCustomer) {
        theCustomer.setId(id);
        return customerService.updateCustomer(theCustomer);
    }

    @DeleteMapping("/{id}")
    public Customer deleteCustomer(@PathVariable UUID id) {
        Customer theCustomer = customerService.findCustomerByIdAndState(id, Boolean.TRUE);
        return customerService.deleteCustomer(theCustomer);
    }
}
