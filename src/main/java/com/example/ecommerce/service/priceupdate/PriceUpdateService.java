package com.example.ecommerce.service.priceupdate;

import com.example.ecommerce.utils.enums.PriceUpdateCategory;
import com.example.ecommerce.domain.entity.Product;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceUpdateService {

    Optional<Product> updatePrice(long productId, BigDecimal value);

    PriceUpdateCategory getPriceUpdateCategory();
}
