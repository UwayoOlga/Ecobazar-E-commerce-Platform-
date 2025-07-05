package rw.ac.auca.ecommerce.core.customer.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String type; // e.g., "Order Update", "Prescription", "Promotion", "Account", "Reminder"
    private String title;
    private String message;
    private LocalDateTime dateTime;
    private boolean read;
    private String link; // URL or route to relevant page
} 