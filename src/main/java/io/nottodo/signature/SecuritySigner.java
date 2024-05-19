package io.nottodo.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.nottodo.dto.MemberDto;


import java.util.Date;

public abstract class SecuritySigner {
    
    /**
     *
     * @param jwsSigner
     * @param user
     * @param jwk
     * @return
     */
    protected String getJwtTokenInternal(MACSigner jwsSigner, MemberDto user, JWK jwk) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder((JWSAlgorithm) jwk.getAlgorithm()).keyID(jwk.getKeyID()).build();
        
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject("user")
                .issuer("http://localhost:8080")
                .claim("id",user.getId())
                .claim("username",user.getUsername())
                .claim("memberName",user.getMemberName())
                .claim("memberLoginType",user.getLoginType())
                .claim("authority",user.getMemberRoles())
                .expirationTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000)) // 1일 (24시간) 유효 시간 설정
                .build();
        
        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        signedJWT.sign(jwsSigner);
        return signedJWT.serialize();
    }
   public abstract String getToken(MemberDto user , JWK jwk) throws JOSEException;
    
   
}
