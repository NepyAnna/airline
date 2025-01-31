package com.sheoanna.airline.profile;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sheoanna.airline.users.User;


class ProfileTest {
    private Profile profile;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setIdUser(1L);
    }

    @Test
    void testNoArgsConstructor() {
        profile = new Profile();
        assertThat(profile).isNotNull();
    }

    @Test
    void testConstructorWithEmailAndUser() {
        profile = new Profile("test@example.com", user);
        assertThat(profile.getEmail()).isEqualTo("test@example.com");
        assertThat(profile.getUser()).isEqualTo(user);
    }

    @Test
    void testFullConstructor() {
        profile = new Profile("test@example.com", "1234567890", "123 Street", "http://image.url", user);
        assertThat(profile.getEmail()).isEqualTo("test@example.com");
        assertThat(profile.getPhoneNumber()).isEqualTo("1234567890");
        assertThat(profile.getAddress()).isEqualTo("123 Street");
        assertThat(profile.getPhotoUrl()).isEqualTo("http://image.url");
        assertThat(profile.getUser()).isEqualTo(user);
    }

    @Test
    void testSettersAndGetters() {
        profile = new Profile();
        profile.setEmail("new@example.com");
        profile.setPhoneNumber("0987654321");
        profile.setAddress("New Address");
        profile.setPhotoUrl("http://newimage.url");
        profile.setUser(user);

        assertThat(profile.getEmail()).isEqualTo("new@example.com");
        assertThat(profile.getPhoneNumber()).isEqualTo("0987654321");
        assertThat(profile.getAddress()).isEqualTo("New Address");
        assertThat(profile.getPhotoUrl()).isEqualTo("http://newimage.url");
        assertThat(profile.getUser()).isEqualTo(user);
    }

    @Test
    void testPrePersistSetsDefaultPhotoIfEmpty() {
        profile = new Profile("test@example.com", user);
        profile.setPhotoUrl("");

        profile.setDefaultPhoto();

        assertThat(profile.getPhotoUrl()).isEqualTo("https://postimg.cc/Y4hcfndB");
    }

    @Test
    void testPrePersistDoesNotOverridePhotoUrlIfPresent() {
        profile = new Profile("test@example.com", user);
        profile.setPhotoUrl("http://custom.url");

        profile.setDefaultPhoto();

        assertThat(profile.getPhotoUrl()).isEqualTo("http://custom.url");
    }
}

