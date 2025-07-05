package rw.ac.auca.ecommerce.core.customer.service;

import rw.ac.auca.ecommerce.core.customer.model.Notification;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;

public interface INotificationService {
    List<Notification> getNotificationsForCustomer(Customer customer);
    List<Notification> getNotificationsByType(Customer customer, String type);
    List<Notification> getUnreadNotifications(Customer customer);
    Notification saveNotification(Notification notification);
    void deleteNotification(Long id);
    void markAllAsRead(Customer customer);
    Notification markAsRead(Long id);
} 