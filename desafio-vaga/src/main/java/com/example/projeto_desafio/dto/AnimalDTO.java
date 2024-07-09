package com.example.projeto_desafio.dto;


import com.example.projeto_desafio.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AnimalDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private String urlImagem;
    private String nomeCategoria;
    private LocalDate dataNascimento;
    private Status status;
    private String idade;
}
