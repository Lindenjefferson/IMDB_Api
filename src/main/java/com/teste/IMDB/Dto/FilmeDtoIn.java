package com.teste.IMDB.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilmeDtoIn {
    
    private String nome;
    private String diretor;
    private String genero;
    private String atores;

}
