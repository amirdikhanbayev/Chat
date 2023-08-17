package com.example.demo.service.user;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.chatroom.ChatRoomService;
import com.example.demo.service.role.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ChatRoomService chatRoomService;
    @Mock
    private RoleService roleService;

    @AfterEach
    void tearDown(){
        usersRepository.deleteAll();
    }
    @Test
    void listAll() {
        userService.listAll();
        verify(usersRepository).findAll();
    }

    @Test
    void create() {
        Users user = new Users();
        user.setUsername("john");
        user.setPassword("password");

        Users newUser = new Users();
        newUser.setId(12L);
        newUser.setUsername("john");
        newUser.setPassword("password");
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("12345");
        Mockito.when(usersRepository.save(user)).thenReturn(newUser);

        Users savedUser = userService.create(user);
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertNotNull(savedUser.getId());
    }

    @Test
    void changeUsername() {
        String newUsername = "Admin";
        Users user = new Users();
        user.setId(1L);
        user.setUsername("john");
        user.setPassword("password");
        Mockito.when(usersRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Mockito.when(usersRepository.save(any())).thenReturn(user);
        Users changedUser = userService.changeUsername(user.getId(),newUsername);
        verify(usersRepository).findById(1L);
        verify(usersRepository).save(user);
        assertEquals(newUsername,changedUser.getUsername());
    }

    @Test
    void findByUsername() {
        Users user = new Users();
        user.setId(1L);
        user.setUsername("john");
        user.setPassword("password");
        Mockito.when(usersRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Optional<Users> findUser = userService.findByUsername(user.getUsername());
        verify(usersRepository).findByUsername(user.getUsername());
        assertEquals(Optional.of(user),findUser);
    }
    @Test
    void joinToChatRoom() {
        String chatRoomName = "ChatRoom";
        String username = "john";

        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password");
        user.setOnline(true);
        user.setChatRooms(new ArrayList<>());

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1L);
        chatRoom.setName(chatRoomName);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(usersRepository.save(any())).thenReturn(user);
        when(chatRoomService.findByName(any())).thenReturn(chatRoom);

        Optional<Users> newUser = userService.joinToChatRoom(chatRoomName);

        assertEquals(List.of(chatRoom), newUser.get().getChatRooms());
        verify(usersRepository).findByUsername(username);
        verify(usersRepository).save(user);
        verify(chatRoomService).findByName(chatRoomName);
    }


    @Test
    void addRoleToUser() {
        String username ="john";
        String roleName = "ROLE_ADMIN";
        Role role = new Role();
        role.setRole(roleName);
        role.setId(1L);
        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password");
        user.setRoles(new ArrayList<>());

        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(roleService.findRoleText(any())).thenReturn(Optional.of(role));
        when(usersRepository.save(any())).thenReturn(user);

        Users newUser = userService.addRoleToUser(username,roleName);

        verify(usersRepository).save(user);
        verify(usersRepository).findByUsername(username);
        verify(roleService).findRoleText(roleName);

        assertEquals(List.of(role),newUser.getRoles());
    }
}