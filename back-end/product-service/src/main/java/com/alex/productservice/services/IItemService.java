package com.alex.productservice.services;

import com.alex.productservice.model.Item;
import com.alex.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IItemService {

    List<Item> getAllItems();

    Optional<Item> getItemByProductNumber(Integer productNumber);

    Double getTotalPriceNet();

    Double getTotalPriceGross();
    Integer getTotalAmount();
    void deleteItemByProductNumber(Integer productNumber);
    void clearItems();


    Boolean addNewItem(String idHex);

    Item convertProductToItem(Product product);

    void deleteIdHexesFromTagService(Integer productNumber);
}
