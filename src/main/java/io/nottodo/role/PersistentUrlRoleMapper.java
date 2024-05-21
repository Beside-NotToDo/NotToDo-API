package io.nottodo.role;



import io.nottodo.entity.Resources;
import io.nottodo.repository.ResourcesRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PersistentUrlRoleMapper implements UrlRoleMapper{
    
    private LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    private final ResourcesRepository repository;
    
    public PersistentUrlRoleMapper(ResourcesRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Map<String, String> getRoleMappings() {
        urlRoleMappings.clear();
        List<Resources> allResources = repository.findAllResources();
        
        allResources
                .forEach(re -> {
                    re.getResourcesRoles().forEach(urlRole -> {
                        urlRoleMappings.put(re.getResourceName(), urlRole.getRole().getRoleName());
                    });
                });
        return urlRoleMappings;
    }
}
