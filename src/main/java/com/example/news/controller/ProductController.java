package com.example.news.controller;

import com.example.news.impl.ProductImpl;
import com.example.news.impl.UserImpl;
import com.example.news.model.Product;
import com.example.news.model.User;
import com.example.news.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final UserImpl userImpl;
    private final ProductImpl productImpl;
    private final ProductRepo productRepo;

    @GetMapping("/")
    public String home(Authentication authentication, Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userImpl.findByUsername(username);
            if (user == null) {
                return "redirect:/login?logout";
            }

            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("user", user);
                List<Product> allProducts = productImpl.findAll();
                model.addAttribute("allProducts", allProducts);
                return "index";
            }
        }
        return "auth";
    }

    @GetMapping("/my")
    public String usersMarket(Authentication authentication, Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userImpl.findByUsername(username);
            if (user == null) {
                return "redirect:/login?logout";
            }

            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("user", user);
                model.addAttribute("productList", productImpl.findByUser(user));
                model.addAttribute("bookedProducts", productImpl.findByBookedBy(user));
                return "userMarket";
            }
        }
        return "auth";
    }

    //Просмотр всех забронированных пользователем товаров
//    @GetMapping("/booked")
//    public String bookedProducts(Authentication authentication, Model model, Principal principal) {
//        if (principal != null) {
//            String username = principal.getName();
//            User user = userImpl.findByUsername(username);
//            if (user == null) {
//                return "redirect:/login?logout";
//            }
//
//            if (authentication != null && authentication.isAuthenticated()) {
//                model.addAttribute("user", user);
//                List<Product> bookedProducts = productImpl.findByBookedBy(user);
//                model.addAttribute("productList", bookedProducts);
//                return "index";
//            }
//        }
//        return "auth";
//    }


    @GetMapping("/product/create")
    public String createProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @PostMapping("/product/save")
    public String createProduct(Authentication authentication, @ModelAttribute("product") Product product, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userImpl.findByUsername(username);
            product.setUser(user);

            try {
                Double.parseDouble(product.getPrice());
                productImpl.save(product);
            } catch (NumberFormatException e) {
                model.addAttribute("errorMessage", "Цена должна быть числом.");
                return "create";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productImpl.delete(id);
        return "redirect:/";
    }

    @GetMapping("/product/book/{id}")
    public String bookProduct(@PathVariable Long id, Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userImpl.findByUsername(username);
            Product product = productImpl.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор"));

            if (!product.getUser().getId().equals(user.getId()) && !product.isBooked()) {
                productImpl.bookProduct(product, user);
            } else {
                model.addAttribute("errorMessage", "Вы не можете забронировать этот товар.");
            }
        }
        return "redirect:/";
    }

    @GetMapping("/product/cancelBooking/{id}")
    public String cancelBooking(@PathVariable Long id, Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userImpl.findByUsername(username);
            Product product = productImpl.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор"));

            if (product.isBooked() && product.getBookedBy().getId().equals(user.getId())) {
                productImpl.cancelBooking(product);
            } else {
                model.addAttribute("errorMessage", "Вы не можете отменить бронь этого товара.");
            }
        }
        return "redirect:/";
    }

}













