package br.com.zupacademy.mercadolivre.dto.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NotaFiscalRequest {

    @NotNull
    @Positive
    private Long idCompra;
    @NotNull
    @Positive
    private Long idComprador;

    public NotaFiscalRequest(Long idCompra, Long idComprador) {
        super();
        this.idCompra = idCompra;
        this.idComprador = idComprador;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdComprador() {
        return idComprador;
    }

    @Override
    public String toString() {
        return "NotaFiscalRequest [idCompra=" + this.idCompra + ", idComprador="
                + this.idComprador + "]";
    }

}