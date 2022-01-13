package com.teste.IMDB.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.teste.IMDB.Dto.VotoDto;
import com.teste.IMDB.Model.Filme;
import com.teste.IMDB.Model.Usuario;
import com.teste.IMDB.Model.Voto;
import com.teste.IMDB.Repository.FilmeRepository;
import com.teste.IMDB.Repository.UsuarioRepository;
import com.teste.IMDB.Repository.VotoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
    
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FilmeRepository filmeRepository;

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
        for (Object[] obj : list) mapIdAndRate.put((Long) obj[0], (Double) obj[0]);
        return mapIdAndRate;
    }

    public ResponseEntity<String> votar(VotoDto votoDto) {
        if (votoDto.getNota() < 0 || votoDto.getNota() > 4) {
            return new ResponseEntity<>("A nota está fora do range de 0 a 4.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Usuario> user = usuarioRepository.findByIdAndAtivo(votoDto.getIdUsuario());
        Optional<Filme> film = filmeRepository.findById(votoDto.getIdFilme());
        if (user.isEmpty() || film.isEmpty()) {
            return new ResponseEntity<>("Usuário ou filme não econtrado.", HttpStatus.NOT_FOUND);
        }
        Optional<Voto> voto = votoRepository.findByVoto(film.get(), user.get());
        if (voto.isEmpty()) votoRepository.save(new Voto(user.get(), film.get(), votoDto.getNota()));
        else {
            voto.get().setNota(votoDto.getNota());
            votoRepository.save(voto.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
