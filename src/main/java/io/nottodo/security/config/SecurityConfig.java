package io.nottodo.security.config;


import com.nimbusds.jose.jwk.OctetSequenceKey;
import io.nottodo.security.filter.AppleAuthenticationFilter;
import io.nottodo.security.filter.KaKaoAuthenticationFilter;
import io.nottodo.signature.MacSecuritySigner;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final AuthenticationProvider kakaoAuthenticationProvider;
    private final MacSecuritySigner macSecuritySigner;
    private final OctetSequenceKey octetSequenceKey;
    private final JwtDecoder hS256JwtDecoder;
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.authenticationProvider(kakaoAuthenticationProvider);
        AuthenticationManager manager = managerBuilder.build();
        
        http.authorizeHttpRequests(auth -> auth.
                        anyRequest().authenticated())
                .addFilterBefore(kaKaoAuthenticationFilter(manager,macSecuritySigner,octetSequenceKey), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(appleAuthenticationFilter(manager,macSecuritySigner,octetSequenceKey), UsernamePasswordAuthenticationFilter.class)
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.decoder(hS256JwtDecoder)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(manager)
        
        ;
        return http.build();
    }
    
    private KaKaoAuthenticationFilter kaKaoAuthenticationFilter(AuthenticationManager manager, MacSecuritySigner macSecuritySigner, OctetSequenceKey octetSequenceKey) {
        KaKaoAuthenticationFilter kaKaoAuthenticationFilter = new KaKaoAuthenticationFilter(macSecuritySigner,octetSequenceKey);
        kaKaoAuthenticationFilter.setAuthenticationManager(manager);
        return kaKaoAuthenticationFilter;
    }
    
    private AppleAuthenticationFilter appleAuthenticationFilter(AuthenticationManager manager, MacSecuritySigner macSecuritySigner, OctetSequenceKey octetSequenceKey) {
        AppleAuthenticationFilter appleAuthenticationFilter = new AppleAuthenticationFilter(macSecuritySigner, octetSequenceKey);
        appleAuthenticationFilter.setAuthenticationManager(manager);
        return appleAuthenticationFilter;
    }
}
