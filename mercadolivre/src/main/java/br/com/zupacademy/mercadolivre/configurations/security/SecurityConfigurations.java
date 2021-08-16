package br.com.zupacademy.mercadolivre.configurations.security;

import br.com.zupacademy.mercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.mercadolivre.services.AuthenticationService;
import br.com.zupacademy.mercadolivre.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * Responsável por configurações de autenticação, ex: Login..
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Responsável por configurações de autorização de acesso.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/notas-fiscais").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/ranking").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Responsável por configurações de recursos estáticos, ex: JS, CSS, Imagens, etc.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
}