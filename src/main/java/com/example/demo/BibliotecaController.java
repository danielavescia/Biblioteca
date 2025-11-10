package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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
    public ResponseEntity<String> mensagemDeBemVindo() {
        return ResponseEntity.ok("Bem vindo a biblioteca central!");
    }

    @GetMapping("/livros")
    public ResponseEntity<List<Livro>> getLivros(){
        
        if(livros.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livro/id/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable int id){

        return livros.stream()
                    .filter(l-> l.getId() == id)
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/livro/autor/{autor}")
    public ResponseEntity<List<Livro>> getLivrosByAutor(@PathVariable String autor){
       
        var livroAutor = livros.stream()
       .filter(l-> l.getAutor()
       .equalsIgnoreCase(autor))
       .toList();
       
       if(livroAutor.isEmpty()){
        return ResponseEntity.notFound().build();
       }

        return ResponseEntity.ok(livroAutor);
    }
    
    @GetMapping("/livro/titulo/{titulo}")
    public ResponseEntity<List<Livro>> getLivrosByTitulo(@PathVariable String titulo){

        var t = livros.stream()
        .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
        .toList();

        if(titulo == null){
             return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(t);
    }

    @GetMapping("/autores")
    public ResponseEntity<List<String>> getAutores(){
       
        var autores =  livros.stream()
       .map(Livro::getAutor)
       .distinct()
       .toList();

        if(autores.isEmpty()){
             return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(autores);   
        
    }
    
    @PostMapping("/livro/cadastra")
    public ResponseEntity<?> cadastraLivro(@RequestBody Livro livroCriado) {
        
        if(livroCriado == null){
            return ResponseEntity.badRequest().body("Preencha o cadastro para adicionar o livro"); 
        }
        
        livros.add(livroCriado);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(livroCriado); // retorna status 201
    }
    

}
