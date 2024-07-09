package com.example.projeto_desafio.controller;

import com.example.projeto_desafio.dto.CategoriaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldListAllCategories() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("Cachorros"))
                .andExpect(jsonPath("$[1].nome").value("Gatos"));
    }

    @Test
    public void shouldGetCategoryById() throws Exception {
        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cachorros"));
    }

    @Test
    public void shouldNotFindCategoryById() throws Exception {
        mockMvc.perform(get("/api/categories/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Category not found with id 999")));
    }

    @Test
    public void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Category with ID 3 was deleted successfully.")));
    }

    @Test
    public void shouldNotDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Category not found with ID 999")));
    }



}


