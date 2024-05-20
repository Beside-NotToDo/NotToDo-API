package io.nottodo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleLoginRequest {
    
    @JsonProperty("idToken")
    private String idToken;
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("user")
    private String user;
    
    @JsonProperty("state")
    private String state;
    
    @JsonProperty("expiresIn")
    private int expiresIn;
}
