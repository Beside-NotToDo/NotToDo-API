package io.nottodo.service.impl;



import io.nottodo.role.UrlRoleMapper;
import io.nottodo.service.DynamicAuthorizationService;

import java.util.Map;

public class DynamicAuthorizationServiceImpl implements DynamicAuthorizationService {
    
    private final UrlRoleMapper delegate;
    
    public DynamicAuthorizationServiceImpl(UrlRoleMapper delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public Map<String, String> getUrlRoleMappings() {
        return delegate.getRoleMappings();
    }
}
