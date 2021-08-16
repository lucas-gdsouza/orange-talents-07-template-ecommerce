package br.com.zupacademy.mercadolivre.dto.requests.impl;

import br.com.zupacademy.mercadolivre.domains.Compra;
import br.com.zupacademy.mercadolivre.domains.Transacao;

public interface RetornoGatewayPagamento {
    Transacao toTransacao(Compra compra);
}
