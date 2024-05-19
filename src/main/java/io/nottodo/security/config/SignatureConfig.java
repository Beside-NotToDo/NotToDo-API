package io.nottodo.security.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;

import io.nottodo.signature.MacSecuritySigner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignatureConfig {
    
    @Bean
    public MacSecuritySigner macSecuritySigner() {
        return new MacSecuritySigner();
    }
    
    
    @Bean
    public OctetSequenceKey OctetSequenceKey() throws JOSEException {
        return new OctetSequenceKeyGenerator(256)
                .keyID("macKey")
                .algorithm(JWSAlgorithm.HS256)
                .generate();
    }
}
