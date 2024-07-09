package com.example.projeto_desafio.mapper;


import com.example.projeto_desafio.dto.AnimalDTO;
import com.example.projeto_desafio.entity.Animal;
import com.example.projeto_desafio.entity.Categoria;
import com.example.projeto_desafio.repository.CategoriaRepository;

import java.time.LocalDate;
import java.time.Period;

public class AnimalMapper {

    public static AnimalDTO toDTO(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(animal.getId());
        dto.setNome(animal.getNome());
        dto.setDescricao(animal.getDescricao());
        dto.setUrlImagem(animal.getUrlImagem());
        dto.setDataNascimento(animal.getDataNascimento());
        dto.setStatus(animal.getStatus());
        if (animal.getCategoria() != null) {
            dto.setNomeCategoria(animal.getCategoria().getNome());
        }
        dto.setIdade(getIdade(animal.getDataNascimento()));
        return dto;

    }

    public static Animal toEntity(AnimalDTO dto, CategoriaRepository categoriaRepository) {
        Animal animal = new Animal();
//        animal.setId(dto.getId());
        animal.setNome(dto.getNome());
        animal.setDescricao(dto.getDescricao());
        animal.setUrlImagem(dto.getUrlImagem());
        animal.setDataNascimento(dto.getDataNascimento());
        animal.setStatus(dto.getStatus());

        if (dto.getNomeCategoria() != null) {
            Categoria categoria = findOrCreateCategoria(dto.getNomeCategoria(), categoriaRepository);
            animal.setCategoria(categoria);
        }

        return animal;
    }

    public static Categoria findOrCreateCategoria(String nome, CategoriaRepository categoriaRepository) {
        Categoria categoria = categoriaRepository.findByNome(nome);
        if (categoria == null) {
            categoria = new Categoria();
            categoria.setNome(nome);
            categoria = categoriaRepository.save(categoria);
        }
        return categoria;
    }
    public static String getIdade(LocalDate dataNascimento) {
        if (dataNascimento != null) {
            Period periodo = Period.between(dataNascimento, LocalDate.now());
            return String.format("%d anos, %d meses e %d dias",
                    periodo.getYears(),
                    periodo.getMonths(),
                    periodo.getDays());
        }
        return null;
    }
}