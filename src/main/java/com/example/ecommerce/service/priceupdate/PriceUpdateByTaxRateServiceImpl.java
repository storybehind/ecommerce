package com.example.ecommerce.service.priceupdate;

import com.example.ecommerce.utils.enums.PriceUpdateCategory;
import com.example.ecommerce.domain.entity.Product;
import com.example.ecommerce.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class PriceUpdateByTaxRateServiceImpl implements PriceUpdateService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Optional<Product> updatePrice(long productId, BigDecimal value) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return optionalProduct;
        }
        Product product = optionalProduct.get();
        BigDecimal currentPrice = product.getPrice();
        BigDecimal multiplicand = BigDecimal.ONE.add(value.divide(BigDecimal.valueOf(100), new MathContext(2)));
        BigDecimal newPrice =  currentPrice.multiply(multiplicand).setScale(2, RoundingMode.CEILING);
        product.setPrice(newPrice);
        return Optional.of(productRepository.save(product));
    }

    @Override
    public PriceUpdateCategory getPriceUpdateCategory() {
        return PriceUpdateCategory.TAX_RATE;
    }
}
