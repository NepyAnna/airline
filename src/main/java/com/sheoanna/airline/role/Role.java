package com.sheoanna.airline.role;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sheoanna.airline.users.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    Set<User> users;
}
