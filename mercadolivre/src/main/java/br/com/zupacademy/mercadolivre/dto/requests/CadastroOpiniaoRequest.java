package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.domains.Opiniao;
import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;

import javax.validation.constraints.*;

public class CadastroOpiniaoRequest {

    @Min(1)
    @Max(5)
    @NotNull
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String descricao;


    public CadastroOpiniaoRequest(@Size(min = 1, max = 5) Integer nota, @NotBlank String titulo,
                                  @NotBlank String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public @NotNull Opiniao toModel(Usuario usuario, Produto produto) {
        return new Opiniao(this.nota, this.titulo, this.descricao, usuario, produto);
    }
}
