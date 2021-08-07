package br.com.zupacademy.mercadolivre.controllers;

import br.com.zupacademy.mercadolivre.domains.Categoria;
import br.com.zupacademy.mercadolivre.dto.requests.CategoriaRequest;
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
@RequestMapping("api/v1/categorias")
public class CategoriaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity salvar(@RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = request.toModel(manager);
        manager.persist(categoria);

        return ResponseEntity.ok().build();
    }
}