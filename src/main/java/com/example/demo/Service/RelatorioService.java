package com.example.demo.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

    private final BibliotecaService service;

    public RelatorioService(BibliotecaService service) {
        this.service = service;
    }

    public int totalLivrosPorAutor(String autor){
        return (int) service.getLivros().stream()
        .filter(l -> l.getAutor().equalsIgnoreCase(autor))
        .count();
    }

    public int livrosRecentes(int ano){
        return (int) service.getLivros().stream()
        .filter(l-> l.getAnoPublicacao()> ano)
        .count();
    }

    public double mediaLivrosAutor(){
        var livros = service.getLivros();

        if(livros.isEmpty()) return 0.0;

        int totalLivros = livros.size();
        int totalLivrosPorAutor = livros.stream()
                .map(l -> l.getAutor().toLowerCase())
                .distinct()
                .toList()
                .size();
        
        double mediaFinal = (double) totalLivros/totalLivrosPorAutor;
        
        mediaFinal = new BigDecimal(mediaFinal)
        .setScale(2, RoundingMode.HALF_UP)
        .doubleValue();

        return mediaFinal;
    }

}
