package com.ecommerce.repository;
 
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUser(User user);
    List<Order> findByStatus(String status);
}
