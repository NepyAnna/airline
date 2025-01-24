package com.sheoanna.airline.profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sheoanna.airline.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private Long idProfile;

    private String email;
    private String address;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    @JsonBackReference//буде ігноруватися при серіалізації (
    private User user;

    public Profile(String email, String address, User user) {
        this.email = email;
        this.address = address;
        this.user = user;
    }     
}
