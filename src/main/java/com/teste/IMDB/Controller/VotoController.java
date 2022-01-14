package com.teste.IMDB.Controller;

import com.teste.IMDB.Dto.VotoDto;
import com.teste.IMDB.Service.VotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("voto")
public class VotoController {
    
    @Autowired
    private VotoService votoService;

    @PostMapping
    public ResponseEntity<String> votar(@RequestBody VotoDto voto, @RequestHeader HttpHeaders headers) {
        return votoService.votar(voto, headers);
    }

}
