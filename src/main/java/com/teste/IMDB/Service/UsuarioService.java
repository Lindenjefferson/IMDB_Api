package com.teste.IMDB.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.teste.IMDB.Dto.LoginDto;
import com.teste.IMDB.Dto.UsuarioDtoIn;
import com.teste.IMDB.Dto.UsuarioDtoOut;
import com.teste.IMDB.Model.Usuario;
import com.teste.IMDB.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private SenhaService senhaService;

    public ResponseEntity<Page<UsuarioDtoOut>> listAllUsersComunsWithPage(int pag, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null || !usuarioLogado.isAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        PageRequest pageRequest = PageRequest.of(pag, 10, Sort.Direction.ASC, "nome");
        Page<UsuarioDtoOut> page = usuarioRepository.findAllUserComunsAtivosWithPage(pageRequest)
        .map(this::convertToObjectDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    public ResponseEntity<List<UsuarioDtoOut>> listAllUsersComuns(HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null || !usuarioLogado.isAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<Usuario> usuario = usuarioRepository.findAllUserComunsAtivos();
        List<UsuarioDtoOut> list = usuario.stream().map(this::convertToObjectDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<String> save(Usuario usuario) {
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        String senha = senhaService.decodeSenha(usuario.getSenha());
        senha = senhaService.criptografaSenha(senha);
        usuario.setSenha(senha);
        usuarioRepository.save(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<String> saveUser(UsuarioDtoIn userDto, boolean admin, HttpHeaders headers) {
        if (admin) {
            Usuario usuarioLogado = authService.autorizacao(headers);
            if (usuarioLogado == null || !usuarioLogado.isAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (verificarEmail(userDto.getEmail())) return new ResponseEntity<>("Email ja cadastrado", HttpStatus.OK);
        Usuario user = new Usuario(userDto, admin);
        return save(user);
    }

    public ResponseEntity<UsuarioDtoOut> update(long id, UsuarioDtoIn userDto, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Usuario> user = usuarioRepository.findByIdAndAtivo(id);
        if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        user.get().setNome(userDto.getNome());
        user.get().setEmail(userDto.getEmail());
        if (!userDto.getSenha().isEmpty()) user.get().setSenha(userDto.getSenha());
        usuarioRepository.save(user.get());
        return new ResponseEntity<>(new UsuarioDtoOut(user.get()),HttpStatus.OK);
    }

    public ResponseEntity<UsuarioDtoOut> delete(Long id, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Usuario> user = usuarioRepository.findByIdAndAtivo(id);
        if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        user.get().setAtivo(false);
        usuarioRepository.save(user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private UsuarioDtoOut convertToObjectDto(Usuario user) {
        return new UsuarioDtoOut(user);
    }

    public boolean verificarEmail(String email) {
        return usuarioRepository.existUser(email);
    }

    public Long findIdbyEmailAndSenha(LoginDto usuario) {
        return usuarioRepository.findIdbyEmailAndSenha(usuario.getEmail(), usuario.getSenha());
    }

}
