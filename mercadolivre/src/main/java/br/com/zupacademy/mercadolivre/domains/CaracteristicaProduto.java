package br.com.zupacademy.mercadolivre.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
public class CaracteristicaProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @ManyToOne
    @NotNull
    @Valid
    private Produto produto;

    @Deprecated
    public CaracteristicaProduto() {

    }

    public CaracteristicaProduto(@NotBlank String nome,
                                 @NotBlank String descricao, @NotNull @Valid Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaracteristicaProduto)) return false;
        CaracteristicaProduto that = (CaracteristicaProduto) o;
        return getNome().equals(that.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }
}