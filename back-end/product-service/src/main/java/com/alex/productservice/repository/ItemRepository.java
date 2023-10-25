package com.alex.productservice.repository;


import com.alex.productservice.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findItemByProductNumber(Integer productNumber);

    @Modifying
    @Transactional
    void deleteItemByProductNumber(Integer productNumber);

    @Query("SELECT SUM(i.priceGross) FROM Item i")
    Double calculateTotalPriceGross();

    @Query("SELECT SUM(i.priceNet) FROM Item i")
    Double calculateTotalPriceNet();

    @Query("SELECT SUM(i.amount) FROM Item i")
    Integer calculateTotalAmount();

    Boolean existsByProductNumber(Integer productNumber);



    @Modifying
    @Transactional
    @Query("UPDATE Item i SET i.amount = i.amount + 1 WHERE i.productNumber = :productNumber")
    int increaseAmountByProductNumber(Integer productNumber);

}
