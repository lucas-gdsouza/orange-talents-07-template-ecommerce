package br.com.zupacademy.mercadolivre.services;

import br.com.zupacademy.mercadolivre.domains.Compra;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotaFiscalService implements EventoCompraSucesso {

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(), "Compra não realizada com sucesso.");

        /*Template Fake para disparar requisição*/
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(),
                "idComprador", compra.getComprador().getId());

        restTemplate.postForEntity("http://localhost:8080/api/v1/notas-fiscais",
                request, String.class);
    }
}