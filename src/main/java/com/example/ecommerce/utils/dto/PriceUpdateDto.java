package com.example.ecommerce.utils.dto;

import com.example.ecommerce.utils.enums.PriceUpdateCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceUpdateDto {
    private long productId;
    private PriceUpdateCategory priceUpdateCategory;
    private BigDecimal value;
}
