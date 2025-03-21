package com.revive.marketplace.user;

import java.util.List;

public interface UserService {
    User saveUser(UserDTO userDto);
    User saveUser(User user);
    User findByEmail(String email);
    User findUserByEmail(String email); 
    User getUserByEmail(String email);  
    User findByUsername(String username);
    User getUserById(Long id);
    List<User> findAllUsers();
    List<User> getAllUsers();
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    void deleteUserById(Long id);
}
