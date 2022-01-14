package com.teste.IMDB.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.teste.IMDB.Dto.FilmeDtoIn;
import com.teste.IMDB.Dto.FilmeDtoOut;
import com.teste.IMDB.Model.Filme;
import com.teste.IMDB.Model.Usuario;
import com.teste.IMDB.Repository.FilmeCriteria;
import com.teste.IMDB.Repository.FilmeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {
    
    @Autowired
    private FilmeRepository filmeRepository;
    @Autowired
    private FilmeCriteria filmeCriteria;
    @Autowired
    private VotoService votoService;
    @Autowired
    private AuthService authService;

    public ResponseEntity<FilmeDtoOut> save(FilmeDtoIn filmeDto, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null || !usuarioLogado.isAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Filme filmeSave = filmeRepository.save(new Filme(filmeDto));
        return new ResponseEntity<>(new FilmeDtoOut(filmeSave), HttpStatus.CREATED);
    }

    public ResponseEntity<List<FilmeDtoOut>> findAllOrderByRate(HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<FilmeDtoOut> filmesDto = mergeFilmesWithRate();
        List<FilmeDtoOut> list = filmesDto.stream()
            .sorted((f1, f2) -> f1.getNotaMedia().compareTo(f2.getNotaMedia()))
            .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private List<FilmeDtoOut> mergeFilmesWithRate() {
        List<Filme> filmes = filmeRepository.findAll();
        Map<Long, Double> mediaVotosPorFilme = votoService.mediaVotosPorFilme();
        List<FilmeDtoOut> filmesDto = new ArrayList<>();
        for (Filme filme : filmes) {
            double rate = 0.0;
            if (mediaVotosPorFilme.get(filme.getId()) != null) {
                rate = mediaVotosPorFilme.get(filme.getId());
            }
            FilmeDtoOut filmeDto = new FilmeDtoOut(filme, rate);
            filmesDto.add(filmeDto);
        }
        return filmesDto;
    }

    public ResponseEntity<Page<FilmeDtoOut>> listByFilterWithPage(FilmeDtoIn filtro, int pag, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        PageRequest pageRequest = PageRequest.of(pag, 10, Sort.Direction.ASC, "nome");
        Page<FilmeDtoOut> page = filmeCriteria.findAllByCriteriaWithPage(filtro, pageRequest)
        .map(this::convertToObjectDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    public ResponseEntity<List<FilmeDtoOut>> listByFilter(FilmeDtoIn filtro, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<Filme> filmes = filmeCriteria.findAllByCriteria(filtro);
        List<FilmeDtoOut> list = filmes.stream().map(this::convertToObjectDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private FilmeDtoOut convertToObjectDto(Filme filme) {
        return new FilmeDtoOut(filme);
    }

    public ResponseEntity<FilmeDtoOut> detailFilme(long id, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Filme> filme = filmeRepository.findById(id);
        if (!filme.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        FilmeDtoOut filmeDto = new FilmeDtoOut(filme.get(), votoService.mediaVotos(filme.get()));
        return new ResponseEntity<>(filmeDto, HttpStatus.OK);
    }

}
