package br.com.zupacademy.mercadolivre.components.impl;

import br.com.zupacademy.mercadolivre.domains.Pergunta;

import javax.validation.constraints.NotNull;

public interface Send {

    void enviarEmail(@NotNull Pergunta pergunta);

}
