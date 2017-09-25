package io.yorkecao.jwtauth.web;

import io.yorkecao.jwtauth.service.JwtAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yorke
 */
@RestController
public class JwtAuthController {

    private JwtAuthService jwtAuthService;

    public JwtAuthController(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @GetMapping("/read")
    public String read() {
        return this.jwtAuthService.read();
    }

    @GetMapping("/write")
    public String write() {
        return this.jwtAuthService.write();
    }
}
