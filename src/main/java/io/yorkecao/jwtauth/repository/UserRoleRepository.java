package io.yorkecao.jwtauth.repository;

import io.yorkecao.jwtauth.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Yorke
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    List<UserRole> findByUsername(String username);
}
