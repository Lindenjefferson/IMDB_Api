package com.teste.IMDB.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.teste.IMDB.Dto.VotoDto;
import com.teste.IMDB.Model.Filme;
import com.teste.IMDB.Model.Usuario;
import com.teste.IMDB.Model.Voto;
import com.teste.IMDB.Repository.FilmeRepository;
import com.teste.IMDB.Repository.VotoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
    
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private FilmeRepository filmeRepository;
    @Autowired
    private AuthService authService;

    public double mediaVotos(Filme filme) {
        List<Voto> votos = votoRepository.findAllByFilme(filme);
        int quantVotos = votos.size();
        if (quantVotos == 0) return 0.0;
        int somaVotos = 0;
        for (Voto voto : votos) somaVotos += voto.getNota();
        return (double) somaVotos / quantVotos;
    }

    public Map<Long, Double> mediaVotosPorFilme() {
        List<Object[]> list = votoRepository.findRatingbyFilme();
        Map<Long, Double> mapIdAndRate = new HashMap<>();
        for (Object[] obj : list) mapIdAndRate.put(((BigInteger) obj[0]).longValue(), ((BigDecimal) obj[1]).doubleValue());
        return mapIdAndRate;
    }

    public ResponseEntity<String> votar(VotoDto votoDto, HttpHeaders headers) {
        Usuario usuarioLogado = authService.autorizacao(headers);
        if (usuarioLogado == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (votoDto.getNota() < 0 || votoDto.getNota() > 4) {
            return new ResponseEntity<>("A nota está fora do range de 0 a 4.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Filme> film = filmeRepository.findById(votoDto.getIdFilme());
        if (film.isEmpty()) return new ResponseEntity<>("Filme não econtrado.", HttpStatus.NOT_FOUND);
        Optional<Voto> voto = votoRepository.findByVoto(film.get(), usuarioLogado);
        if (voto.isEmpty()) votoRepository.save(new Voto(usuarioLogado, film.get(), votoDto.getNota()));
        else {
            voto.get().setNota(votoDto.getNota());
            votoRepository.save(voto.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
