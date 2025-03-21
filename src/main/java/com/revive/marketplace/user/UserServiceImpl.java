package com.revive.marketplace.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User saveUser(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhonenumber(userDto.getPhonenumber());
        user.setAddress(userDto.getAddress());

        if (userDto.getRole() != null) {
            try {
                user.setRole(User.UserRole.valueOf(userDto.getRole()));
            } catch (IllegalArgumentException e) {
                user.setRole(User.UserRole.USER);
            }
        } else {
            user.setRole(User.UserRole.USER);
        }
        
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User findUserByEmail(String email) {
        return findByEmail(email);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return findByEmail(email);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public List<User> getAllUsers() {
        return findAllUsers();
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
