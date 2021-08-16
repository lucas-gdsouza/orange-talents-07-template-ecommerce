package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.dto.requests.NotaFiscalRequest;
import br.com.zupacademy.mercadolivre.dto.requests.RankingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class SistemaFakeController {

    @PostMapping(value = "/notas-fiscais")
    public void criaNota(@Valid @RequestBody NotaFiscalRequest request) throws InterruptedException {
        System.out.println("Nota: " + request);
        Thread.sleep(150);
    }

    @PostMapping(value = "/ranking")
    public void ranking(@Valid @RequestBody RankingRequest request) throws InterruptedException {
        System.out.println("Ranking: " + request);
        Thread.sleep(150);
    }
}