package com.alex.productservice.services;

import com.alex.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IProductService {

    List<Product> getAllProducts();

    Optional<Product> getProductByIdHex(String idHex);

    Optional<Product> getProductByProductNumber(Integer productNumber);

    Optional<Product> updateProductByProductNumber(Integer productNumber, Product product);

    void deleteProductByProductNumber(Integer productNumber);

    void deleteAllProducts();

    Product createNewProduct(Product product);

    List<Product> createNewProducts(List<Product> productList);

    Integer getProductNumberByIdHex(String idHex);

    Boolean checkIsProductExists(Integer productNumber);






}
