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
import org.springframework.web.bind.annotation.PutMapping;


/**
 * Controlador responsável por gerenciar os endpoints de livros
 */
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
    /**
     * Exibe mensagem de boas-vindas da biblioteca
     * @return status code 200 e a mensangem de boas-vidas
     */
    @GetMapping
    public ResponseEntity<String> mensagemDeBemVindo() {
        return ResponseEntity.ok("Bem vindo a biblioteca central!");
    }

    /**
     * Método responsável por retornar a lista de livros do acervo
     * @return lista de livros e status 200 se há livros, 204 se acervo está vazio
     */

    @GetMapping("/livros")
    public ResponseEntity<List<Livro>> getLivros(){
        
        if(livros.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(livros);
    }

    /**
     * Retorna livro especifico dado pela id que é um parametro
     * @param int id enviado no corpo da requisição
     * @return status code 200 com o objeto requisitado e
     * 404 se id desejada não existe
     
     */
    @GetMapping("/livro/id/{id}")
    public ResponseEntity<?> getLivroById(@PathVariable Integer id){

        return livros.stream()
                    .filter(l-> l.getId() == id)
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

     /**
     * Retorna lista de livro especifico de um autor dado através do request body
     * @param string autor enviado no corpo da requisição
     * @return status code 200 se há livros do autor solicitado 
     * ou status 204 caso não encontre correspondência entre o objeto solicitado e o acervo.
     */
    @GetMapping("/livro/autor/{autor}")
    public ResponseEntity<?> getLivrosByAutor(@PathVariable String autor){

        var livroAutor = livros.stream()
       .filter(l-> l.getAutor()
       .equalsIgnoreCase(autor))
       .toList();
       
       if(livroAutor.isEmpty()){
        return ResponseEntity.noContent().build();
       }

        return ResponseEntity.ok(livroAutor);
    }
    
    /**
     * Retorna lista de livro especifico com o titulo solicitado através do request body
     * @param string titulo enviado no corpo da requisição
     * @return status code 200 se há o titulo solicitado 
     * ou status 204 caso não encontre correspondência entre o titulo desejado.
     */
    @GetMapping("/livro/titulo/{titulo}")
    public ResponseEntity<?> getLivrosByTitulo(@PathVariable String titulo){

        var t = livros.stream()
        .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
        .toList();

        if(t.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(t);
    }

    /**
     * Retorna lista de string com os autores disponíveis no acervo 
     * 
     * @return status code 200 se há autores cadastrados ou status 204 caso não há autores cadastrados
     */
    @GetMapping("/autores")
    public ResponseEntity<List<String>> getAutores(){
       
        var autores =  livros.stream()
       .map(Livro::getAutor)
       .distinct()
       .toList();

        if(autores.isEmpty()){
              return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(autores);   
        
    }

    /**
     * Retorna lista de objetos do tipo livro com os livros publicados no ano solicitada via parametro 
     * @param Integer ano de publicação solicitado via parâmetro
     * @return status code 200 se há livros publicados no ano desejado, 
     * ou status 204 caso não encontra correspondência.
     */
    @GetMapping("/livros/porAno/{ano}")
    public ResponseEntity<?> getLivrosPorAno(@PathVariable Integer ano) {
        
        var livrosDomain = livros.stream()
        .filter(l-> l
        .getAnoPublicacao() == ano)
        .toList();

        if(livrosDomain.isEmpty()) return ResponseEntity.noContent().build();
        
        return ResponseEntity.ok(livrosDomain);
    }

    /**
     * Retorna uma lista dos livros cujo ano de publicação é inferior ao ano informado.
     * @param int ano dsejado para filtrar os livros mais antigos.
     * @return lista de objetos tipo livro que possuem a data menor que a informada por parâmetro,
     * caso não haja correspondência retorna 204 No Content
     */
    @GetMapping("livros/desatualizados/{ano}")
    public ResponseEntity<List<Livro>> getLivrosDesatualizados(@PathVariable int ano) {
        var livrosDomain = livros.stream()
        .filter(l -> l.getAnoPublicacao() < ano)
        .toList();

        if( livrosDomain .isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(livrosDomain);
    }

    /**
     * Retorna uma lista dos livros cujo ano de publicação e autor correspondentes aos parâmetros.
     * @param Integer ano dsejado para filtrar anoPublicação e 
     * @param String com o nome do autor desejado
     * @return lista de objetos tipo livro que correspondem aos parâmetros informados.
     */

    @GetMapping("/livros/autor/{autor}/ano/{ano}")
    public ResponseEntity<?> getMethodName(@PathVariable String autor, @PathVariable Integer ano) {
        
        var livrosDomain = livros.stream()
        .filter(l -> l.getAutor().equalsIgnoreCase(autor))
        .filter(l -> l.getAnoPublicacao() == ano)
        .toList();

        if( livrosDomain .isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(livrosDomain);
    }
    
    
    /**
     * Cria um objeto do tipo livro através das informações do body para cadastrar um novo livro no acervo 
     * @param objeto do tipo livro enviado no corpo da requisição
     * @return status code 201 para informar livro criado com sucesso
     */
    @PostMapping("/livro/cadastra")
    public ResponseEntity<?> cadastraLivro(@RequestBody Livro livroCriado) {
        
        livros.add(livroCriado);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(livroCriado); // retorna status 201
    }
    
    /**
     * Endpoint responsável por atualizar todos os dados de um livro, menos id, conforme os 
     * dados fornecidos pelo corpo da mensagem
     * @param id
     * @param livroDto
     * @return o livro atualizado com os dados enviados no corpo da requisição
     */

    @PutMapping("livro/{id}")
    public ResponseEntity<?> atualizarIdLivro(@RequestBody int id, @RequestBody Livro livroDto) {
        
        var livroDomain = livros.stream().filter(l->l.getId() == id).findFirst();

        if(livroDomain.isEmpty()) return ResponseEntity.notFound().build();

        var livro = livroDomain.get();
        livro.setTitulo(livroDto.getTitulo());
        livro.setAnoPublicacao(livroDto.getAnoPublicacao());
        livro.setAutor(livroDto.getAutor());
        livro.setId(id);

        return ResponseEntity.ok(livro);
    }

    
}
