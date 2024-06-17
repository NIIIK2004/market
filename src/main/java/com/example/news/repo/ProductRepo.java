package com.example.news.repo;

import com.example.news.model.Product;
import com.example.news.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByUser(User user);
    List<Product> findByBookedBy(User user);
}
