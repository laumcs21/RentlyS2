package com.Rently.Configuration;

import com.Rently.Configuration.Security.JwtAuthenticationFilter;
import com.Rently.Persistence.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

/**
 * Configuración central de Spring Security.
 * Aquí se define la cadena de filtros de seguridad, el codificador de contraseñas
 * y otras configuraciones relacionadas con la seguridad.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    /**
     * Define el bean para la codificación de contraseñas.
     * Se utiliza BCrypt, que es el estándar recomendado para almacenar contraseñas de forma segura.
     * @return una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * Define qué rutas son públicas y cuáles requieren autenticación.
     * @param http el objeto HttpSecurity para configurar.
     * @return la cadena de filtros de seguridad construida.
     * @throws Exception si ocurre un error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean que define cómo se obtienen los detalles de los usuarios.
     * Utiliza el UsuarioRepository para buscar un usuario por su email.
     * @return una implementación de UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.Rently.Persistence.Entity.Usuario usuario = usuarioRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));

            return new User(
                    usuario.getEmail(),
                    usuario.getContrasena(),
                    Collections.singleton(new SimpleGrantedAuthority(usuario.getRol().name()))
            );
        };
    }

    /**
     * Bean que define el proveedor de autenticación.
     * Utiliza el UserDetailsService y el PasswordEncoder para validar las credenciales.
     * @return una instancia de AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean que expone el AuthenticationManager de Spring Security.
     * Es necesario para procesar las solicitudes de autenticación.
     * @param config la configuración de autenticación.
     * @return el AuthenticationManager.
     * @throws Exception si ocurre un error.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
