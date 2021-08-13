package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.domains.CaracteristicaProduto;
import br.com.zupacademy.mercadolivre.domains.Produto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CadastroCaracteristicaRequest {
    @NotBlank
    public String nome;

    @NotBlank
    public String descricao;

    public CadastroCaracteristicaRequest(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public CaracteristicaProduto toModel(@NotNull @Valid Produto produto) {
        return new CaracteristicaProduto(this.nome, this.descricao, produto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CadastroCaracteristicaRequest)) return false;
        CadastroCaracteristicaRequest that = (CadastroCaracteristicaRequest) o;
        return getNome().equals(that.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }
}