package com.sheoanna.airline.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sheoanna.airline.bookings.Booking;
import com.sheoanna.airline.profile.Profile;
import com.sheoanna.airline.role.Role;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private String username;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
}
