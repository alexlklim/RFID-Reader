package com.alex.productservice.services.impl;


import com.alex.productservice.model.Product;
import com.alex.productservice.repository.HexRepository;
import com.alex.productservice.repository.ProductRepository;
import com.alex.productservice.services.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final HexRepository hexRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductByIdHex(String idHex) {
        return productRepository.findProductByProductNumber(getProductNumberByIdHex(idHex));
    }

    @Override
    public Optional<Product> getProductByProductNumber(Integer productNumber) {
        return productRepository.findProductByProductNumber(productNumber);
    }

    @Modifying
    @Transactional
    @Override
    public Optional<Product> updateProductByProductNumber(Integer productNumber, Product product) {
        if (productRepository.existsByProductNumber(productNumber)) {
            Product productFromDB = productRepository.findProductByProductNumber(productNumber).get();
            productFromDB.setTitle(product.getTitle());
            productFromDB.setPriceGross(product.getPriceGross());
            productFromDB.setPriceNet(product.getPriceNet());

            productFromDB.setDescription(product.getDescription());
            productFromDB.setSpecification(product.getSpecification());
            productFromDB.setImage(product.getImage());
            return Optional.of(productRepository.save(productFromDB));
        }
        return Optional.empty();
    }

    @Override
    public void deleteProductByProductNumber(Integer productNumber) {
        productRepository.deleteProductByProductNumber(productNumber);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Override
    public Product createNewProduct(Product product) {
        // validate data of Product entity
        if (productRepository.existsByProductNumber(product.getProductNumber())) {
            log.warn("ProductService : createNewProduct : product with productNumber: {} already exists",
                    product.getProductNumber());
            return null;
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> createNewProducts(List<Product> productList) {
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            if (!productRepository.existsByProductNumber(product.getProductNumber())) {
                // if product not exits, add it to DB
                productRepository.save(product);
                products.add(product);
            }
            // if product exists it will be skiped
        }
        return products;
    }


    public Integer getProductNumberByIdHex(String idHex) {
        return hexRepository.findHexByIdHex(idHex).get().getProduct().getProductNumber();
    }

    @Override
    public Boolean checkIsProductExists(Integer productNumber) {
        return productRepository.existsByProductNumber(productNumber);
    }
}
