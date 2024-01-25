package com.example.ecommerce.controller;

import com.example.ecommerce.domain.entity.Product;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.priceupdate.PriceUpdateFactory;
import com.example.ecommerce.utils.convertor.ProductConvertor;
import com.example.ecommerce.utils.dto.PriceUpdateDto;
import com.example.ecommerce.utils.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConvertor productConvertor;

    @Autowired
    private PriceUpdateFactory priceUpdateFactory;

    @GetMapping("/health")
    public String index() {
        return "Success!";
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto) {
        Product product = productService.create(productDto);
        return new ResponseEntity<>(productConvertor.convertToDto(product), HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable("id") long productId) {
        Optional<Product> optionalProduct = productService.getById(productId);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productConvertor.convertToDto(optionalProduct.get()), HttpStatus.OK);
    }

    @PutMapping("/product")
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto) {
        Optional<Product> optionalProduct = productService.update(productDto);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productConvertor.convertToDto(optionalProduct.get()), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long productId) {
        productService.delete(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/product/updateprice")
    public ResponseEntity<ProductDto> updatePrice(@RequestBody PriceUpdateDto priceUpdateDto) {
        Optional<Product> optionalProduct = priceUpdateFactory.getService(priceUpdateDto.getPriceUpdateCategory())
                            .updatePrice(priceUpdateDto.getProductId(), priceUpdateDto.getValue());
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productConvertor.convertToDto(optionalProduct.get()), HttpStatus.OK);
    }
}
