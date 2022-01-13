package com.teste.IMDB.Dto;

import com.teste.IMDB.Model.Usuario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDtoOut {

    private Long id;
    private String email;
    private String nome;
    
    public UsuarioDtoOut(Usuario user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nome = user.getNome();
    }

}
