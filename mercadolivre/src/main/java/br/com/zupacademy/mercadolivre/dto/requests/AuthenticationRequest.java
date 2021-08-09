package br.com.zupacademy.mercadolivre.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @Size(min = 6)
    private String senhaLimpa;

    public AuthenticationRequest(@Email @NotBlank String login, @NotBlank @Size(min = 6) @JsonProperty("senha") String senhaLimpa) {
        this.login = login;
        this.senhaLimpa = senhaLimpa;
    }

    public UsernamePasswordAuthenticationToken converter(){
        return new UsernamePasswordAuthenticationToken(this.login, this.senhaLimpa);
    }
}