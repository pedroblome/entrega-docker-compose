package com.example.projeto_desafio.service;

import com.example.projeto_desafio.dto.AnimalDTO;
import com.example.projeto_desafio.entity.Animal;
import com.example.projeto_desafio.entity.Status;
import com.example.projeto_desafio.exception.EntityNotFoundException;
import com.example.projeto_desafio.mapper.AnimalMapper;
import com.example.projeto_desafio.repository.AnimalRepository;
import com.example.projeto_desafio.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AnimalServiceTest {

    @Autowired
    private AnimalService animalService;

    @MockBean
    private AnimalRepository animalRepository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private AnimalDTO animalDTO1;
    private AnimalDTO animalDTO2;
    private Animal animal1;
    private Animal animal2;

    private final Integer existingAnimalId = 1;
    private final Integer nonExistingAnimalId = 99;

    @BeforeEach
    public void setup() {
        // Setup for listAll and findById
        animalDTO1 = new AnimalDTO();
        animalDTO1.setNome("Rex");
        animalDTO1.setDescricao("Friendly dog");
        animalDTO1.setDataNascimento(LocalDate.of(2015, 1, 1));
        animalDTO1.setStatus(Status.DISPONIVEL);

        animalDTO2 = new AnimalDTO();
        animalDTO2.setNome("Mia");
        animalDTO2.setDescricao("Calm cat");
        animalDTO2.setDataNascimento(LocalDate.of(2018, 3, 3));
        animalDTO2.setStatus(Status.DISPONIVEL);

        animal1 = AnimalMapper.toEntity(animalDTO1, categoriaRepository);
        animal2 = AnimalMapper.toEntity(animalDTO2, categoriaRepository);

        when(animalRepository.findAll()).thenReturn(Arrays.asList(animal1, animal2));
        when(animalRepository.findById(existingAnimalId)).thenReturn(Optional.of(animal1));
        when(animalRepository.findById(nonExistingAnimalId)).thenReturn(Optional.empty());
    }

    @Test
    public void testListAllSuccess() {
        List<AnimalDTO> result = animalService.listAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Rex", result.get(0).getNome());
        assertEquals("Mia", result.get(1).getNome());
    }

    @Test
    public void testListAllException() {
        when(animalRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () -> animalService.listAll());
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testFindByIdSuccess() {
        AnimalDTO result = animalService.findById(existingAnimalId);
        assertNotNull(result);
        assertEquals("Rex", result.getNome());
        assertEquals("Friendly dog", result.getDescricao());
        assertEquals(LocalDate.of(2015, 1, 1), result.getDataNascimento());
        assertNotNull(result.getIdade());  // Confirmar a idade baseada na data de nascimento
    }

    @Test
    public void testFindByIdNotFound() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> animalService.findById(nonExistingAnimalId));
        assertEquals("Animal not found with ID: " + nonExistingAnimalId, exception.getMessage());
    }

    @Test
    public void testSaveSuccess() {
        AnimalDTO newAnimalDTO = new AnimalDTO();
        newAnimalDTO.setNome("Bella");
        newAnimalDTO.setDescricao("Energetic puppy");
        newAnimalDTO.setDataNascimento(LocalDate.now());
        newAnimalDTO.setStatus(Status.DISPONIVEL);

        Animal newAnimal = AnimalMapper.toEntity(newAnimalDTO, categoriaRepository);
        when(animalRepository.save(any(Animal.class))).thenReturn(newAnimal);

        AnimalDTO savedAnimalDTO = animalService.save(newAnimalDTO);

        assertNotNull(savedAnimalDTO);
        assertEquals("Bella", savedAnimalDTO.getNome());
    }

    @Test
    public void testSaveException() {
        AnimalDTO newAnimalDTO = new AnimalDTO();
        newAnimalDTO.setNome("Bella");
        newAnimalDTO.setDescricao("Energetic puppy");
        newAnimalDTO.setDataNascimento(LocalDate.now());
        newAnimalDTO.setStatus(Status.DISPONIVEL);

        when(animalRepository.save(any(Animal.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> animalService.save(newAnimalDTO));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testDeleteSuccess() {
        when(animalRepository.existsById(existingAnimalId)).thenReturn(true);

        assertDoesNotThrow(() -> animalService.delete(existingAnimalId));
        verify(animalRepository).deleteById(existingAnimalId);
    }

    @Test
    public void testDeleteNotFound() {
        when(animalRepository.existsById(nonExistingAnimalId)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> animalService.delete(nonExistingAnimalId));
        assertEquals("Animal with ID " + nonExistingAnimalId + " not found.", exception.getMessage());
    }

    @Test
    public void testUpdateStatusSuccess() {
        when(animalRepository.existsById(existingAnimalId)).thenReturn(true);
        when(animalRepository.findById(existingAnimalId)).thenReturn(Optional.of(animal1));

        AnimalDTO updatedAnimal = animalService.updateStatus(existingAnimalId);
        assertNotNull(updatedAnimal);
        assertNotEquals(Status.DISPONIVEL, updatedAnimal.getStatus());  // Assuming initial status is DISPONIVEL

        verify(animalRepository).save(animal1);
    }

    @Test
    public void testUpdateStatusNotFound() {
        when(animalRepository.existsById(nonExistingAnimalId)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> animalService.updateStatus(nonExistingAnimalId));
        assertEquals("Animal with ID " + nonExistingAnimalId + " not found.", exception.getMessage());
    }

    // Test for possible exception in updateStatus, e.g., when save fails
    @Test
    public void testUpdateStatusException() {
        when(animalRepository.existsById(existingAnimalId)).thenReturn(true);
        when(animalRepository.findById(existingAnimalId)).thenReturn(Optional.of(animal1));
        doThrow(new RuntimeException("Database error")).when(animalRepository).save(any(Animal.class));

        Exception exception = assertThrows(RuntimeException.class, () -> animalService.updateStatus(existingAnimalId));
        assertEquals("Database error", exception.getMessage());
    }
}
