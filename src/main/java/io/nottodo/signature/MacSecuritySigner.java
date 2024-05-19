package io.nottodo.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import io.nottodo.dto.MemberDto;


/**
 * 서명 클래스
 */

public class MacSecuritySigner extends SecuritySigner {
    @Override
    public String getToken(MemberDto user, JWK jwk) throws JOSEException {
        
        MACSigner jwsSigner = new MACSigner(((OctetSequenceKey)jwk).toSecretKey()); // 대칭키
    
        return super.getJwtTokenInternal(jwsSigner,user,jwk);
    }
}
