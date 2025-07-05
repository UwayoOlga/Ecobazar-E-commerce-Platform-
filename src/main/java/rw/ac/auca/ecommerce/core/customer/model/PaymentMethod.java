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
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String type; // e.g., "Visa", "Mobile Money", "PayPal"
    private String provider; // e.g., "MTN", "Airtel", "Mastercard"
    private String maskedDetails; // e.g., "**** **** **** 1234"
    private Integer expiryMonth; // for cards
    private Integer expiryYear;  // for cards
    private boolean isDefault;
    private String token; // tokenized by payment gateway

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 