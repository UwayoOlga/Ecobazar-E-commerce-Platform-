package rw.ac.auca.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.service.IProductService;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;
import java.util.UUID;

@Controller
public class CatalogController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/catalog")
    public String showCatalog(Model model) {
        model.addAttribute("products", productService.findProductsByState(true));
        return "catalog";
    }

    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable UUID id, Model model) {
        model.addAttribute("product", productService.findProductByIdAndState(id, true));
        return "productDetail";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Customer customer) {
        customerService.registerCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @GetMapping("/trackOrder")
    public String showTrackOrderPage() {
        return "trackOrder";
    }

    @GetMapping("/cart")
    public String showCartPage() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage() {
        return "checkout";
    }

    // Add more endpoints for cart, checkout, etc. as needed
} 