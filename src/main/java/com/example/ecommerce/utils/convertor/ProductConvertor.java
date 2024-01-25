package com.example.ecommerce.utils.convertor;

import com.example.ecommerce.utils.dto.ProductDto;
import com.example.ecommerce.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConvertor {

    public ProductDto convertToDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .productPrice(product.getPrice())
                .productQuantity(product.getQuantity())
                .build();
    }
}
