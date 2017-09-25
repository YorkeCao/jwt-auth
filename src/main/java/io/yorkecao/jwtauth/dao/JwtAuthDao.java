package io.yorkecao.jwtauth.dao;

import java.util.Set;

/**
 * @author Yorke
 */
public interface JwtAuthDao {
    String getPasswordByUsername(String username);
    Set<String> getRolesByUsername(String username);
    Set<String> getPermissionsByRole(String role);
}
