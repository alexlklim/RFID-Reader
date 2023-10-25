package com.alex.productservice.services.impl;

import com.alex.productservice.model.Item;
import com.alex.productservice.model.Product;
import com.alex.productservice.repository.ItemRepository;
import com.alex.productservice.services.IItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {
    private final ItemRepository itemRepository;
    private final ProductService iProductService;
    private final HexService iHexService;

    @Value("${my.url.delete-tag-from-tag-service}")
    private String URL_deleteTagFromTagService;



    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> getItemByProductNumber(Integer productNumber) {
        return itemRepository.findItemByProductNumber(productNumber);
    }

    @Override
    public Double getTotalPriceNet() {
        return itemRepository.calculateTotalPriceNet();
    }

    @Override
    public Double getTotalPriceGross() {
        return itemRepository.calculateTotalPriceGross();
    }

    @Override
    public Integer getTotalAmount() {
        return itemRepository.calculateTotalAmount();
    }

    @Override
    public void deleteItemByProductNumber(Integer productNumber) {
        itemRepository.deleteItemByProductNumber(productNumber);
    }

    @Override
    public void clearItems() {
        itemRepository.deleteAll();
    }

    @Modifying
    @Transactional
    @Override
    public Boolean addNewItem(String idHex) {
        Optional<Product> product = iProductService.getProductByIdHex(idHex);
        if (product.isPresent()) {
            if (!itemRepository.existsByProductNumber(product.get().getProductNumber())) {
                log.warn("NEW_ITEM_CREATED");
                Item item = convertProductToItem(product.get());
                itemRepository.save(item);
                return true;
            }
            // if item with this productNumber exists in Item table
            itemRepository.increaseAmountByProductNumber(product.get().getProductNumber());
            log.warn("AMOUNT_UPDATED_FOR_EXISTING_PRODUCT");
            return false;
        }
        log.warn("PRODUCT_NOT_EXISTS_IN_DB");
        return false;
    }

    @Override
    public Item convertProductToItem(Product product) {
        Item item = new Item();
        item.setTitle(product.getTitle());
        item.setProductNumber(product.getProductNumber());
        item.setPriceGross(product.getPriceGross());
        item.setPriceNet(product.getPriceNet());
        item.setAmount(1);
        item.setDescription(product.getDescription());
        item.setSpecification(product.getSpecification());
        item.setImage(product.getImage());
        return item;

    }


    @Transactional
    @Modifying
    @Override
    public void deleteIdHexesFromTagService(Integer productNumber) {
        System.out.println("ffg");
        List<String> idHexList = iHexService.getAllHexesForProductNumber(productNumber);
        for (String idHex : idHexList) {
            sendDeleteRequestToTagService(idHex);
        }

    }

    private void sendDeleteRequestToTagService(String idHex) {
        RestTemplate restTemplate = new RestTemplate();
        String url = URL_deleteTagFromTagService + idHex;
        restTemplate.getForObject(url, String.class);
        System.out.println();
    }
}
