package com.ing.testapp.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: built the infrastructure for unit testing, no time to finish the rest, we can talk about it tomorrow :)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductRepository productRepository;
    private final Product PRODUCT_1 = new Product(1, "Toy", 20.0, "red");
    private final Product PRODUCT_2 = new Product(2, "Car", 30.0, "blue");
    private final Product PRODUCT_3 = new Product(3, "Bear", 40.0, "yellow");
    private final List<Product> PRODUCT_LIST = List.of(PRODUCT_1, PRODUCT_2, PRODUCT_3);

    @Test
    @WithMockUser(username = "basic", password = "password", roles = "USER")
    public void testGetAllProductsSuccess() throws Exception {
        Mockito.when(productRepository.findAll()).thenReturn(PRODUCT_LIST);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/store/all_products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}
