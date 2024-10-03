package com.eqp3e1.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/empresa/registro", "/empresa/registrar", "aluno/registro",
                                "/aluno/registrar", "/oferta/todas", "/oferta/candidatos/**", "/empresa/todas")
                        .permitAll() // Permite acesso público aos recursos estáticos
                        .requestMatchers("/oferta/candidatar", "/aluno/minha-ficha").hasRole("USER")
                        .requestMatchers("/oferta/cadastrarEstagio", "/oferta/cancelar").hasRole("ADMIN")
                        .requestMatchers("oferta/salvar", "/empresa/editar/**").hasAnyRole("EMPRESA", "ADMIN")// Apenas
                                                                                                              // admins
                                                                                                              // podem
                                                                                                              // acessar
                                                                                                              // ofertas
                        .anyRequest().authenticated()) // Qualquer outra rota requer autenticação
                .formLogin((form) -> form
                        .loginPage("/auth/login") // Página de login personalizada
                        .defaultSuccessUrl("/", true) // Redireciona para /home após o login bem-sucedido
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Criação de usuários básicos, armazenados no banco de dados
        UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("password1")).roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .build();

        // Criação e persistência dos usuários no banco de dados
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (!users.userExists(user1.getUsername())) {
            users.createUser(user1);
            users.createUser(admin);
        }
        return users;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
