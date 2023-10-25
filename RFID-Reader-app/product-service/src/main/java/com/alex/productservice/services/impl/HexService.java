package com.alex.productservice.services.impl;


import com.alex.productservice.model.Hex;
import com.alex.productservice.model.HexRequest;
import com.alex.productservice.repository.HexRepository;
import com.alex.productservice.services.IHexService;
import com.alex.productservice.services.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HexService implements IHexService {
    private final HexRepository hexRepository;
    private final ProductService iProductService;

    @Override
    public List<Hex> getAllHexes() {
        return hexRepository.findAll();
    }

    @Override
    public Optional<Hex> getHexByIdHex(String idHex) {
        return hexRepository.findHexByIdHex(idHex);
    }

    @Override
    public String createNewHex(HexRequest hexRequest) {
        if (hexRepository.existsByIdHex(hexRequest.getIdHex())){
            return "ID_HEX_EXISTS";
        }
        if (iProductService.checkIsProductExists(hexRequest.getProductNumber())){
            Hex hex = new Hex();
            hex.setIdHex(hexRequest.getIdHex());
            hex.setProduct(iProductService.getProductByProductNumber(hexRequest.getProductNumber()).get());
            hexRepository.save(hex);
            return "SUCCESS";
        }
        return "PRODUCT_NOT_EXISTS";
    }

    @Override
    public List<Hex> createNewHexes(List<HexRequest> hexRequestList) {
        List<Hex> createdHexes = new ArrayList<>();
        for (HexRequest hexRequest : hexRequestList){
            String message = createNewHex(hexRequest);
            System.out.println("mess: "+ message);
            if (message.equals("SUCCESS"))
                createdHexes.add(hexRepository.findHexByIdHex(hexRequest.getIdHex()).get());
        }
        return createdHexes;
    }

    @Override
    public void deleteHexByIdHex(String idHex) {
        hexRepository.delete(hexRepository.findHexByIdHex(idHex).get());
    }

    @Override
    public void clearHexes() {
        hexRepository.deleteAll();
    }

    @Transactional
    @Modifying
    public List<String> getAllHexesForProductNumber(Integer productNumber){
        List<Hex> hexList = hexRepository.findHexesByProduct_ProductNumber(productNumber);
        List<String> idHexList = new ArrayList<>();
        for (Hex hex : hexList){
            idHexList.add(hex.getIdHex());
        }
        return idHexList;
     }
}
