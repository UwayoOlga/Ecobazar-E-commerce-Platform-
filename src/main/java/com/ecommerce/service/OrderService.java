package com.ecommerce.service;

import com.ecommerce.dto.OrderRequestDTO;
import com.ecommerce.dto.OrderResponseDTO;
import com.ecommerce.dto.OrderItemRequestDTO;
import com.ecommerce.dto.OrderItemResponseDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {
        User user = userService.getUserById(orderRequestDTO.getUserId());

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setItems(new ArrayList<>());

        double totalAmount = 0.0;

        for (OrderItemRequestDTO itemDTO : orderRequestDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDTO.getProductId()));

            if (product.getQuantityInStock() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);

            product.setQuantityInStock(product.getQuantityInStock() - itemDTO.getQuantity());
            productRepository.save(product);

            totalAmount += orderItem.getPrice() * orderItem.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return mapToResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return orderRepository.findByUser(user).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return mapToResponseDTO(order);
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream()
                .map(this::mapToItemResponseDTO)
                .collect(Collectors.toList());
        orderDTO.setItems(itemDTOs);

        return orderDTO;
    }

    private OrderItemResponseDTO mapToItemResponseDTO(OrderItem item) {
        OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
        itemDTO.setProductId(item.getProduct().getId());
        itemDTO.setProductName(item.getProduct().getName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update order status
    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setStatus(status);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    // Add item to order
    public Order addItemToOrder(Long orderId, OrderItem item) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.getItems().add(item);
            item.setOrder(existingOrder);
            calculateTotalAmount(existingOrder);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    // Remove item from order
    public Order removeItemFromOrder(Long orderId, Long itemId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.getItems().removeIf(item -> item.getId().equals(itemId));
            calculateTotalAmount(existingOrder);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    // Calculate total amount for an order
    private void calculateTotalAmount(Order order) {
        double total = 0.0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(total);
    }

    // Delete order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
} 