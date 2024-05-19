package io.nottodo.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KaKaoLoginRequest {
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("token_type")
    private String tokenType;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("id_token")
    private String idToken;
    
    @JsonProperty("expires_in")
    private int expiresIn;
    
    @JsonProperty("scope")
    private String scope;
    
    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;
}
