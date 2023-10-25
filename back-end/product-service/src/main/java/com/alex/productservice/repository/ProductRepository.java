package com.alex.productservice.repository;


import com.alex.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductByProductNumber(Integer productNumber);

    Boolean existsByProductNumber(Integer productNumber);

    void deleteProductByProductNumber(Integer productNumber);
}
