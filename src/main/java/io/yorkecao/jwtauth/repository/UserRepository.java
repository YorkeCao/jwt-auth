package io.yorkecao.jwtauth.repository;

import io.yorkecao.jwtauth.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Yorke
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
