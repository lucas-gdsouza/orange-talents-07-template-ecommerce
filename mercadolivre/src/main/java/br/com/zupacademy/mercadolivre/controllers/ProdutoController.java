package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;
import br.com.zupacademy.mercadolivre.dto.requests.CadastroProdutoRequest;
import br.com.zupacademy.mercadolivre.validator.ImpedirCaracteristicaComNomeIgualValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity salvar(@RequestBody @Valid CadastroProdutoRequest request,
                                 @AuthenticationPrincipal Optional usuarioPrincipal) {

        Usuario usuario = (Usuario) usuarioPrincipal.get();

        Produto produto = request.toModel(manager, usuario);
        manager.persist(produto);

        return ResponseEntity.ok().build();
    }
}