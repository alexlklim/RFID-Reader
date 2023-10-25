package com.alex.productservice.services;

import com.alex.productservice.model.HexRequest;
import com.alex.productservice.model.Hex;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface IHexService {
    List<Hex> getAllHexes();

    Optional<Hex> getHexByIdHex(String idHex);

    String createNewHex(HexRequest hexRequest);

    List<Hex> createNewHexes(List<HexRequest> hexRequestList);

    void deleteHexByIdHex(String idHex);

    void clearHexes();

}
