package io.nottodo.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KaKaoLoginRequest {
    @JsonProperty("accessToken")
    private String accessToken;
    
    @JsonProperty("tokenType")
    private String tokenType;
    
    @JsonProperty("refreshToken")
    private String refreshToken;
    
    @JsonProperty("idToken")
    private String idToken;
    
    @JsonProperty("expiresIn")
    private int expiresIn;
    
    @JsonProperty("scope")
    private String scope;
    
    @JsonProperty("refreshTokenExpiresIn")
    private int refreshTokenExpiresIn;
    
   
}
