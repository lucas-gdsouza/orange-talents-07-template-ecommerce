package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.annotations.UniqueValue;
import br.com.zupacademy.mercadolivre.domains.SenhaLimpa;
import br.com.zupacademy.mercadolivre.domains.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @Email
    @NotBlank
    @UniqueValue(domainClass = Usuario.class, fieldName = "login")
    private String login;

    /**
     * @param senhaLimpa - Texto puro, sem criptografia, recebido via Request.
     */

    @JsonProperty("senha")
    @NotBlank
    @Size(min = 6)
    private String senhaLimpa;

    public UsuarioRequest(@Email @NotBlank String login, @NotBlank @Size(min = 6) @JsonProperty("senha") String senhaLimpa) {
        this.login = login;
        this.senhaLimpa = senhaLimpa;
    }

    public Usuario toModel() {
        return new Usuario(this.login, new SenhaLimpa(this.senhaLimpa));
    }
}