package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.domains.Usuario;
import br.com.zupacademy.mercadolivre.dto.requests.CadastroUsuarioRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity salvar(@RequestBody @Valid CadastroUsuarioRequest request) {
        Usuario usuario = request.toModel();
        manager.persist(usuario);

        return ResponseEntity.ok().build();
    }
}