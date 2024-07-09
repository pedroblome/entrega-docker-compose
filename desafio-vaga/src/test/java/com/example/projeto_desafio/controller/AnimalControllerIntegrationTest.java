package com.example.projeto_desafio.controller;

import com.example.projeto_desafio.dto.AnimalDTO;
import com.example.projeto_desafio.entity.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateAnimal() throws Exception {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setNome("Buddy");
        animalDTO.setDescricao("Cachorro calmo e docil.");
        animalDTO.setNomeCategoria("Cachorros");
        animalDTO.setDataNascimento(LocalDate.of(2015, 1, 1));
        animalDTO.setUrlImagem("https://cdn.pixabay.com/photo/2023/12/12/21/20/dog-8445917_1280.jpg");
        animalDTO.setStatus(Status.DISPONIVEL);

        // Defina os outros campos conforme necessário

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animalDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Buddy"))
                .andExpect(jsonPath("$.nomeCategoria").value("Cachorros"))
                .andExpect(jsonPath("$.descricao").value("Cachorro calmo e docil."))
                .andExpect(jsonPath("$.dataNascimento").value("2015-01-01"))
                .andExpect(jsonPath("$.urlImagem").value("https://cdn.pixabay.com/photo/2023/12/12/21/20/dog-8445917_1280.jpg"))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));

    }

    @Test
    public void shouldListAllAnimals() throws Exception {
        mockMvc.perform(get("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))  //
                .andExpect(jsonPath("$[0].nome").value("Rex"))
                .andExpect(jsonPath("$[0].descricao").value("Gato carinhoso e bastante dócil"))
                .andExpect(jsonPath("$[0].urlImagem").value("https://img.freepik.com/fotos-premium/gato-listrado-de-raca-mista-sentado_191971-20577.jpg?ga=GA1.1.1960310311.1720063059&semt=sph"))
                .andExpect(jsonPath("$[0].nomeCategoria").value("Gatos"))
                .andExpect(jsonPath("$[0].dataNascimento").value("2020-01-01"))
                .andExpect(jsonPath("$[0].status").value("DISPONIVEL"));


    }

    @Test
    public void shouldGetAnimalById() throws Exception {
        mockMvc.perform(get("/api/animals/1")  // Suponha que o ID 1 exista
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Rex"))
                .andExpect(jsonPath("$.descricao").value("Gato carinhoso e bastante dócil"))
                .andExpect(jsonPath("$.urlImagem").value("https://img.freepik.com/fotos-premium/gato-listrado-de-raca-mista-sentado_191971-20577.jpg?ga=GA1.1.1960310311.1720063059&semt=sph"))
                .andExpect(jsonPath("$.nomeCategoria").value("Gatos"))
                .andExpect(jsonPath("$.dataNascimento").value("2020-01-01"))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));

    }

    @Test
    public void shouldNotFindAnimalById() throws Exception {
        mockMvc.perform(get("/api/animals/999")  // ID 999 não exista
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateAnimalStatus() throws Exception {
        mockMvc.perform(put("/api/animals/changeStatus/2")  //
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("ADOTADO"));
    }

    @Test
    public void shouldNotUpdateAnimalStatus() throws Exception {
        mockMvc.perform(put("/api/animals/changeStatus/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


}
