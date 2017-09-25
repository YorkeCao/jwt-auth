package io.yorkecao.jwtauth.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @author Yorke
 */
public interface JwtAuthService {
    @RequiresPermissions("read")
    String read();
    @RequiresPermissions("write")
    String write();
}
