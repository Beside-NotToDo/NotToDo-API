package io.nottodo.security.auth;



import io.nottodo.service.RoleHierarchyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public RoleHierarchyImpl roleHierarchy(RoleHierarchyService roleHierarchyService) {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = roleHierarchyService.findAllHierarchy();
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
}
