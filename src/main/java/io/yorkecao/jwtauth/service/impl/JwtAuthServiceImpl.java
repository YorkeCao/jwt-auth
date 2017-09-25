package io.yorkecao.jwtauth.service.impl;

import io.yorkecao.jwtauth.service.JwtAuthService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

/**
 * @author Yorke
 */
@Service
public class JwtAuthServiceImpl implements JwtAuthService {
    @Override
    public String read() {
        return "reading...";
    }

    @Override
    public String write() {
        return "writting...";
    }
}
