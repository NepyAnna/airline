package com.sheoanna.airline.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sheoanna.airline.security.JpaUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${api-endpoint}")
    String endpoint;

    private JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfiguration(JpaUserDetailsService userDetailsService) {
        this.jpaUserDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .logout(out -> out
                        .logoutUrl(endpoint + "/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, endpoint + "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, endpoint + "/login").permitAll()
                        .requestMatchers(endpoint + "/private/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, endpoint + "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, endpoint + "/bookings").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, endpoint + "/profiles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, endpoint + "/bookings/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, endpoint + "/profiles/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, endpoint + "/bookings").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/bookings/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/profiles/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, endpoint + "/profiles").hasRole("USER")
                        .anyRequest().authenticated())
                .userDetailsService(jpaUserDetailsService)
                .httpBasic(withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        http.headers(header -> header.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}