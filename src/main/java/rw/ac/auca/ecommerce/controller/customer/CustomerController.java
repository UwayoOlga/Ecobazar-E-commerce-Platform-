package rw.ac.auca.ecommerce.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.model.CustomerRegistrationDto;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;
import rw.ac.auca.ecommerce.core.customer.model.Address;
import rw.ac.auca.ecommerce.core.customer.service.IAddressService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final IAddressService addressService;

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

    @GetMapping("/customer/register")
    public String showCustomerRegisterPage(Model model) {
        model.addAttribute("customerRegistrationDto", new CustomerRegistrationDto());
        return "customer/customerRegistrationPage";
    }

    @PostMapping("/customer/register")
    public String processCustomerRegistration(@ModelAttribute("customerRegistrationDto") CustomerRegistrationDto registrationDto, Model model) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "customer/customerRegistrationPage";
        }
        Customer customer = new Customer();
        customer.setFirstName(registrationDto.getFirstName());
        customer.setLastName(registrationDto.getLastName());
        customer.setEmail(registrationDto.getEmail());
        customer.setPhoneNumber(registrationDto.getPhoneNumber());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        customerService.registerCustomer(customer);
        model.addAttribute("message", "Customer registered successfully!");
        return "customer/customerRegistrationPage";
    }

    // Get current logged-in customer
    @GetMapping("/me")
    public Customer getCurrentCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return null;
        return customerService.findCustomerByEmail(userDetails.getUsername());
    }

    // Update current logged-in customer
    @PutMapping("/me")
    public Customer updateCurrentCustomer(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Customer updated) {
        if (userDetails == null) return null;
        Customer existing = customerService.findCustomerByEmail(userDetails.getUsername());
        if (existing == null) return null;
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setPhoneNumber(updated.getPhoneNumber());
        // Do not update email or password here for security
        return customerService.updateCustomer(existing);
    }

    // --- Address Management ---
    @GetMapping("/addresses")
    public List<Address> getAddresses(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return List.of();
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        return addressService.getAddressesForCustomer(customer);
    }

    @PostMapping("/addresses")
    public Address addAddress(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Address address) {
        if (userDetails == null) return null;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        address.setCustomer(customer);
        return addressService.saveAddress(address);
    }

    @PutMapping("/addresses/{id}")
    public Address updateAddress(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody Address address) {
        if (userDetails == null) return null;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        address.setId(id);
        address.setCustomer(customer);
        return addressService.saveAddress(address);
    }

    @DeleteMapping("/addresses/{id}")
    public void deleteAddress(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (userDetails == null) return;
        addressService.deleteAddress(id);
    }

    @PostMapping("/addresses/{id}/default")
    public Address setDefaultAddress(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (userDetails == null) return null;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        return addressService.setDefaultAddress(customer, id);
    }
}
