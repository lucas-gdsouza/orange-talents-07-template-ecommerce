package br.com.zupacademy.mercadolivre.domains.treatments;

import io.jsonwebtoken.lang.Assert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SenhaLimpa {

    private String senha;

    public SenhaLimpa(@NotBlank @Size(min = 6) String senha) {
        validarArgumentos(senha);
        this.senha = senha;
    }

    private void validarArgumentos(String senha) {
        Assert.hasText(senha, "Argumento 'senha' precisa ser preenchido.");
    }

    public @NotBlank @Size(min = 6) String hash() {
        return new BCryptPasswordEncoder().encode(this.senha);
    }
}