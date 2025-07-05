package rw.ac.auca.ecommerce.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.customer.model.Notification;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.service.INotificationService;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers/notifications")
public class NotificationController {
    private final INotificationService notificationService;
    private final ICustomerService customerService;

    @GetMapping
    public List<Notification> getNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return List.of();
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        return notificationService.getNotificationsForCustomer(customer);
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return List.of();
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        return notificationService.getUnreadNotifications(customer);
    }

    @GetMapping("/type/{type}")
    public List<Notification> getNotificationsByType(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String type) {
        if (userDetails == null) return List.of();
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        return notificationService.getNotificationsByType(customer, type);
    }

    @PostMapping
    public Notification addNotification(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Notification notification) {
        if (userDetails == null) return null;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        notification.setCustomer(customer);
        return notificationService.saveNotification(notification);
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }

    @PutMapping("/markAllAsRead")
    public void markAllAsRead(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return;
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        notificationService.markAllAsRead(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
} 