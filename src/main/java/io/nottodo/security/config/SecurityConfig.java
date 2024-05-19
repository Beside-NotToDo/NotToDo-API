package io.nottodo.security.config;


import io.nottodo.security.filter.KaKaoAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final AuthenticationProvider restAuthenticationProvider;
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.authenticationProvider(restAuthenticationProvider);
        AuthenticationManager manager = managerBuilder.build();
        
        http.authorizeHttpRequests(auth -> auth.
                        anyRequest().permitAll())
                .addFilterBefore(kaKaoAuthenticationFilter(manager), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(manager)
        
        ;
        return http.build();
    }
    
    private KaKaoAuthenticationFilter kaKaoAuthenticationFilter(AuthenticationManager manager) {
        KaKaoAuthenticationFilter kaKaoAuthenticationFilter = new KaKaoAuthenticationFilter();
        kaKaoAuthenticationFilter.setAuthenticationManager(manager);
        return kaKaoAuthenticationFilter;
    }
}
