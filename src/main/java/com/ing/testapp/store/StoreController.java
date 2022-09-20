package com.ing.testapp.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/store")
public class StoreController {
    @Autowired
    private FictionalProductRepository productRepository;

    @PostMapping(path = "/add_product")
    public @ResponseBody String addProduct(
            @RequestParam String name,
            @RequestParam Double price
    ) {
        FictionalProduct newProduct = new FictionalProduct();
        newProduct.setName(name);
        newProduct.setPrice(price);

        productRepository.save(newProduct);
        return "placeholder";
    }

    @GetMapping(path = "/all_products")
    public @ResponseBody Iterable<FictionalProduct> getAllProducts() {
        return productRepository.findAll();
    }
}
