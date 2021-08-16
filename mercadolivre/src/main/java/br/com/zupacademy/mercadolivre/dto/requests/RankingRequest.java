package br.com.zupacademy.mercadolivre.dto.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RankingRequest {
    @NotNull
    @Positive
    private Long idCompra;
    @NotNull
    @Positive
    private Long idDonoProduto;

    public RankingRequest(Long idCompra, Long idDonoProduto) {
        super();
        this.idCompra = idCompra;
        this.idDonoProduto = idDonoProduto;
    }

    @Override
    public String toString() {
        return "RankingRequest [idCompra=" + this.idCompra + ", idComprador="
                + this.idDonoProduto + "]";
    }
}
