package com.teste.IMDB.Repository;

import java.util.List;
import java.util.Optional;

import com.teste.IMDB.Model.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @Query("select u from Usuario u where u.ativo = true and u.admin = false ")
    Page<Usuario> findAllUserComunsAtivosWithPage(Pageable pageable);

    @Query("select u from Usuario u where u.ativo = true and u.admin = false order by u.nome asc")
    List<Usuario> findAllUserComunsAtivos();

    @Query("select u from Usuario u where u.ativo = true and u.id = :id")
    Optional<Usuario> findByIdAndAtivo(@Param("id") long id);

    @Query("select count(u.id) > 0 from Usuario u where u.ativo = true and u.email = :email")
    boolean existUser(@Param("email") String email);

    @Query("select u.id from Usuario u where u.ativo = true and u.email = :email and u.senha = :senha")
    Long findIdbyEmailAndSenha(@Param("email") String email, @Param("senha") String senha);

}
