package com.ing.testapp.store;

import com.ing.testapp.exceptions.InvalidProductIdException;
import com.ing.testapp.exceptions.ProductIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/store")
public class StoreController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path = "/add_product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(
            @RequestBody Product newProduct
    ) {
        try {
            productRepository.save(newProduct);
            return new ResponseEntity<>("New product added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error encountered on add operation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete_product")
    public ResponseEntity<String> deleteProduct(
            @RequestParam Integer id
    ) {
        if (id <= 0) {
            throw new InvalidProductIdException();
        }
        if (!productRepository.existsById(id)) {
            throw new ProductIdNotFoundException();
        }

        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(String.format("Product with id: %d deleted successfully.", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error encountered on delete operation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update_product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(
            @RequestParam Integer id,
            @RequestBody Product newProduct
    ) {
        if (id <= 0) {
            throw new InvalidProductIdException();
        }

        try {
        Product updatedProduct = productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setColour(newProduct.getColour());

                    return product;
                })
                .orElseGet(() -> newProduct);

            productRepository.save(updatedProduct);
            return new ResponseEntity<>(String.format("Product with id: %d updated successfully.", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error encountered on update operation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/find_colour")
    public ResponseEntity<Object> getProductsByColour(
            @RequestParam String colour
    ) {
        try {
            return new ResponseEntity(productRepository.findByColour(colour), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error encountered on find operation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/find_product")
    public ResponseEntity<Object> getProductById(
            @RequestParam Integer id
    ) {
        if (id <= 0) {
            throw new InvalidProductIdException();
        }
        if (!productRepository.existsById(id)) {
            throw new ProductIdNotFoundException();
        }

        try {
            return new ResponseEntity(productRepository.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error encountered on find operation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all_products")
    public ResponseEntity<Object> getAllProducts() {
        try {
            return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error encountered on find operation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
