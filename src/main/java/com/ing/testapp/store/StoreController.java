package com.ing.testapp.store;

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
        productRepository.save(newProduct);
        return new ResponseEntity<>("New product added successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete_product")
    public ResponseEntity<String> deleteProduct(
            @RequestParam Integer id
    ) {
        productRepository.deleteById(id);
        return new ResponseEntity<>(String.format("Product with id: %d deleted successfully.", id), HttpStatus.OK);
    }

    @PutMapping(path = "/update_product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(
            @RequestParam Integer id,
            @RequestBody Product newProduct
    ) {
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
    }

    @GetMapping(path = "/find_colour")
    public @ResponseBody Iterable<Product> getProductsByColour(
            @RequestParam String colour
    ) {
        return productRepository.findByColour(colour);
    }

    @GetMapping(path = "/find_product")
    public @ResponseBody Product getProductById(
            @RequestParam Integer id
    ) {
        return productRepository.findById(id).get();
    }

    @GetMapping(path = "/all_products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
