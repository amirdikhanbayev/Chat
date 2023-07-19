package com.example.demo.service.role;

import com.example.demo.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findRoleText(String role);

    Role create(Role role);

    Role changeText(Long id, String Text);

    String delete(Long id);
}
