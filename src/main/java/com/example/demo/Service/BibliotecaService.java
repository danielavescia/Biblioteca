package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.Model.Livro;

@Service
public class BibliotecaService {

    private List<Livro> livros = new ArrayList<>();
    
    public BibliotecaService(){
        livros.add(
                new Livro(
                    1, 
                    "O Senhor dos Anéis",
                    1954,
                    "J. R. R. Tolkien"
                    )
                );

            livros.add(
                new Livro(
                    2, 
                    "A Vegetariana", 
                    2007,
                    "Han Kang"
                )
            );

            livros.add(
                new Livro(
                    3, 
                    "Imagens Estranhas", 
                    2025,
                    "Uketsu"
                )
            );

            livros.add( new Livro(
                    4, 
                    "O Livro Branco",
                    2023,
                    "Han Kang"
                    )
                );
    }

    public List<Livro> getLivros(){
        return livros;
    }

    public Livro getLivroById(Integer id){
        return livros.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Livro> getLivrosByAutor(String autor){
        return livros.stream()
            .filter(l-> l.getAutor()
            .equalsIgnoreCase(autor))
            .toList();
    }

    public List<Livro> getLivrosByTitulo(String titulo){
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .toList();

    }

    public List<String> getAutores(){
       
        return livros.stream()
            .map(Livro::getAutor)
            .distinct()
            .toList();
    } 

    public List<Livro> getLivrosPorAno(Integer ano) {
        
        return livros.stream()
            .filter(l-> l
            .getAnoPublicacao() == ano)
            .toList();
    }

     public List<Livro> getLivrosDesatualizados(int ano) {
        
        return livros.stream()
            .filter(l -> l.getAnoPublicacao() < ano)
            .toList();
    }

     public List<Livro> getLivroAutorAno(String autor, Integer ano) {
        
        return  livros.stream()
            .filter(l -> l.getAutor().equalsIgnoreCase(autor))
            .filter(l -> l.getAnoPublicacao() == ano)
            .toList();
    }

    public Livro cadastraLivro(Livro livroCriado) {
        
        livros.add(livroCriado);
        
        return livroCriado;
    }

    public Livro atualizarLivro( int id,  Livro livroDto) {
        
        var livro = livros.stream()
            .filter(l->l.getId() == id)
            .findFirst();

        if(livro == null){
            return null;
        } 

        var livroDomain = livro.get();
        livroDomain.setTitulo(livroDto.getTitulo());
        livroDomain.setAnoPublicacao(livroDto.getAnoPublicacao());
        livroDomain.setAutor(livroDto.getAutor());

        return livroDomain;
    }


}