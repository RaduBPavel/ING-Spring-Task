package com.ing.testapp.store;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();
    Optional<Product> findById(Integer id);
    List<Product> findByColour(String colour);
}
