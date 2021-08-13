package br.com.zupacademy.mercadolivre.domains;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "Opinioes")
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @ManyToOne
    @NotNull
    @Valid
    private Usuario usuario;

    @ManyToOne
    @NotNull
    @Valid
    private Produto produto;

    @Deprecated
    public Opiniao() {
    }

    public Opiniao(@Size(min = 1, max = 5) Integer nota, @NotBlank String titulo, @NotBlank String descricao,
                   @NotNull Usuario usuario, @NotNull Produto produto) {

        validarArgumentos(nota, titulo, descricao, usuario, produto);
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.produto = produto;
    }

    private void validarArgumentos(Integer nota, String titulo, String descricao,
                                   Usuario usuario, Produto produto) {
        Assert.isTrue(nota >= 1 || nota <= 5, "O argumento 'nota' precisa ser preenchido entre 1 a 5.");
        Assert.hasText(titulo, "O argumento 'titulo' precisa ser preenchido");
        Assert.hasText(descricao, "O argumento 'descricao' precisa ser preenchido");
        Assert.notNull(usuario, "O argumento 'usuario' não pode ser null");
        Assert.notNull(produto, "O argumento 'produto' não pode ser null");
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opiniao)) return false;
        Opiniao opiniao = (Opiniao) o;
        return titulo.equals(opiniao.titulo) && usuario.equals(opiniao.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, usuario);
    }
}
