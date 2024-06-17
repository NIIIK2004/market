package com.example.news.impl;

import com.example.news.dao.ProductDao;
import com.example.news.model.Product;
import com.example.news.model.User;
import com.example.news.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImpl implements ProductDao {
    private final ProductRepo productRepo;

    @Override
    public Product save(Product bid) {
        return productRepo.save(bid);
    }

    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public List<Product> findByUser(User user) {
        return productRepo.findByUser(user);
    }

    @Override
    public List<Product> findByBookedBy(User user) {
        return productRepo.findByBookedBy(user);
    }

    @Override
    public void bookProduct(Product product, User user) {
        product.setBooked(true);
        product.setBookedBy(user);
        productRepo.save(product);
    }

    @Override
    public void cancelBooking(Product product) {
        product.setBooked(false);
        product.setBookedBy(null);
        productRepo.save(product);
    }
}
