package com.sheoanna.airline.users;

import java.util.Set;

import org.springframework.context.annotation.Profile;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id_user;

    private String username;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles;

    public User() {
    }

    public User(String username, String password, Profile profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}
