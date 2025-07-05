package rw.ac.auca.ecommerce.core.customer.service;

import rw.ac.auca.ecommerce.core.customer.model.PaymentMethod;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;

public interface IPaymentMethodService {
    List<PaymentMethod> getPaymentMethodsForCustomer(Customer customer);
    PaymentMethod getPaymentMethod(Long id);
    PaymentMethod savePaymentMethod(PaymentMethod paymentMethod);
    void deletePaymentMethod(Long id);
    PaymentMethod setDefaultPaymentMethod(Customer customer, Long paymentMethodId);
} 