package com.example.news.dao;

import com.example.news.model.Product;
import com.example.news.model.User;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product save(Product product);
    void delete(Long id);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByUser(User user);
    List<Product> findByBookedBy(User user);
    void bookProduct(Product product, User user);
    void cancelBooking(Product product);

}
