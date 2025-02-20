package br.com.zupacademy.mercadolivre.domains;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Perguntas")
public class Pergunta implements Comparable <Pergunta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotNull
    @Valid
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @Valid
    @ManyToOne
    private Produto produto;

    private LocalDateTime criadoEm = LocalDateTime.now();

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(String titulo, Usuario usuario, Produto produto) {
        validarArgumentos(titulo, usuario, produto);
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
    }

    private void validarArgumentos(String titulo, Usuario usuario, Produto produto) {
        Assert.hasText(titulo, "O argumento 'titulo' precisa ser preenchido");
        Assert.notNull(usuario, "O argumento 'usuario' não pode ser null");
        Assert.notNull(produto, "O argumento 'produto' não pode ser null");
    }

    public String getTitulo() {
        return titulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public int compareTo(Pergunta o) {
        return this.titulo.compareTo(o.titulo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pergunta)) return false;
        Pergunta pergunta = (Pergunta) o;
        return getTitulo().equals(pergunta.getTitulo()) &&
                getUsuario().equals(pergunta.getUsuario()) && getProduto().equals(pergunta.getProduto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getUsuario(), getProduto());
    }
}