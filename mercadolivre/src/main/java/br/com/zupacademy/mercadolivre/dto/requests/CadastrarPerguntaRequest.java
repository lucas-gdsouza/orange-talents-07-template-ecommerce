package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.domains.Pergunta;
import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CadastrarPerguntaRequest {

    @NotBlank
    private String titulo;

    public void setTitulo(@NotBlank String titulo) {
        this.titulo = titulo;
    }

    public @NotNull Pergunta toModel(Usuario usuario, Produto produto) {
        return new Pergunta(this.titulo, usuario, produto);
    }
}
