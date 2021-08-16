package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.domains.Compra;
import br.com.zupacademy.mercadolivre.dto.requests.impl.RetornoGatewayPagamento;
import br.com.zupacademy.mercadolivre.dto.requests.RetornoPagSeguroRequest;
import br.com.zupacademy.mercadolivre.dto.requests.RetornoPayPalRequest;
import br.com.zupacademy.mercadolivre.services.EventosNovaCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class GatewayController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private EventosNovaCompra eventosNovaCompra;

    @PostMapping(value = "/retorno-pagseguro/{idCompra}")
    @Transactional
    public void processamentoPagSeguro(@PathVariable("idCompra") Long idCompra,
                                       @Valid RetornoPagSeguroRequest request) {
        processamento(idCompra, request);
    }

    @PostMapping(value = "/retorno-paypal/{idCompra}")
    @Transactional
    public void processamentoPayPal(@PathVariable("idCompra") Long idCompra,
                                    @Valid RetornoPayPalRequest request) {
        processamento(idCompra, request);
    }

    /**
     * Independente da Plataforma de Pagamento, existirá a execução do passo-a-passo.
     * Atenção ao Polimorfismo! Independente da Plataforma de Pagamento, utilizamos o mesmo retorno de toModel(),
     * então criamos uma interface de forma a generalizar o recebimento desse método.
     */
    private void processamento(Long idCompra, RetornoGatewayPagamento retornoGatewayPagamento) {
        Compra compra = manager.find(Compra.class, idCompra);
        compra.adicionaTransacao(retornoGatewayPagamento);
        manager.merge(compra);
        eventosNovaCompra.processa(compra);
    }
}