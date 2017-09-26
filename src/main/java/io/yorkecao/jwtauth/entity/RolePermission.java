package io.yorkecao.jwtauth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Yorke
 */
@Entity
public class RolePermission {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String role;
    private String permission;

    protected RolePermission() {

    }

    public RolePermission(String role, String permission) {
        this.role = role;
        this.permission = permission;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
