package com.alex.productservice.controllers;


import com.alex.productservice.model.Message;
import com.alex.productservice.model.Product;
import com.alex.productservice.services.IProductService;
import com.alex.productservice.services.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService iProductService;

//    @Autowired
//    public ProductController(IProductService iProductService) {
//        this.iProductService = iProductService;
//    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<>(iProductService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product-number/{productNumber}")
    public ResponseEntity<?> getAllProductsByProductNumber(@PathVariable("productNumber") Integer productNumber) {
        Optional<Product> product = iProductService.getProductByProductNumber(productNumber);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id-hex/{idHex}")
    public ResponseEntity<?> getProductByIdHex(@PathVariable("idHex") String idHex) {
        Optional<Product> product = iProductService.getProductByIdHex(idHex);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update/{productNumber}")
    public ResponseEntity<?> updateProductByProductNumber(@PathVariable("productNumber") Integer productNumber,
                                                          @RequestBody Product product){
        Optional<Product> productFromDB = iProductService.getProductByProductNumber(productNumber);
        if (productFromDB.isPresent()){
            Optional<Product> updatedProduct = iProductService.updateProductByProductNumber(productNumber, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>(Message.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/{productNumber}")
    public ResponseEntity<?> deleteProductByProductNumber(@PathVariable("productNumber") Integer productNumber) {
        iProductService.deleteProductByProductNumber(productNumber);
        return new ResponseEntity<>(Message.PRODUCT_DELETED.getMessage(), HttpStatus.OK);
    }

    @PostMapping("/clear")
    public ResponseEntity<?> deleteAllProducts() {
        iProductService.deleteAllProducts();
        return new ResponseEntity<>(Message.ALL_PRODUCTS_WERE_DELETED.getMessage(), HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<?> createNewProduct(@RequestBody Product productNew) {
        Optional<Product> product = iProductService.getProductByProductNumber(productNew.getProductNumber());
        if (product.isPresent()){
            return new ResponseEntity<>(Message.PRODUCT_ALREADY_EXISTS.getMessage(), HttpStatus.CONFLICT);
        }
        Product productToSave = iProductService.createNewProduct(productNew);
        return new ResponseEntity<>(productToSave, HttpStatus.CREATED);

    }

    @PostMapping("/new/products")
    public ResponseEntity<?> createNewProducts(@RequestBody List<Product> listProducts) {
        List<Product> createdProducts = iProductService.createNewProducts(listProducts);
        if (createdProducts.isEmpty()){
            return new ResponseEntity<>(Message.PRODUCT_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }
}
