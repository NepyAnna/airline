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
import jakarta.persistence.PrePersist;
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

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    @JsonBackReference
    private User user;

    public Profile(String email, User user) {
        this.email = email;
        this.user = user;
    }

    public Profile(String email, String phoneNumber, String address, String photoUrl, User user) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.photoUrl = photoUrl;
        this.user = user;
    }

    @PrePersist
    public void setDefaultPhoto() {
        if (this.photoUrl.isEmpty()) {
            this.photoUrl = "https://postimg.cc/Y4hcfndB";
        }
    }

}
