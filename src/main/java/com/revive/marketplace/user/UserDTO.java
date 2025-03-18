package com.revive.marketplace.user;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phonenumber;
    private String address;
    private String role;
    
    public UserDTO(Long id, String username, String email, String phonenumber, String address,
                   String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.role = role;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhonenumber() {
        return phonenumber;
    }
    
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
