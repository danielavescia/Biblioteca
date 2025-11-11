package com.example.demo.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.*;
import com.example.demo.Service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/biblioteca") //adicionando raiz no controlador para agrupar os endpoints
@Tag(name ="Biblioteca", description ="Controlador com endpoints para gerenciar/pesquisar no acervo ")
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    @GetMapping
    @Operation(summary = "Mensagem de boas-vindas", description = "Exibe mensagem de boas-vindas da biblioteca")
    @ApiResponse(responseCode ="200", description ="Mensagem retornada com sucesso")
    public ResponseEntity<String> mensagemDeBemVindo() {
        return ResponseEntity.ok("Bem vindo a biblioteca central!");
    }


    @GetMapping("/livros")
    @Operation(summary = "Lista livros do acervo", description = "Retorna lista de livros do acervo")
    @ApiResponse(responseCode ="200", description ="Lista de livros retornada com sucesso")
    @ApiResponse(responseCode ="204", description ="Acervo vazio")
    public ResponseEntity<List<Livro>> getLivros(){
        
        var livrosDomain = service.getLivros();

        if(livrosDomain.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(livrosDomain);
    }

    @GetMapping("/livro/id/{id}")
    @Operation(summary = "Pesquisa livro por Id", description = "Retorna o livro de Id correspondente")
    @ApiResponses({
    @ApiResponse(responseCode ="200", description ="Livro encontrado com sucesso"),
    @ApiResponse(responseCode ="404", description ="Id do livro não encontrado")
    })
    public ResponseEntity<Livro> getLivroById(@PathVariable Integer id){
        
        var livroDomain = service.getLivroById(id);

        if(livroDomain == null){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(livroDomain); 
    }

   
    @GetMapping("/livro/autor/{autor}")
    @Operation(summary = "Pesquisa livro por nome de autor", description = "Retorna o livros do autor correspondente")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso"),
    @ApiResponse(responseCode = "204", description = "Nenhum livro encontrado para o autor informado"),
    })
    public ResponseEntity<?> getLivrosByAutor(@PathVariable String autor){

        var livroDomain = service.getLivrosByAutor(autor);
       
       if(livroDomain.isEmpty()){
        return ResponseEntity.noContent().build();
       }

        return ResponseEntity.ok(livroDomain);
    }
    
    
    @GetMapping("/livro/titulo/{titulo}")
    @Operation(summary = "Pesquisa livro pelo titulo", description = "Retorna o livros titulo correspondente")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso"),
    @ApiResponse(responseCode = "204", description = "Nenhum livro encontrado para o titulo informado"),
    })
    public ResponseEntity<?> getLivrosByTitulo(@PathVariable String titulo){

        var livroDomain =service.getLivrosByTitulo(titulo);

        if(livroDomain.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(livroDomain);
    }

    @GetMapping("/autores")
    @Operation(summary = "Retorna lista de autores", description = "Retorna lista de autores do acervo")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Autores encontrados com sucesso"),
    @ApiResponse(responseCode = "204", description = "Acervo vazio"),
    })
    public ResponseEntity<List<String>> getAutores(){
        
        var autoresDomain = service.getAutores();

        if(autoresDomain.isEmpty()){
              return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(autoresDomain);          
    }

    @GetMapping("/livros/porAno/{ano}")
    @Operation(summary = "Retorna lista de livros", description = "Retorna lista de livros publicados no ano correspondente")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Livros encontrados para o ano de publicação desejado"),
    @ApiResponse(responseCode = "204", description = "Livro não foi encontrado para o ano de publicação desejado"),
    })
    public ResponseEntity<?> getLivrosPorAno(@PathVariable Integer ano) {
        
        var livrosDomain =service.getLivrosPorAno(ano);

        if(livrosDomain.isEmpty()){
            return ResponseEntity.noContent().build();
        } 
        
        return ResponseEntity.ok(livrosDomain);
    }

  
    @GetMapping("livros/desatualizados/{ano}")
    @Operation(summary = "Retorna lista de livros", description = "Retorna lista de livros desatualizados inferiores ao ano correspondente")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Livros encontrados para o ano de publicação desejado"),
    @ApiResponse(responseCode = "204", description = "Livro não foi encontrado para o ano de publicação desejado"),
    })
    public ResponseEntity<List<Livro>> getLivrosDesatualizados(@PathVariable int ano) {
        var livrosDomain = service.getLivrosDesatualizados(ano);

        if( livrosDomain .isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(livrosDomain);
    }

    @GetMapping("/livros/autor/{autor}/ano/{ano}")
    @Operation(summary = "Retorna lista de livros", description = "Retorna lista de livros publicados no ano correspondente para o autor correspondente")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Livros encontrados para o ano e autor de publicação desejado"),
    @ApiResponse(responseCode = "204", description = "Livro não foi encontrado para o ano de publicação e autor desejado"),
    })
    public ResponseEntity<?> getLivroAutorAno(@PathVariable String autor, @PathVariable Integer ano) {
        
        var livrosDomain = service.getLivroAutorAno(autor, ano);

        if( livrosDomain .isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(livrosDomain);
    }
    
    @PostMapping("/livro/cadastra")
    @Operation(summary = "Cria objeto livro", description = "Cadastra livro")
    @ApiResponse(responseCode = "201", description = "Livros cadastrado com sucesso")
    
    public ResponseEntity<?> cadastraLivro(@RequestBody Livro livroCriado) {
        
       var livroDomain = service.cadastraLivro(livroCriado);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(livroDomain); // retorna status 201
    }
   
    @PutMapping("livro/{id}")
    @Operation(summary = "Altera dados de um livro", description = "Atualiza os dados de um livro previamente cadastrado")
    @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Livros atualziado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Nenhum livro foi encontrado com a id desejada"),
    })
    

    public ResponseEntity<?> atualizarLivro(@RequestBody int id, @RequestBody Livro livroDto) {
        
        var livroDomain = service.atualizarLivro(id, livroDto);

        if(livroDomain == null){
            return ResponseEntity.notFound().build();
        } 

        return ResponseEntity.ok(livroDomain);
    }
 
}
