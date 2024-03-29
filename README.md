# ecommerce

## Tools Required
* Java 17
* Gradle 8.5
* Curl

## Build and Run

1) To build the project, run following command in root folder of the project.

    `gradle build --info`

    
2) To execute the build and start the server, run

    `.\gradlew bootRun`

## Demo

1) Health check

    `curl http://localhost:8080/health`

    ```textmate
    Success!
    ```
   
2) Create Product

    ```textmate
    curl --location --request POST 'http://localhost:8080/product' --header 'Content-Type: application/json' --data '{"productName": "P1", "productDescription": "Product 1", "productPrice" : 75, "productQuantity": 30}'
    ```
   
    ```textmate
    {"productId":1,"productName":"P1","productDescription":"Product 1","productPrice":75,"productQuantity":30}
    ```
   
3) Get Product

    ```textmate
    curl --location --request GET 'http://localhost:8080/product/1'
    ```

    ```textmate
    {"productId":1,"productName":"P1","productDescription":"Product 1","productPrice":75.00,"productQuantity":30}
    ```
 
4) Update Product

    ```textmate
    curl --location --request PUT 'http://localhost:8080/product' --header 'Content-Type: application/json' --data '{
    "productId": 1,
    "productName": "P1",
    "productDescription": "Product 1",
    "productPrice" : 100,
    "productQuantity": 30
    }'
    ```
   
    ```textmate
    {"productId":1,"productName":"P1","productDescription":"Product 1","productPrice":100,"productQuantity":30}
    ```
   
5) Update Price
    
    5.1) Tax Rate

    ```textmate
    curl --location --request PUT 'http://localhost:8080/product/updateprice' --header 'Content-Type: application/json' --data '{
    "productId": 1,
    "priceUpdateCategory": "TAX_RATE",
    "value": 25
    }'
    ```
   
    ```textmate
    {"productId":1,"productName":"P1","productDescription":"Product 1","productPrice":125.00,"productQuantity":30}
    ```
   
    5.2) Discount Percentage

    ```textmate
    curl --location --request PUT 'http://localhost:8080/product/updateprice' --header 'Content-Type: application/json' --data '{
    "productId": 1,
    "priceUpdateCategory": "DISCOUNT_PERCENTAGE",
    "value": 25
    }'
    ```
   
    ```textmate
    {"productId":1,"productName":"P1","productDescription":"Product 1","productPrice":93.75,"productQuantity":30}
    ```