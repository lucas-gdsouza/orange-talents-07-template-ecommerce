package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.components.UploaderFake;
import br.com.zupacademy.mercadolivre.domains.Opiniao;
import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;
import br.com.zupacademy.mercadolivre.dto.requests.CadastrarOpiniaoRequest;
import br.com.zupacademy.mercadolivre.dto.requests.CadastroProdutoRequest;
import br.com.zupacademy.mercadolivre.dto.requests.NovasImagensRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UploaderFake uploaderFake;

    @PostMapping
    @Transactional
    public ResponseEntity salvar(@RequestBody @Valid CadastroProdutoRequest request,
                                 @AuthenticationPrincipal Optional usuarioPrincipal) {

        Usuario usuario = (Usuario) usuarioPrincipal.get();

        Produto produto = request.toModel(manager, usuario);
        manager.persist(produto);

        return ResponseEntity.ok().build();
    }

    /**
     * 1) Enviar Imagens para o Local de Armazenamento;
     * 2) Capturar os links;
     * 3) Associar os links das imagens com o Produto;
     * 4) Carregar produto;
     * 5) Salvar.
     */

    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public ResponseEntity adicionarImagemAoProduto(@PathVariable("id") Long id, @Valid NovasImagensRequest request,
                                                   @AuthenticationPrincipal Optional usuarioPrincipal) {

        Usuario usuarioLogado = (Usuario) usuarioPrincipal.get();
        Produto produto = manager.find(Produto.class, id);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (usuarioPrincipal.isEmpty() || !produto.verificacaoOwnership(usuarioLogado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        /*Fake*/
        Set<String> links = uploaderFake.envia(request.getImagens());
        produto.associaImagens(links);

        manager.merge(produto);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/opinioes")
    @Transactional
    public ResponseEntity salvar(@PathVariable("id") Long id, @RequestBody @Valid CadastrarOpiniaoRequest request,
                                 @AuthenticationPrincipal Optional usuarioPrincipal) {

        if (usuarioPrincipal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Usuario usuario = (Usuario) usuarioPrincipal.get();
        Produto produto = manager.find(Produto.class, id);

        if(produto == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Opiniao opiniao = request.toModel(usuario, produto);
        manager.persist(opiniao);

        return ResponseEntity.ok().build();
    }
}