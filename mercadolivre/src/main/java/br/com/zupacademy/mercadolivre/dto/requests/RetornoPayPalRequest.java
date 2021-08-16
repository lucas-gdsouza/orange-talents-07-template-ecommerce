package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.annotations.UniqueValue;
import br.com.zupacademy.mercadolivre.domains.Compra;
import br.com.zupacademy.mercadolivre.domains.Transacao;
import br.com.zupacademy.mercadolivre.domains.enums.StatusTransacao;
import br.com.zupacademy.mercadolivre.dto.requests.impl.RetornoGatewayPagamento;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class RetornoPayPalRequest implements RetornoGatewayPagamento {

    @Min(0)
    @Max(1)
    private int status;

    @NotBlank
    @UniqueValue(domainClass = Transacao.class, fieldName = "idTransacaoGateway")
    private String idTransacao;

    public RetornoPayPalRequest(@Min(0) @Max(1) int status,
                                @NotBlank String idTransacao) {
        this.status = status;
        this.idTransacao = idTransacao;
    }

    public Transacao toTransacao(Compra compra) {
        StatusTransacao statusCalculado = this.status == 0 ? StatusTransacao.ERRO
                : StatusTransacao.SUCESSO;

        return new Transacao(statusCalculado, idTransacao, compra);
    }
}