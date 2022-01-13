package com.teste.IMDB.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.teste.IMDB.Dto.UsuarioDtoIn;
import com.teste.IMDB.Dto.UsuarioDtoOut;
import com.teste.IMDB.Model.Usuario;
import com.teste.IMDB.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Apenas se for admin
    public Page<UsuarioDtoOut> listAllUsersComunsWithPage(int pag) {
        PageRequest pageRequest = PageRequest.of(pag, 10, Sort.Direction.ASC, "nome");
        return usuarioRepository.findAllUserComunsAtivosWithPage(pageRequest)
        .map(this::convertToObjectDto);
    }

    // Apenas se for admin
    public List<UsuarioDtoOut> listAllUsersComuns() {
        List<Usuario> usuario = usuarioRepository.findAllUserComunsAtivos();
        return usuario.stream().map(this::convertToObjectDto).collect(Collectors.toList());
    }

    private UsuarioDtoOut convertToObjectDto(Usuario user) {
        return new UsuarioDtoOut(user);
    }

    public ResponseEntity<UsuarioDtoOut> save(Usuario user) {
        Usuario userSave = usuarioRepository.save(user);
        return new ResponseEntity<>(new UsuarioDtoOut(userSave), HttpStatus.CREATED);
    }

    public ResponseEntity<UsuarioDtoOut> saveUser(UsuarioDtoIn userDto, boolean admin) {
        Usuario user = new Usuario(userDto, admin);
        return save(user);
    }

    public ResponseEntity<UsuarioDtoOut> update(long id, UsuarioDtoIn userDto) {
        Optional<Usuario> user = usuarioRepository.findByIdAndAtivo(id);
        if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        user.get().setNome(userDto.getNome());
        user.get().setEmail(userDto.getEmail());
        if (!userDto.getSenha().isEmpty()) user.get().setSenha(userDto.getSenha());
        usuarioRepository.save(user.get());
        return new ResponseEntity<>(new UsuarioDtoOut(user.get()),HttpStatus.OK);
    }

    public ResponseEntity<UsuarioDtoOut> delete(Long id) {
        Optional<Usuario> user = usuarioRepository.findByIdAndAtivo(id);
        if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        user.get().setAtivo(false);
        usuarioRepository.save(user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
