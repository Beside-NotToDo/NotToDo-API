package io.nottodo.security.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class KakaoTokenValidator {
    
    @Value("${kakao.token.info.url}")
    private String kakaoTokenInfoUrl;
    @Value("${kakao.token.user.url}")
    private String kakaoTokenUserUrl;
    
    
    public boolean isValidKakaoToken(String kakaoAccessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + kakaoAccessToken);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(kakaoTokenInfoUrl, HttpMethod.GET, entity, String.class);
            
            // 응답 코드가 200인 경우 토큰이 유효함
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            // 예외가 발생하면 토큰이 유효하지 않음
            return false;
        }
    }
    
    public JsonNode getUserInfo(String kakaoAccessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + kakaoAccessToken);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(kakaoTokenUserUrl, HttpMethod.GET, entity, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readTree(response.getBody());
            }
            return null;
        } catch (Exception e) {
            log.info("getUserInfo 에러 = {}", e.getMessage());
            return null;
        }
    }
}
