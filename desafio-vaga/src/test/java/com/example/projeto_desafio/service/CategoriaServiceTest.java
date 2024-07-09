package com.example.projeto_desafio.service;

import com.example.projeto_desafio.dto.CategoriaDTO;
import com.example.projeto_desafio.entity.Categoria;
import com.example.projeto_desafio.exception.EntityNotFoundException;
import com.example.projeto_desafio.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private Categoria categoria1;
    private Categoria categoria2;
    private CategoriaDTO categoriaDTO1;

    @BeforeEach
    public void setup() {
        categoria1 = new Categoria();
        categoria1.setId(1);
        categoria1.setNome("Cachorros");

        categoria2 = new Categoria();
        categoria2.setId(2);
        categoria2.setNome("Gatos");

        categoriaDTO1 = new CategoriaDTO();
        categoriaDTO1.setNome("Acessórios");
    }

    @Test
    public void testListAll() {
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria1, categoria2));
        List<Categoria> result = categoriaService.listAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cachorros", result.get(0).getNome());
        assertEquals("Gatos", result.get(1).getNome());
    }

    @Test
    public void testFindByIdSuccess() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria1));
        Optional<Categoria> result = categoriaService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Cachorros", result.get().getNome());
    }

    @Test
    public void testFindByIdNotFound() {
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());
        Optional<Categoria> result = categoriaService.findById(99);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> {
            Categoria categoria = invocation.getArgument(0);
            categoria.setId(3); // Simulate setting an ID after save
            return categoria;
        });
        CategoriaDTO savedCategoriaDTO = categoriaService.save(categoriaDTO1);
        assertNotNull(savedCategoriaDTO);
        assertEquals("Acessórios", savedCategoriaDTO.getNome());
        assertNotNull(savedCategoriaDTO.getId()); // Ensure ID is set
    }

    @Test
    public void testUpdateSuccess() {
        when(categoriaRepository.existsById(1)).thenReturn(true);
        when(categoriaRepository.save(categoria1)).thenReturn(categoria1);
        Categoria result = categoriaService.update(categoria1);
        assertNotNull(result);
        assertEquals("Cachorros", result.getNome());
    }

    @Test
    public void testUpdateNotFound() {
        when(categoriaRepository.existsById(99)).thenReturn(false);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> categoriaService.update(categoria2));
        assertEquals("Category with ID " + categoria2.getId() + " not found.", exception.getMessage());
    }

    @Test
    public void testDeleteSuccess() {
        when(categoriaRepository.existsById(1)).thenReturn(true);
        assertDoesNotThrow(() -> categoriaService.delete(1));
        verify(categoriaRepository).deleteById(1);
    }

    @Test
    public void testDeleteNotFound() {
        when(categoriaRepository.existsById(99)).thenReturn(false);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> categoriaService.delete(99));
        assertEquals("Category with ID " + 99 + " not found.", exception.getMessage());
    }
}
