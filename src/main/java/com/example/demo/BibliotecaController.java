package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biblioteca") //adicionando raiz no controlador para agrupar os endpoints
public class BibliotecaController {

     private List<Livro> livros = new ArrayList<>();

     public BibliotecaController(){
       
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

    @GetMapping
    public String mensagemDeBemVindo() {
        return "Bem vindo a biblioteca central!";
    }

    @GetMapping("/livros")
    public List<Livro> getLivros(){
        return livros;
    }

    @GetMapping("/livro/id/{id}")
    public List<Livro> getLivroById(@PathVariable int id){
      return  livros.stream()
      .filter(l-> l.getId() == id)
      .toList();
    }

    @GetMapping("/livro/autor/{autor}")
    public List<Livro> getLivrosByAutor(@PathVariable String autor){
       return livros.stream()
       .filter(l-> l.getAutor()
       .equalsIgnoreCase(autor))
       .toList();
    }
    
    @GetMapping("/livro/titulo/{titulo}")
    public List<Livro> getLivrosByTitulo(@PathVariable String titulo){
        return livros.stream()
        .filter(l-> l.getTitulo()
        .equalsIgnoreCase(titulo))
        .toList();
    }

    @GetMapping("/autores")
    public List<String> getLivrosByTitulo(){
        return livros.stream()
        .map(Livro::getAutor)
        .distinct()
        .toList();
        
    }
    

}
