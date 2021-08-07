package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.annotations.UniqueValue;
import br.com.zupacademy.mercadolivre.domains.Categoria;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CategoriaRequest {

    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    @Positive
    private Long id_categoria_mae;

    public CategoriaRequest(@NotBlank String nome, @Positive Long id_categoria_mae) {
        this.nome = nome;
        this.id_categoria_mae = id_categoria_mae;
    }

    public Categoria toModel(EntityManager manager) {
        Categoria categoria = new Categoria(this.nome);

        if (id_categoria_mae != null) {
            Categoria categoriaMae = manager.find(Categoria.class, this.id_categoria_mae);
            categoria.setCategoriaMae(categoriaMae);
        }

        return categoria;
    }
}