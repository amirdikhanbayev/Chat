package com.example.demo.service.role;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleRepository roleRepository;
    @Test
    void changeText() {
        String string = "Text";
        Role role = new Role();
        role.setId(1L);
        role.setRole("ROLE_ADMIN");

        Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        Mockito.when(roleRepository.save(any())).thenReturn(role);

        Role changedRole = roleService.changeText(1L, string);

        verify(roleRepository).findById(any());
        verify(roleRepository).save(any());
        assertEquals(string, changedRole.getRole());
    }
}