package com.teste.IMDB.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDtoIn {
    
    private String email;
    private String nome;
    private String senha;

}
