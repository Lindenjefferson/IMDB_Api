package com.teste.IMDB.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.teste.IMDB.Dto.FilmeDtoIn;
import com.teste.IMDB.Dto.FilmeDtoOut;
import com.teste.IMDB.Model.Filme;
import com.teste.IMDB.Repository.FilmeCriteria;
import com.teste.IMDB.Repository.FilmeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    // Apenas se for admin
    public ResponseEntity<FilmeDtoOut> save(FilmeDtoIn filmeDto) {
        Filme filmeSave = filmeRepository.save(new Filme(filmeDto));
        return new ResponseEntity<>(new FilmeDtoOut(filmeSave), HttpStatus.CREATED);
    }

    public List<FilmeDtoOut> findAllOrderByRate() {
        List<FilmeDtoOut> filmesDto = mergeFilmesWithRate();
        return filmesDto.stream()
            .sorted((f1, f2) -> f1.getNotaMedia().compareTo(f2.getNotaMedia()))
            .collect(Collectors.toList());
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

    public Page<FilmeDtoOut> listByFilterWithPage(FilmeDtoIn filtro, int pag) {
        PageRequest pageRequest = PageRequest.of(pag, 10, Sort.Direction.ASC, "nome");
        return filmeCriteria.findAllByCriteriaWithPage(filtro, pageRequest).map(this::convertToObjectDto);
    }

    public List<FilmeDtoOut> listByFilter(FilmeDtoIn filtro) {
        List<Filme> filmes = filmeCriteria.findAllByCriteria(filtro);
        return filmes.stream().map(this::convertToObjectDto).collect(Collectors.toList());
    }

    private FilmeDtoOut convertToObjectDto(Filme filme) {
        return new FilmeDtoOut(filme);
    }

    public ResponseEntity<FilmeDtoOut> detailFilme(long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        if (!filme.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        FilmeDtoOut filmeDto = new FilmeDtoOut(filme.get(), votoService.mediaVotos(filme.get()));
        return new ResponseEntity<>(filmeDto, HttpStatus.OK);
    }

}
