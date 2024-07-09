package com.example.projeto_desafio.controller;

import com.example.projeto_desafio.dto.AnimalDTO;
import com.example.projeto_desafio.entity.Animal;
import com.example.projeto_desafio.exception.EntityNotFoundException;
import com.example.projeto_desafio.service.AnimalService;
import com.example.projeto_desafio.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {


    private final AnimalService animalService;
    private final CategoriaService categoriaService;


    public AnimalController(AnimalService animalService,CategoriaService categoriaService) {
        this.animalService = animalService;
        this.categoriaService =  categoriaService;
    }




    @GetMapping
    public ResponseEntity<?> getAllAnimals() {

        try {
            List<AnimalDTO> animalsDTO = animalService.listAll();
            return ResponseEntity.ok(animalsDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimalById(@PathVariable Integer id) {
        try {
            AnimalDTO animalDTO = animalService.findById(id);
            return ResponseEntity.ok(animalDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> createAnimal(@RequestBody AnimalDTO animal) {
        try {
            AnimalDTO savedAnimal = animalService.save(animal);
            return new ResponseEntity<>(savedAnimal, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Integer id) {
        try {
            animalService.delete(id);
            String message = "Animal de ID " + id + " foi deletado com sucesso.";
            return ResponseEntity.ok(message);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> updateStatusAnimal(@PathVariable Integer id) {
        try {
            AnimalDTO updatedStatusAnimal = animalService.updateStatus(id);
            return new ResponseEntity<>(updatedStatusAnimal, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
