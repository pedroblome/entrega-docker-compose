package com.example.projeto_desafio.repository;

import com.example.projeto_desafio.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
}
