package com.teste.IMDB.Dto;

import com.teste.IMDB.Model.Filme;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilmeDtoOut {

    private Long id;
    private String nome;
    private String diretor;
    private String genero;
    private String atores;
    private Double notaMedia = 0.0;
    
    public FilmeDtoOut(Filme filme, double mediaVotos) {
        this.id = filme.getId();
        this.nome = filme.getNome();
        this.diretor = filme.getDiretor();
        this.genero = filme.getGenero();
        this.atores = filme.getAtores();
        this.notaMedia = mediaVotos;
    }

    public FilmeDtoOut(Filme filme) {
        this.id = filme.getId();
        this.nome = filme.getNome();
        this.diretor = filme.getDiretor();
        this.genero = filme.getGenero();
        this.atores = filme.getAtores();
    }

}
