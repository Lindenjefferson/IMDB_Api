package com.teste.IMDB.Repository;

import java.util.List;
import java.util.Optional;

import com.teste.IMDB.Model.Voto;
import com.teste.IMDB.Model.Filme;
import com.teste.IMDB.Model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    
    List<Voto> findAllByFilme(Filme filme);

    @Query(value = "select filme_id, avg(nota) from voto order by filme_id", nativeQuery = true)
    List<Object[]> findRatingbyFilme();

    @Query("select v from Voto v where filme = :filme and usuario = :usuario")
    Optional<Voto> findByVoto(@Param("filme") Filme filme, @Param("usuario") Usuario usuario);

}
