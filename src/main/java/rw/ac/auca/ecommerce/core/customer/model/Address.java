package rw.ac.auca.ecommerce.core.customer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String recipientName;
    private String label; // e.g. Home, Work
    private String street;
    private String apartment;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private boolean isDefault;
} 