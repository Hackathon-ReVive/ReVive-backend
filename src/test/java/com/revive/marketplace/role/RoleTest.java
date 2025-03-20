package com.revive.marketplace.role;

import com.revive.marketplace.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
    }

    @Test
    @DisplayName("Should create Role object successfully")
    void should_Create_Role_Object_Successfully() {
        assertNotNull(role, "Role object should not be null");
        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getName());
    }

    @Test
    @DisplayName("Should correctly assign users to role")
    void should_Assign_Users_To_Role() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("JohnDoe");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("JaneDoe");

        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);

        role.setUsers(users);

        assertNotNull(role.getUsers());
        assertEquals(2, role.getUsers().size());
        assertTrue(role.getUsers().contains(user1));
        assertTrue(role.getUsers().contains(user2));
    }

    @Test
    @DisplayName("Should correctly retrieve enum values")
    void should_Retrieve_Enum_Values_Correctly() {
        assertEquals("USER", Role.Type.USER.name());
        assertEquals("ADMIN", Role.Type.ADMIN.name());
        assertEquals("SELLER", Role.Type.SELLER.name());
    }
}
