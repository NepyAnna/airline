package com.sheoanna.airline.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private Long id_profile;

    private String email;
    private String address;

    @Column(name = "url_photo", length = 255, nullable = true)
    private String photoUrl;
    
    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    public Profile() {
    }

    public Profile(String email, String address, User user) {
        this.email = email;
        this.address = address;
        this.user = user;
    }
}
