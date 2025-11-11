package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.BibliotecaService;
import com.example.demo.Service.RelatorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/relatorios") //adicionando raiz no controlador para agrupar os endpoints
@Tag(name ="Relatorios", description ="Controlador com endpoints para gerar relatorios estatisticos do acervo ")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/autor/{autor}")
    @Operation(summary = "Retorna quantas obras existem para determinado autor", description = "Retorna um inteiro que representa a quantidade de livros de determinado autor")
    @ApiResponses({
    @ApiResponse(responseCode ="200", description ="Total de livros por autor calculado com sucesso!"),
    @ApiResponse(responseCode ="404", description ="Autor não encontrado")
    })

    public ResponseEntity<Integer> totalLivrosPorAutor(@PathVariable String autor) {
        
        int totalLivros = relatorioService.totalLivrosPorAutor(autor);

        if(totalLivros == 0){
            return ResponseEntity.notFound().build(); 
        }

         return ResponseEntity.ok(totalLivros); 
    }


    @GetMapping("/recentes/{ano}")
    @Operation(summary = "Retorna quantidade de obras", description = "Retorna um inteiro que representa a quantidade de livros do acervo que são mais recentes que a determinado ano")
    @ApiResponses({
    @ApiResponse(responseCode ="200", description ="Total de livros por autor calculado com sucesso!"),
    @ApiResponse(responseCode ="204", description ="Nennhum livro não encontrado")
    })
    public ResponseEntity<Integer> livrosRecentes(@PathVariable int ano) {
        
        int livrosrecentes = relatorioService.livrosRecentes(ano);

        if(livrosrecentes == 0){
            return ResponseEntity.notFound().build(); 
        }

         return ResponseEntity.ok(livrosrecentes); 
    }
    
    @GetMapping("/media")
    @Operation(summary = "Retorna media de obras", description = "Retorna um double que representa média de livros do acervo por autores distintos")
    @ApiResponse(responseCode ="200", description ="Total de livros por autor calculado com sucesso!")
    public ResponseEntity<Double> mediaLivrosAutor() {
        
        double mediaLivros = relatorioService.mediaLivrosAutor();

         return ResponseEntity.ok(mediaLivros); 
    }
}
