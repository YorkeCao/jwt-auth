package io.yorkecao.jwtauth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
}
