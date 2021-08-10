package br.com.zupacademy.mercadolivre.domains;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @ManyToOne
    private Categoria categoriaMae;

    /**
     * @Deprecated - Para uso do Hibernate
     */
    @Deprecated
    public Categoria() {
    }

    /**
     * @param nome - Obrigatório
     */
    public Categoria(@NotBlank String nome) {
        validarArgumentos(nome);
        this.nome = nome;
    }

    /**
     * @param categoriaMae - Opcional
     */
    public void setCategoriaMae(Categoria categoriaMae) {
        this.categoriaMae = categoriaMae;
    }

    private void validarArgumentos(String nome) {
        Assert.hasText(nome, "Argumento 'nome' precisa ser preenchido.");
    }
}