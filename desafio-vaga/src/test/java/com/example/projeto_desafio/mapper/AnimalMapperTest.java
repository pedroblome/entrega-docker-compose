package com.example.projeto_desafio.mapper;

import com.example.projeto_desafio.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import com.example.projeto_desafio.dto.AnimalDTO;
import com.example.projeto_desafio.entity.Animal;
import com.example.projeto_desafio.entity.Categoria;
import com.example.projeto_desafio.repository.CategoriaRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AnimalMapperTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;
    private Animal animal;
    private AnimalDTO animalDTO;

    @BeforeEach
    public void setup() {
        // Set up Categoria
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Gatos");

        // Set up Animal
        animal = new Animal();
        animal.setId(1);
        animal.setNome("Rex");
        animal.setDescricao("Friendly dog");
        animal.setUrlImagem("http://example.com/image.jpg");
        animal.setDataNascimento(LocalDate.of(2010, 1, 1));
        animal.setStatus(Status.DISPONIVEL);
        animal.setCategoria(categoria);

        // Set up AnimalDTO
        animalDTO = new AnimalDTO();
        animalDTO.setNome("Rex");
        animalDTO.setDescricao("Friendly dog");
        animalDTO.setUrlImagem("http://example.com/image.jpg");
        animalDTO.setDataNascimento(LocalDate.of(2010, 1, 1));
        animalDTO.setStatus(Status.DISPONIVEL);
        animalDTO.setNomeCategoria("Gatos");
    }

    @Test
    public void testToDTO() {
        AnimalDTO dto = AnimalMapper.toDTO(animal);
        assertEquals(animal.getId(), dto.getId());
        assertEquals(animal.getNome(), dto.getNome());
        assertEquals(animal.getDescricao(), dto.getDescricao());
        assertEquals(animal.getUrlImagem(), dto.getUrlImagem());
        assertEquals(animal.getDataNascimento(), dto.getDataNascimento());
        assertEquals(animal.getStatus(), dto.getStatus());
        assertEquals(categoria.getNome(), dto.getNomeCategoria());
        assertNotNull(dto.getIdade());
    }

    @Test
    public void testToEntity() {
        when(categoriaRepository.findByNome("Gatos")).thenReturn(categoria);

        Animal result = AnimalMapper.toEntity(animalDTO, categoriaRepository);
        assertEquals(animalDTO.getNome(), result.getNome());
        assertEquals(animalDTO.getDescricao(), result.getDescricao());
        assertEquals(animalDTO.getUrlImagem(), result.getUrlImagem());
        assertEquals(animalDTO.getDataNascimento(), result.getDataNascimento());
        assertEquals(animalDTO.getStatus(), result.getStatus());
        assertNotNull(result.getCategoria());
        assertEquals(categoria, result.getCategoria());
    }

    @Test
    public void testFindOrCreateCategoriaFound() {
        when(categoriaRepository.findByNome("Gatos")).thenReturn(categoria);

        Categoria foundCategoria = AnimalMapper.findOrCreateCategoria("Gatos", categoriaRepository);
        assertEquals(categoria, foundCategoria);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    public void testFindOrCreateCategoriaNotFound() {
        when(categoriaRepository.findByNome("Cachorros")).thenReturn(null);
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(i -> i.getArguments()[0]);

        Categoria createdCategoria = AnimalMapper.findOrCreateCategoria("Cachorros", categoriaRepository);
        assertNotNull(createdCategoria);
        assertEquals("Cachorros", createdCategoria.getNome());
        verify(categoriaRepository).save(any(Categoria.class));
    }
}
