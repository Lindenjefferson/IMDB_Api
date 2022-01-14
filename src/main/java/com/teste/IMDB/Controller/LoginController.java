package com.teste.IMDB.Controller;

import com.teste.IMDB.Dto.LoginDto;
import com.teste.IMDB.Dto.TokenDto;
import com.teste.IMDB.Service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto usuarioDto) {
        return loginService.login(usuarioDto);
    }

}
