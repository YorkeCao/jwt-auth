package io.yorkecao.jwtauth;

import io.yorkecao.jwtauth.config.JwtAuthConfing;
import io.yorkecao.jwtauth.entity.RolePermission;
import io.yorkecao.jwtauth.entity.User;
import io.yorkecao.jwtauth.entity.UserRole;
import io.yorkecao.jwtauth.repository.RolePermissionRepository;
import io.yorkecao.jwtauth.repository.UserRepository;
import io.yorkecao.jwtauth.repository.UserRoleRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class JwtAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthApplication.class, args);
	}

	@GetMapping("/login")
	public void login(String username, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);
	}

	@GetMapping("/logout")
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository, UserRoleRepository userRoleRepository, RolePermissionRepository rolePermissionRepository) {
		return (args) -> {
			userRepository.save(new User("zhangsan", "zhangsan"));
			userRepository.save(new User("lisi", "lisi"));

			userRoleRepository.save(new UserRole("zhangsan", "admin"));
			userRoleRepository.save(new UserRole("lisi", "guest"));

			rolePermissionRepository.save(new RolePermission("admin", "read"));
			rolePermissionRepository.save(new RolePermission("admin", "write"));
			rolePermissionRepository.save(new RolePermission("guest", "read"));
		};
	}
}
