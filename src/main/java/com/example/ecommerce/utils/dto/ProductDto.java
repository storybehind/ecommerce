package com.example.ecommerce.utils.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {
    private long productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private long productQuantity;
}
