package com.example.demo.service.user;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.chatroom.ChatRoomService;
import com.example.demo.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    public Optional<Users> joinToChatRoom(String chatRoomName){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());
       Optional<ChatRoom> chatRoom = Optional.ofNullable(chatRoomService.findByName(chatRoomName));
       if(chatRoom.isPresent()){
           users.getChatRooms().add(chatRoom.orElseThrow(()-> new EntityNotFoundException()));
           return Optional.of(usersRepository.save(users));
       } else throw new RuntimeException("Not founded");
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
        users.getRoles().add(roleService.findRoleText(roleName).orElseThrow(()-> new EntityNotFoundException("Not found")));
        return usersRepository.save(users);
    }


}
