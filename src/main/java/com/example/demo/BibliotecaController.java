package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BibliotecaController {

    @GetMapping
    public String mensagemDeBemVindo() {
        return "Bem vindo a biblioteca central!";
    }
}
