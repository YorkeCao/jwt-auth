package io.yorkecao.jwtauth.shiro;

import io.yorkecao.jwtauth.dao.JwtAuthDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author Yorke
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private JwtAuthDao jwtAuthDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = jwtAuthDao.getRolesByUsername(username);
        authorizationInfo.setRoles(roles);
        roles.forEach(role -> {
            Set<String> permissions = jwtAuthDao.getPermissionsByRole(role);
            authorizationInfo.addStringPermissions(permissions);
        });

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = jwtAuthDao.getPasswordByUsername(username);

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
