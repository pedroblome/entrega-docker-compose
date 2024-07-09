package com.example.projeto_desafio.mapper;

import com.example.projeto_desafio.dto.CategoriaDTO;
import com.example.projeto_desafio.entity.Categoria;

public class CategoriaMapper {

    public static CategoriaDTO toDTO(Categoria categoria){
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        return dto;
    }

    public static Categoria toEntity(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        return categoria;
    }
}
