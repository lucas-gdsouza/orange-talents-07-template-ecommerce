package br.com.zupacademy.mercadolivre.repositories;

import br.com.zupacademy.mercadolivre.domains.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}