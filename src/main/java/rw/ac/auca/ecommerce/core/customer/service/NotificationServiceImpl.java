package rw.ac.auca.ecommerce.core.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.auca.ecommerce.core.customer.model.Notification;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.repository.INotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final INotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsForCustomer(Customer customer) {
        return notificationRepository.findByCustomer(customer);
    }

    @Override
    public List<Notification> getNotificationsByType(Customer customer, String type) {
        return notificationRepository.findByCustomerAndType(customer, type);
    }

    @Override
    public List<Notification> getUnreadNotifications(Customer customer) {
        return notificationRepository.findByCustomerAndRead(customer, false);
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void markAllAsRead(Customer customer) {
        List<Notification> unread = notificationRepository.findByCustomerAndRead(customer, false);
        for (Notification n : unread) {
            n.setRead(true);
            notificationRepository.save(n);
        }
    }

    @Override
    public Notification markAsRead(Long id) {
        Notification n = notificationRepository.findById(id).orElse(null);
        if (n != null) {
            n.setRead(true);
            return notificationRepository.save(n);
        }
        return null;
    }
} 