package ru.itbooks.productservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.itbooks.productservice.exceptions.BookNotFoundException;
import ru.itbooks.productservice.services.ProductService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    void bookNotExistsStatusTest() throws Exception {
        String article = "12345";
        given(productService.viewBookDetails(article)).willThrow(BookNotFoundException.class);
        mockMvc
                .perform(get("/books/" + article))
                .andExpect(status().isNotFound());
    }
}