package io.nottodo.service;

import java.util.Map;

public interface DynamicAuthorizationService {
     Map<String, String> getUrlRoleMappings();
}
