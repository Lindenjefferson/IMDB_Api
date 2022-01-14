package com.teste.IMDB.Service;

import java.util.Optional;

import com.teste.IMDB.Model.Usuario;
import com.teste.IMDB.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autorizacao(HttpHeaders headers) {
        String header = findHeaderAuth(headers);
        if (header == null ) return null;
        String token = tokenService.getTokenFromHeader(header);
        if (token == null || !tokenService.isTokenValid(token)) return null;
        Long id = tokenService.getTokenId(token);
        Optional<Usuario> user = usuarioRepository.findByIdAndAtivo(id);
        if (user.isEmpty()) return null;
        return user.get();
    }

    private String findHeaderAuth(HttpHeaders headers) {
        return headers.getFirst("Authorization");
    }

}
