package com.gts.backgts.configuration;

import com.gts.backgts.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publics
                        .requestMatchers(
                                "/",
                                "/uploads/**",
                                "/api/gts/auth/**",
                                "/api/gts/emails/forgot-password",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Gestion utilisateurs et rôles — ADMIN uniquement
                        .requestMatchers("/api/gts/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/gts/roles/**").hasRole("ADMIN")

                        // Finances — ADMIN, MANAGER, COMPTABLE
                        .requestMatchers("/api/gts/factures/**").hasAnyRole("ADMIN", "MANAGER", "COMPTABLE")
                        .requestMatchers("/api/gts/reglements/**").hasAnyRole("ADMIN", "MANAGER", "COMPTABLE")
                        // Reporting carburant — ouvert aussi à OPERATEUR (règle spécifique avant la règle générale)
                        .requestMatchers("/api/gts/reporting/carburant/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR", "COMPTABLE")
                        .requestMatchers("/api/gts/reporting/**").hasAnyRole("ADMIN", "MANAGER", "COMPTABLE")

                        // Flotte & opérations — ADMIN, MANAGER, OPERATEUR
                        .requestMatchers("/api/gts/maintenance/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")
                        .requestMatchers("/api/gts/engins/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")
                        .requestMatchers("/api/gts/pelles/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")
                        .requestMatchers("/api/gts/conducteurs/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR", "COMPTABLE")
                        .requestMatchers("/api/gts/missions/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR", "COMPTABLE")
                        .requestMatchers("/api/gts/assurances/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")
                        .requestMatchers("/api/gts/controles-vgp/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")
                        .requestMatchers("/api/gts/consommations-carburant/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")
                        .requestMatchers("/api/gts/pieces-rechange/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")
                        .requestMatchers("/api/gts/etats-des-lieux/**").hasAnyRole("ADMIN", "MANAGER", "OPERATEUR")

                        // Locations — ADMIN, MANAGER, COMPTABLE, OPERATEUR (lecture seule via @PreAuthorize)
                        .requestMatchers("/api/gts/locations/**").hasAnyRole("ADMIN", "MANAGER", "COMPTABLE", "OPERATEUR")

                        // Clients — ADMIN, MANAGER, COMPTABLE, OPERATEUR (lecture seule via @PreAuthorize)
                        .requestMatchers("/api/gts/clients/**").hasAnyRole("ADMIN", "MANAGER", "COMPTABLE", "OPERATEUR")

                        // Dashboard KPI — tout utilisateur authentifié
                        .requestMatchers("/api/gts/dashboard/**").authenticated()

                        // Référentiels (types, activités) — tout utilisateur authentifié
                        .requestMatchers("/api/gts/types-engins/**").authenticated()
                        .requestMatchers("/api/gts/activite-client/**").authenticated()

                        // Emails — ADMIN
                        .requestMatchers("/api/gts/emails/**").hasRole("ADMIN")

                        // Audit — ADMIN uniquement
                        .requestMatchers("/api/gts/audit/**").hasRole("ADMIN")

                        // Toute autre requête authentifiée
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, e) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\":\"Non authentifié : token manquant ou invalide\"}");
                        })
                        .accessDeniedHandler((request, response, e) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\":\"Accès refusé : droits insuffisants\"}");
                        })
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }
}
