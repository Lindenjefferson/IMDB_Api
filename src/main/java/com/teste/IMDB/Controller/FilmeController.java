package com.teste.IMDB.Controller;

import java.util.List;

import com.teste.IMDB.Dto.FilmeDtoIn;
import com.teste.IMDB.Dto.FilmeDtoOut;
import com.teste.IMDB.Service.FilmeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("filme")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;
    
    @PostMapping("save")
    public ResponseEntity<FilmeDtoOut> save(@RequestBody FilmeDtoIn filme) {
        return filmeService.save(filme);
    }

    @GetMapping("filter/page={pag}")
    public Page<FilmeDtoOut> listByFilterWithPage(@RequestBody FilmeDtoIn filtro, @PathVariable int pag) {
        return filmeService.listByFilterWithPage(filtro, pag);
    }

    @GetMapping("filter")
    public List<FilmeDtoOut> listByFilter(@RequestBody FilmeDtoIn filtro) {
        return filmeService.listByFilter(filtro);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<FilmeDtoOut> detailFilme(@PathVariable long id) {
        return filmeService.detailFilme(id);
    }

    @GetMapping("orderByRate")
    public List<FilmeDtoOut> findAllOrderByRate() {
        return filmeService.findAllOrderByRate();
    }

}
