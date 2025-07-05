package rw.ac.auca.ecommerce.core.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.auca.ecommerce.core.customer.model.PaymentMethod;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.repository.IPaymentMethodRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements IPaymentMethodService {
    private final IPaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getPaymentMethodsForCustomer(Customer customer) {
        return paymentMethodRepository.findByCustomer(customer);
    }

    @Override
    public PaymentMethod getPaymentMethod(Long id) {
        return paymentMethodRepository.findById(id).orElse(null);
    }

    @Override
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod.isDefault()) {
            List<PaymentMethod> methods = paymentMethodRepository.findByCustomer(paymentMethod.getCustomer());
            for (PaymentMethod pm : methods) {
                if (pm.isDefault() && !pm.getId().equals(paymentMethod.getId())) {
                    pm.setDefault(false);
                    paymentMethodRepository.save(pm);
                }
            }
        }
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PaymentMethod setDefaultPaymentMethod(Customer customer, Long paymentMethodId) {
        List<PaymentMethod> methods = paymentMethodRepository.findByCustomer(customer);
        PaymentMethod newDefault = null;
        for (PaymentMethod pm : methods) {
            if (pm.getId().equals(paymentMethodId)) {
                pm.setDefault(true);
                newDefault = pm;
            } else {
                pm.setDefault(false);
            }
            paymentMethodRepository.save(pm);
        }
        return newDefault;
    }
} 