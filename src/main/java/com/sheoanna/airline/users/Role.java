package com.sheoanna.airline.users;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_role")
    private Long idRole;
    
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    Set<User> users;
}
