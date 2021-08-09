package br.com.zupacademy.mercadolivre.domains;

import br.com.zupacademy.mercadolivre.domains.treatments.SenhaLimpa;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Usuarios")
public class Usuario implements UserDetails {

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

    @Deprecated
    public Usuario() {
    }

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

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", dataDeCadastro=" + dataDeCadastro +
                '}';
    }
}