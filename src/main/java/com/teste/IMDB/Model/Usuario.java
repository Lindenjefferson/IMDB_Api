package com.teste.IMDB.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.teste.IMDB.Dto.UsuarioDtoIn;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String nome;
    private String senha;
    private boolean ativo = true;
    private boolean admin = false;

    public Usuario(UsuarioDtoIn user, boolean admin) {
        this.email = user.getEmail();
        this.nome = user.getNome();
        this.senha = user.getSenha();
        this.admin = admin;
    }

}
