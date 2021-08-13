package br.com.zupacademy.mercadolivre.dto.responses;

import br.com.zupacademy.mercadolivre.domains.CaracteristicaProduto;

import java.util.StringJoiner;

public class DetalhesCaracteristicasResponse {

    public String nome;
    public String descricao;

    public DetalhesCaracteristicasResponse(CaracteristicaProduto caracteristicaProduto) {
        this.nome = caracteristicaProduto.getNome();
        this.descricao = caracteristicaProduto.getDescricao();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DetalhesCaracteristicasResponse.class.getSimpleName() + "[", "]")
                .add("nome='" + nome + "'")
                .add("descricao='" + descricao + "'")
                .toString();
    }
}