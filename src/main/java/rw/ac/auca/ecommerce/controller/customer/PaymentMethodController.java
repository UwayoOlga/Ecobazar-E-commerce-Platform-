package rw.ac.auca.ecommerce.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.customer.model.PaymentMethod;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.service.IPaymentMethodService;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers/payment-methods")
public class PaymentMethodController {
    private final IPaymentMethodService paymentMethodService;
    private final ICustomerService customerService;

    @GetMapping
    public List<PaymentMethod> getPaymentMethods(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return List.of();
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        return paymentMethodService.getPaymentMethodsForCustomer(customer);
    }

    @PostMapping
    public PaymentMethod addPaymentMethod(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PaymentMethod paymentMethod) {
        if (userDetails == null) return null;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        paymentMethod.setCustomer(customer);
        return paymentMethodService.savePaymentMethod(paymentMethod);
    }

    @PutMapping("/{id}")
    public PaymentMethod updatePaymentMethod(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody PaymentMethod paymentMethod) {
        if (userDetails == null) return null;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        paymentMethod.setId(id);
        paymentMethod.setCustomer(customer);
        return paymentMethodService.savePaymentMethod(paymentMethod);
    }

    @DeleteMapping("/{id}")
    public void deletePaymentMethod(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (userDetails == null) return;
        paymentMethodService.deletePaymentMethod(id);
    }

    @PostMapping("/{id}/default")
    public PaymentMethod setDefaultPaymentMethod(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (userDetails == null) return null;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        return paymentMethodService.setDefaultPaymentMethod(customer, id);
    }
} 