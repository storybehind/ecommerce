package com.example.ecommerce;

import com.example.ecommerce.controller.ProductController;
import com.example.ecommerce.domain.entity.Product;
import com.example.ecommerce.utils.dto.PriceUpdateDto;
import com.example.ecommerce.utils.dto.ProductDto;
import com.example.ecommerce.utils.enums.PriceUpdateCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class EcommerceApplicationTests {

	@Autowired
	private ProductController productController;

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		// assert product 1 is not present
		ResponseEntity<ProductDto> productDtoResponseEntity1 = productController.get(1);
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(204));

		Map<Long, ProductDto> products = new HashMap<>();

		// Create product p1
		ProductDto createProductDto = ProductDto.builder()
						.productName("P1")
						.productDescription("Product 1")
						.productPrice(BigDecimal.valueOf(70))
						.productQuantity(10).build();
		productDtoResponseEntity1 = productController.create(createProductDto);
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(201));
		ProductDto createdProduct1 = productDtoResponseEntity1.getBody();
		assert (createdProduct1 != null);
		assert (createdProduct1.getProductName().equals("P1"));
		assert (createdProduct1.getProductDescription().equals("Product 1"));
		assert (createdProduct1.getProductPrice().equals(BigDecimal.valueOf(70)));
		assert (createdProduct1.getProductQuantity() == 10);

		products.put(createdProduct1.getProductId(), createdProduct1);

		// Create product p2
		createProductDto = ProductDto.builder()
				.productName("P2")
				.productDescription("Product 2")
				.productPrice(BigDecimal.valueOf(200))
				.productQuantity(20).build();
		productDtoResponseEntity1 = productController.create(createProductDto);
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(201));
		ProductDto createdProduct2 = productDtoResponseEntity1.getBody();
		assert (createdProduct2 != null);
		assert (createdProduct2.getProductName().equals("P2"));
		assert (createdProduct2.getProductDescription().equals("Product 2"));
		assert (createdProduct2.getProductPrice().equals(BigDecimal.valueOf(200)));
		assert (createdProduct2.getProductQuantity() == 20);

		assert (!products.containsKey(createdProduct2.getProductId()));
		products.put(createdProduct2.getProductId(), createdProduct2);

		//update product 1
		createdProduct1.setProductPrice(BigDecimal.valueOf(100));
		productDtoResponseEntity1 = productController.update(createdProduct1);
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(200));
		ProductDto updatedProductDto1 = productDtoResponseEntity1.getBody();
		assert (updatedProductDto1 != null);
		assert (updatedProductDto1.getProductId() == createdProduct1.getProductId());
		assert (updatedProductDto1.getProductName().equals("P1"));
		assert (updatedProductDto1.getProductDescription().equals("Product 1"));
		assert (updatedProductDto1.getProductPrice().equals(BigDecimal.valueOf(100)));
		assert (updatedProductDto1.getProductQuantity() == 10);

		// update price for product 1 with discount percentage of 25
		PriceUpdateDto priceUpdateDto = new PriceUpdateDto();
		priceUpdateDto.setProductId(createdProduct1.getProductId());
		priceUpdateDto.setPriceUpdateCategory(PriceUpdateCategory.DISCOUNT_PERCENTAGE);
		priceUpdateDto.setValue(BigDecimal.valueOf(25));
		productDtoResponseEntity1 = productController.updatePrice(priceUpdateDto);
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(200));
		updatedProductDto1 = productDtoResponseEntity1.getBody();
		assert (updatedProductDto1 != null);
		assert (updatedProductDto1.getProductId() == createdProduct1.getProductId());
		assert (updatedProductDto1.getProductPrice().equals(new BigDecimal("75.00")));

		// update price for product 2 with tax rate of 50
		priceUpdateDto.setProductId(createdProduct2.getProductId());
		priceUpdateDto.setPriceUpdateCategory(PriceUpdateCategory.TAX_RATE);
		priceUpdateDto.setValue(BigDecimal.valueOf(50));
		productDtoResponseEntity1 = productController.updatePrice(priceUpdateDto);
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(200));
		ProductDto updatedProductDto2 = productDtoResponseEntity1.getBody();
		assert (updatedProductDto2 != null);
		assert (updatedProductDto2.getProductId() == createdProduct2.getProductId());
		assert (updatedProductDto2.getProductPrice().equals(new BigDecimal("300.00")));

		// verify product2 is present
		productDtoResponseEntity1 = productController.get(createdProduct2.getProductId());
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(200));
		assert (productDtoResponseEntity1.getBody() != null);
		assert (productDtoResponseEntity1.getBody().getProductId() == createdProduct2.getProductId());

		// delete product2
		ResponseEntity<?> deleteProductResponseEntity = productController.delete(createdProduct2.getProductId());
		assert (deleteProductResponseEntity.getStatusCode() == HttpStatusCode.valueOf(204));

		//verify product2 is not present
		productDtoResponseEntity1 = productController.get(createdProduct2.getProductId());
		assert (productDtoResponseEntity1.getStatusCode() == HttpStatusCode.valueOf(204));

	}
}
