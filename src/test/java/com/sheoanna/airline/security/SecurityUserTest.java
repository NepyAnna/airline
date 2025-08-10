package com.sheoanna.airline.security;

import com.sheoanna.airline.role.Role;
import com.sheoanna.airline.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityUserTest {
    private SecurityUser securityUser;
    private User mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = mock(User.class);

        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getPassword()).thenReturn("testPassword");

        securityUser = new SecurityUser(mockUser);
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUser", securityUser.getUsername(), "Username should match the mocked value.");
    }

    @Test
    public void testGetPassword() {
        assertEquals("testPassword", securityUser.getPassword(), "Password should match the mocked value.");
    }

    @Test
    public void testGetAuthorities() {
        Role role1 = mock(Role.class);
        when(role1.getName()).thenReturn("ROLE_USER");

        Role role2 = mock(Role.class);
        when(role2.getName()).thenReturn("ROLE_ADMIN");

        when(mockUser.getRoles()).thenReturn(Set.of(role1, role2));

        Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();

        assertEquals(2, authorities.size(), "The user should have 2 authorities.");
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")),
                "Authorities should contain ROLE_USER.");
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")),
                "Authorities should contain ROLE_ADMIN.");
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(securityUser.isAccountNonExpired(), "Account should not be expired.");
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(securityUser.isAccountNonLocked(), "Account should not be locked.");
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(securityUser.isCredentialsNonExpired(), "Credentials should not be expired.");
    }

    @Test
    public void testIsEnabled() {
        assertTrue(securityUser.isEnabled(), "Account should be enabled.");
    }
}