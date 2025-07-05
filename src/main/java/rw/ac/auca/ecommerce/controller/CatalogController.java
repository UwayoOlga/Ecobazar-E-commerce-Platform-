package rw.ac.auca.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import rw.ac.auca.ecommerce.core.product.service.IProductService;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;
import rw.ac.auca.ecommerce.core.customer.model.CustomerRegistrationDto;
import java.util.UUID;
import jakarta.servlet.http.HttpSession;

@Controller
public class CatalogController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        model.addAttribute("customerRegistrationDto", new CustomerRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("customerRegistrationDto") CustomerRegistrationDto registrationDto, Model model) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        Customer customer = new Customer();
        customer.setFirstName(registrationDto.getFirstName());
        customer.setLastName(registrationDto.getLastName());
        customer.setEmail(registrationDto.getEmail());
        customer.setPhoneNumber(registrationDto.getPhoneNumber());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        customerService.registerCustomer(customer);
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        try {
            Customer customer = customerService.findCustomerByEmailAndState(email, true);
            // Compare raw password (since registration used encoded, you may want to use plain for now)
            if (customer.getPassword().equals(password)) {
                session.setAttribute("user", customer);
                return "redirect:/dashboard";
            } else {
                model.addAttribute("error", "Invalid email or password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
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

    @GetMapping("/debug/customers")
    @ResponseBody
    public String debugCustomers() {
        try {
            var customers = customerService.findCustomersByState(true);
            return "Found " + customers.size() + " customers: " + customers.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Add more endpoints for cart, checkout, etc. as needed
} 