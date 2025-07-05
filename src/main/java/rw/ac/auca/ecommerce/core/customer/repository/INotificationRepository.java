package rw.ac.auca.ecommerce.core.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.customer.model.Notification;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCustomer(Customer customer);
    List<Notification> findByCustomerAndType(Customer customer, String type);
    List<Notification> findByCustomerAndRead(Customer customer, boolean read);
} 