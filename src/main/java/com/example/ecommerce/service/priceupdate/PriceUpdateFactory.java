package com.example.ecommerce.service.priceupdate;

import com.example.ecommerce.utils.enums.PriceUpdateCategory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriceUpdateFactory {

    @Autowired
    private List<PriceUpdateService> serviceList;

    private Map<PriceUpdateCategory, PriceUpdateService> map = new HashMap<>();

    @PostConstruct
    private void init() {
        for (PriceUpdateService priceUpdateService : serviceList) {
            map.put(priceUpdateService.getPriceUpdateCategory(), priceUpdateService);
        }
    }

    public PriceUpdateService getService(PriceUpdateCategory priceUpdateCategory) {
        return map.get(priceUpdateCategory);
    }
}
