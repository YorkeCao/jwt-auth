package io.yorkecao.jwtauth.repository;

import io.yorkecao.jwtauth.entity.RolePermission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Yorke
 */
public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {
    List<RolePermission> findByRole(String role);
}
