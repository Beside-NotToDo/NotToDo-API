package io.nottodo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleLoginRequest {
    
    @JsonProperty("authorizationCode")
    private String authorizationCode;
    
    @JsonProperty("identityToken")
    private String identityToken;
    
    @JsonProperty("name")
    private String name;
}
