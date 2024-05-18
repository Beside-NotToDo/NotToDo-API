package io.nottodo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/oauth2")
public class KaKaoController {
    
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;
    
    @PostMapping("/callback")
    public ResponseEntity<String> kakaoLogin(@RequestBody Map<String, String> body) {
        String accessToken = body.get("accessToken");
        
        // 액세스 토큰을 사용하여 카카오 서버에 사용자 정보 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    userInfoUri, HttpMethod.GET, entity, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                // 사용자 정보 처리 로직
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch user info");
        }
    }
    @GetMapping("/code/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
    }
}
