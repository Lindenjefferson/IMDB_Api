package com.teste.IMDB.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.teste.IMDB.Dto.FilmeDtoIn;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nome;
    private String diretor;
    private String genero;
    private String atores;

    public Filme(FilmeDtoIn dto) {
        this.nome = dto.getNome();
        this.diretor = dto.getDiretor();
        this.genero = dto.getGenero();
        this.atores = dto.getAtores();
    }

}
