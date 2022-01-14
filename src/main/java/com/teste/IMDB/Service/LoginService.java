package com.teste.IMDB.Service;

import com.teste.IMDB.Dto.LoginDto;
import com.teste.IMDB.Dto.TokenDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SenhaService senhaService;

    public ResponseEntity<TokenDto> login(LoginDto usuarioDto) {
        if (!usuarioService.verificarEmail(usuarioDto.getEmail())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Long idUser = autenticacao(usuarioDto);
        if (idUser == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        TokenDto token = new TokenDto("Bearer", tokenService.generateToken(idUser));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private Long autenticacao(LoginDto usuario) {
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) return null;
        String senha = senhaService.decodeSenha(usuario.getSenha());
        senha = senhaService.criptografaSenha(senha);
        usuario.setSenha(senha);
        return usuarioService.findIdbyEmailAndSenha(usuario);
    }

}
