package br.com.zupacademy.mercadolivre.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthenticationRequest {
    @Email
    @NotBlank
    private String login;

    /**
     * @param senhaLimpa - Texto puro, sem criptografia, recebido via Request.
     */

    @JsonProperty("senha")
    @NotBlank
    private String senhaLimpa;

    public AuthenticationRequest(@Email @NotBlank String login,
                                 @NotBlank @JsonProperty("senha") String senhaLimpa) {
        this.login = login;
        this.senhaLimpa = senhaLimpa;
    }

    public @NotNull UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(this.login, this.senhaLimpa);
    }
}