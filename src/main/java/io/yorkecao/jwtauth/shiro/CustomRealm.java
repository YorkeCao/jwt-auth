package io.yorkecao.jwtauth.shiro;

import io.yorkecao.jwtauth.entity.RolePermission;
import io.yorkecao.jwtauth.entity.UserRole;
import io.yorkecao.jwtauth.repository.RolePermissionRepository;
import io.yorkecao.jwtauth.repository.UserRepository;
import io.yorkecao.jwtauth.repository.UserRoleRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @author Yorke
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Set<String> roles = userRoleRepository
                .findByUsername(username)
                .stream()
                .map(UserRole::getRoleName)
                .collect(toSet());
        authorizationInfo.setRoles(roles);

        roles.forEach(role -> {
                    Set<String> permissions =rolePermissionRepository
                            .findByRole(role)
                            .stream()
                            .map(RolePermission::getPermission)
                            .collect(toSet());
                     authorizationInfo.addStringPermissions(permissions);
                });

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = userRepository.findUserByUsername(username).getPassword();

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
