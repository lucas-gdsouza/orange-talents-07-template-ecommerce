package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.components.EmailFake;
import br.com.zupacademy.mercadolivre.components.UploaderFake;
import br.com.zupacademy.mercadolivre.domains.Opiniao;
import br.com.zupacademy.mercadolivre.domains.Pergunta;
import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;
import br.com.zupacademy.mercadolivre.dto.requests.CadastroOpiniaoRequest;
import br.com.zupacademy.mercadolivre.dto.requests.CadastroPerguntaRequest;
import br.com.zupacademy.mercadolivre.dto.requests.CadastroProdutoRequest;
import br.com.zupacademy.mercadolivre.dto.requests.NovasImagensRequest;
import br.com.zupacademy.mercadolivre.dto.responses.DetalhesDoProdutoResponse;
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
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UploaderFake uploaderFake;

    @Autowired
    private EmailFake emailFake;

    @GetMapping(value = "/{id}")
    public DetalhesDoProdutoResponse capturarInformacoes(@PathVariable("id") Long id) {
        Produto produto = manager.find(Produto.class, id);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return new DetalhesDoProdutoResponse(produto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity salvarProduto(@RequestBody @Valid CadastroProdutoRequest request,
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
    public ResponseEntity salvarOpinioes(@PathVariable("id") Long id, @RequestBody @Valid CadastroOpiniaoRequest request,
                                         @AuthenticationPrincipal Optional usuarioPrincipal) {

        if (usuarioPrincipal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Usuario usuario = (Usuario) usuarioPrincipal.get();
        Produto produto = manager.find(Produto.class, id);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Opiniao opiniao = request.toModel(usuario, produto);
        manager.persist(opiniao);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/perguntas")
    @Transactional
    public ResponseEntity salvarPerguntas(@PathVariable("id") Long id,
                                          @RequestBody @Valid CadastroPerguntaRequest request,
                                          @AuthenticationPrincipal Optional usuarioPrincipal) {

        if (usuarioPrincipal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Usuario usuario = (Usuario) usuarioPrincipal.get();
        Produto produto = manager.find(Produto.class, id);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Pergunta pergunta = request.toModel(usuario, produto);
        manager.persist(pergunta);

        emailFake.enviarEmail(pergunta);

        return ResponseEntity.ok().build();
    }
}