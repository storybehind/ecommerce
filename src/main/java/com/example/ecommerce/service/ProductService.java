package com.example.ecommerce.service;

import com.example.ecommerce.utils.dto.ProductDto;
import com.example.ecommerce.domain.entity.Product;
import com.example.ecommerce.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getProductName());
        product.setDescription(productDto.getProductDescription());
        product.setPrice(productDto.getProductPrice());
        product.setQuantity(productDto.getProductQuantity());
        return productRepository.save(product);
    }

    public Optional<Product> getById(long productId) {
        return productRepository.findById(productId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Optional<Product> update(ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(productDto.getProductId());
        if (productOptional.isEmpty()) {
            return productOptional;
        }
        Product product = productOptional.get();
        product.setName(productDto.getProductName());
        product.setDescription(productDto.getProductDescription());
        product.setPrice(productDto.getProductPrice());
        product.setQuantity(productDto.getProductQuantity());
        return Optional.of(productRepository.save(product));
    }

    public void delete(long productId) {
        productRepository.deleteById(productId);
    }
}
