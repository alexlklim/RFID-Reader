package com.alex.productservice.controllers;


import com.alex.productservice.model.Item;
import com.alex.productservice.model.Message;
import com.alex.productservice.services.IItemService;
import com.alex.productservice.services.impl.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/item")
public class ItemController {
    private final ItemService iItemService;

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> itemList = iItemService.getAllItems();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/{productNumber}")
    public ResponseEntity<?> getItemByProductNumber(@PathVariable("productNumber") Integer productNumber){
        Optional<Item> item = iItemService.getItemByProductNumber(productNumber);
        if (item.isPresent()){
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Message.ITEM_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/total-prices")
    public ResponseEntity<Map<String, Double>> getTotalPrices(){
        Map<String, Double> response = new HashMap<>();
        response.put("totalPriceGross", iItemService.getTotalPriceGross());
        response.put("totalPriceNet", iItemService.getTotalPriceNet());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/total-amount")
    public ResponseEntity<Map<String, Integer>> getTotalAmount(){
        Map<String, Integer> response = new HashMap<>();
        response.put("totalAmount", iItemService.getTotalAmount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/delete/{productNumber}")
    public ResponseEntity<String> deleteItemByProductNumber(@PathVariable("productNumber") Integer productNumber){
        iItemService.deleteItemByProductNumber(productNumber);
        iItemService.deleteIdHexesFromTagService(productNumber);
        return new ResponseEntity<>(Message.ITEM_WAS_DELETED.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<String> clearItems(){
        iItemService.clearItems();
        return new ResponseEntity<>(Message.ITEM_CLEAR_ALL.getMessage(), HttpStatus.OK);
    }


    // for Tag-Service
    @GetMapping("/tag/{idHex}")
    public ResponseEntity<?> addNewItem(@PathVariable("idHex") String idHex){
        if (iItemService.addNewItem(idHex)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
