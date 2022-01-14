package com.teste.IMDB.Controller;

import java.util.List;

import com.teste.IMDB.Dto.UsuarioDtoIn;
import com.teste.IMDB.Dto.UsuarioDtoOut;
import com.teste.IMDB.Service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("list")
    public ResponseEntity<List<UsuarioDtoOut>> listAllUsersComuns(@RequestHeader HttpHeaders headers) {
        return usuarioService.listAllUsersComuns(headers);
    }

    @GetMapping("list/page={pag}")
    public ResponseEntity<Page<UsuarioDtoOut>> listAllUsersComunsWithPage(@PathVariable int pag, @RequestHeader HttpHeaders headers) {
        return usuarioService.listAllUsersComunsWithPage(pag, headers);
    }

    @PostMapping("save")
    public ResponseEntity<String> saveUser(@RequestBody UsuarioDtoIn user) {
        return usuarioService.saveUser(user, false, null);
    }

    @PostMapping("save/Admin")
    public ResponseEntity<String> saveAdmin(@RequestBody UsuarioDtoIn user, @RequestHeader HttpHeaders headers) {
        return usuarioService.saveUser(user, true, headers);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<UsuarioDtoOut> updateUser(@PathVariable long id, @RequestBody UsuarioDtoIn user, @RequestHeader HttpHeaders headers) {
        return usuarioService.update(id, user, headers);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<UsuarioDtoOut> deleteUser(@PathVariable long id, @RequestHeader HttpHeaders headers) {
        return usuarioService.delete(id, headers);
    }

}
