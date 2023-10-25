package com.alex.productservice.controllers;


import com.alex.productservice.model.Hex;
import com.alex.productservice.model.HexRequest;
import com.alex.productservice.model.Message;
import com.alex.productservice.services.impl.HexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product/hex")
public class HexController {
    private final HexService iHexService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllHexadecimal() {
        List<Hex> hexList = iHexService.getAllHexes();
        if (hexList.isEmpty()) {
            return new ResponseEntity<>(Message.HEX_EMPTY_LIST.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hexList, HttpStatus.OK);
    }

    @GetMapping("/id-hex/{idHex}")
    public ResponseEntity<?> getHexadecimalByIdHex(@PathVariable("idHex") String idHex) {
        Optional<Hex> hex = iHexService.getHexByIdHex(idHex);
        if (hex.isPresent()) {
            return new ResponseEntity<>(hex.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Message.HEX_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewHexadecimal(@RequestBody HexRequest hexRequest) {
        String message = iHexService.createNewHex(hexRequest);

        if (message.equals("ID_HEX_EXISTS")){
            return new ResponseEntity<>("ID_HEX_EXISTS", HttpStatus.CONFLICT);
        }
        if (message.equals("PRODUCT_NOT_EXISTS")) {
            return new ResponseEntity<>("PRODUCT_NOT_EXISTS", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(iHexService.getHexByIdHex(hexRequest.getIdHex()).get(), HttpStatus.OK);
    }

    @PostMapping("/new-many")
    public ResponseEntity<?> createNewHexadecimals(@RequestBody List<HexRequest> hexRequestList) {
        List<Hex> hexList = iHexService.createNewHexes(hexRequestList);
        if (hexList.isEmpty()) {
            return new ResponseEntity<>(Message.HEX_NOT_CREATED.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(hexList, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{idHex}")
    public ResponseEntity<?> deleteHexadecimalByIdHex(@PathVariable("idHex") String idHex) {
        iHexService.deleteHexByIdHex(idHex);
        return new ResponseEntity<>(Message.HEX_WAS_DELETED.getMessage(), HttpStatus.OK);
    }

    @PostMapping("/clear")
    public ResponseEntity<?> deleteAllTags() {
        iHexService.clearHexes();
        return new ResponseEntity<>(Message.HEX_CLEAR_TABLE.getMessage(), HttpStatus.OK);
    }

}
