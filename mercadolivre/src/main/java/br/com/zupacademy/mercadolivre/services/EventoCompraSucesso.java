package br.com.zupacademy.mercadolivre.services;

import br.com.zupacademy.mercadolivre.domains.Compra;

/*
*  Interface será aplicada em Ranking e NF para que o serviço consiga disparar todos os eventos necessários.
* */
public interface EventoCompraSucesso {
    void processa(Compra compra);
}
