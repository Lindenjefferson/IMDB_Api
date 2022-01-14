package com.teste.IMDB.Controller;

import java.util.List;

import com.teste.IMDB.Dto.FilmeDtoIn;
import com.teste.IMDB.Dto.FilmeDtoOut;
import com.teste.IMDB.Service.FilmeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("filme")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;
    
    @PostMapping("save")
    public ResponseEntity<FilmeDtoOut> save(@RequestBody FilmeDtoIn filme, @RequestHeader HttpHeaders headers) {
        return filmeService.save(filme, headers);
    }

    @GetMapping("filter/page={pag}")
    public ResponseEntity<Page<FilmeDtoOut>> listByFilterWithPage(@RequestBody FilmeDtoIn filtro, @PathVariable int pag, @RequestHeader HttpHeaders headers) {
        return filmeService.listByFilterWithPage(filtro, pag, headers);
    }

    @GetMapping("filter")
    public ResponseEntity<List<FilmeDtoOut>> listByFilter(@RequestBody FilmeDtoIn filtro, @RequestHeader HttpHeaders headers) {
        return filmeService.listByFilter(filtro, headers);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<FilmeDtoOut> detailFilme(@PathVariable long id, @RequestHeader HttpHeaders headers) {
        return filmeService.detailFilme(id, headers);
    }

    @GetMapping("orderByRate")
    public ResponseEntity<List<FilmeDtoOut>> findAllOrderByRate(@RequestHeader HttpHeaders headers) {
        return filmeService.findAllOrderByRate(headers);
    }

}
