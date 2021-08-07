package br.com.zupacademy.mercadolivre.domains;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 6)
    private String senha;

    @NotNull
    @PastOrPresent
    private LocalDateTime dataDeCadastro = LocalDateTime.now();

    /**
     * @param senhaLimpa - String em texto puro, sem criptografia.
     */

    public Usuario(@Email @NotBlank String login, @NotNull SenhaLimpa senhaLimpa) {
        validarArgumentos(login, senhaLimpa);
        this.login = login;
        this.senha = senhaLimpa.hash();
    }

    private void validarArgumentos(String login, SenhaLimpa senhaLimpa) {
        if (login == null || login.trim().equals("")) {
            throw new IllegalArgumentException("Argumento 'login' não pode ser null ou vazio");
        }

        if (senhaLimpa == null) {
            throw new IllegalArgumentException("Argumento 'senha' não pode ser null");
        }
    }
}