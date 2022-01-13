package com.teste.IMDB.Controller;

import java.util.List;

import com.teste.IMDB.Dto.UsuarioDtoIn;
import com.teste.IMDB.Dto.UsuarioDtoOut;
import com.teste.IMDB.Service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<UsuarioDtoOut> listAllUsersComuns() {
        return usuarioService.listAllUsersComuns();
    }

    @GetMapping("list/page={pag}")
    public Page<UsuarioDtoOut> listAllUsersComunsWithPage(@PathVariable int pag) {
        return usuarioService.listAllUsersComunsWithPage(pag);
    }

    @PostMapping("save")
    public ResponseEntity<UsuarioDtoOut> saveUser(@RequestBody UsuarioDtoIn user) {
        return usuarioService.saveUser(user, false);
    }

    @PostMapping("save/Admin")
    public ResponseEntity<UsuarioDtoOut> saveAdmin(@RequestBody UsuarioDtoIn user) {
        return usuarioService.saveUser(user, true);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<UsuarioDtoOut> updateUser(@PathVariable long id, @RequestBody UsuarioDtoIn user) {
        return usuarioService.update(id, user);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<UsuarioDtoOut> deleteUser(@PathVariable long id) {
        return usuarioService.delete(id);
    }

}
