package com.example.demo.service.user;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.chatroom.ChatRoomService;
import com.example.demo.service.get.GetService;
import com.example.demo.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private GetService getService;
    @Autowired
    private RoleService roleService;

    @Override
    public Users create(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOnline(false);
        return usersRepository.save(user);
    }
    @Override
    public Users changeUsername(Long id, String username){
        Users user = usersRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
        user.setUsername(username);
        return usersRepository.save(user);
    }
    @Override
    public String deleteUser(Long id){
        usersRepository.deleteById(id);
        return "User has been deleted";
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findById(Long id){
        return  usersRepository.findById(id);
    }
    @Override
    public List<Users> listAll(){
        return usersRepository.findAll();
    }

    @Override
    public void online(Long id) {
        usersRepository.online(id);
    }

    @Override
    public void offline(Long id) {
        usersRepository.offline(id);
    }

    @Override
    public List<Users> listAllOnline(boolean online){
        return usersRepository.findByOnline(online);
    }

    @Override
    public Users joinToChatRoom(String chatRoomName){
       ChatRoom chatRoom = chatRoomService.findByName(chatRoomName);
       Users users = getService.getCurrentUser();
       users.getChatRooms().add(chatRoom);
       return usersRepository.save(users);
    }
    @Override
    public List<Users> UsersInChat(ChatRoom chatRoom){
        List<Users> users = usersRepository.findUsersByChatRooms(chatRoom);
        return users;
    };

    @Override
    public Users addRoleToUser(String username, String roleName){
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException());
        users.getRoles().add(roleService.findRoleText(roleName));
        return usersRepository.save(users);
    }

}
