package io.nottodo.security.validation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.math.BigInteger;
import java.security.Key;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.KeyFactory;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppleTokenValidator {
    
    private static final String APPLE_KEYS_URL = "https://appleid.apple.com/auth/keys";
    private Map<String, PublicKey> publicKeys;

    @PostConstruct
    public void init() {
        publicKeys = fetchApplePublicKeys();
    }
    
    public boolean isValidAppleToken(String idToken) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKeyResolver(new AppleKeyResolver(publicKeys))
                    .build()
                    .parseClaimsJws(idToken);
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Claims getUserInfoFromToken(String idToken) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKeyResolver(new AppleKeyResolver(publicKeys))
                    .build()
                    .parseClaimsJws(idToken);
            
            return jws.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract user info from Apple ID Token", e);
        }
    }
    
    private Map<String, PublicKey> fetchApplePublicKeys() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(APPLE_KEYS_URL, String.class);
        
        Map<String, PublicKey> keys = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode keysNode = root.get("keys");
            
            for (JsonNode keyNode : keysNode) {
                String kid = keyNode.get("kid").asText();
                String n = keyNode.get("n").asText();
                String e = keyNode.get("e").asText();
                
                byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
                byte[] exponentBytes = Base64.getUrlDecoder().decode(e);
                
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(1, modulusBytes), new BigInteger(1, exponentBytes));
                PublicKey publicKey = keyFactory.generatePublic(keySpec);
                
                keys.put(kid, publicKey);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Apple public keys", e);
        }
        
        return keys;
    }
    
    private static class AppleKeyResolver extends SigningKeyResolverAdapter {
        private final Map<String, PublicKey> publicKeys;
        
        public AppleKeyResolver(Map<String, PublicKey> publicKeys) {
            this.publicKeys = publicKeys;
        }
        
        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            return publicKeys.get(header.getKeyId());
        }
    }
}