package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.domains.Compra;
import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;
import br.com.zupacademy.mercadolivre.dto.requests.NovaCompraRequest;

import br.com.zupacademy.mercadolivre.dto.responses.CompraResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/compras/")
public class CompraController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity gerarCompra(@RequestBody @Valid NovaCompraRequest request,
                                      @AuthenticationPrincipal Optional usuarioComprador,
                                      UriComponentsBuilder uriComponentsBuilder) {

        Usuario usuario = (Usuario) usuarioComprador.get();
        Produto produto = request.avaliarEstoque(manager);

        Compra compra = request.toModel(usuario, produto);
        manager.persist(compra);

        String controllerMappingPath = this.getClass().getAnnotation(RequestMapping.class).value()[0];

        CompraResponse response = new CompraResponse(compra, controllerMappingPath);
        String enderecoFinal = response.getFinalURI();
        URI uri = uriComponentsBuilder.path(enderecoFinal).buildAndExpand(compra.getId()).toUri();

        return ResponseEntity.status(HttpStatus.FOUND).body(uri);
    }
}