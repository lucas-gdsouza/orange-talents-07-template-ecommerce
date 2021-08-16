package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.annotations.ExistsId;
import br.com.zupacademy.mercadolivre.annotations.UniqueValue;
import br.com.zupacademy.mercadolivre.domains.Compra;
import br.com.zupacademy.mercadolivre.domains.Transacao;
import br.com.zupacademy.mercadolivre.domains.enums.StatusRetornoPagSeguro;
import br.com.zupacademy.mercadolivre.dto.requests.impl.RetornoGatewayPagamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RetornoPagSeguroRequest implements RetornoGatewayPagamento {

    @NotBlank
    @UniqueValue(domainClass = Transacao.class, fieldName = "idTransacaoGateway")
    private String idTransacao;

    @NotNull
    private StatusRetornoPagSeguro status;

    public RetornoPagSeguroRequest(@NotBlank String idTransacao, @NotNull StatusRetornoPagSeguro status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    public Transacao toTransacao(Compra compra) {
        return new Transacao(status.normaliza(), idTransacao, compra);
    }
}