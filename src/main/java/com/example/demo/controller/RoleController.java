package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/find/{role}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<Role> findRole(@PathVariable String role){
        return roleService.findRoleText(role);
    }
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Role create(@RequestBody Role role ){
        return roleService.create(role);
    }
    @PutMapping("/change/{id}/{text}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Role change(@PathVariable Long id, @PathVariable String text){
        return roleService.changeText(id, text);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delete(@PathVariable Long id){
        return roleService.delete(id);
    }
}
