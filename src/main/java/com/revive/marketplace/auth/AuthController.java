package com.revive.marketplace.auth;

import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserDTO;
import com.revive.marketplace.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {
    
    private final UserServiceImpl userService;
    
    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }
    
    // Handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }
    
    // Handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Create model object to store form data
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "register";
    }
    
    // Handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto,
                               BindingResult result,
                               Model model) {
        // Check if user already exists
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                  "There is already an account registered with the same email");
        }
        
        // If there are validation errors, return to the registration form
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }
        
        // Save the user and redirect to the registration page with a success message
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
    
    // Handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}