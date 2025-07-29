package com.sheoanna.airline.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheoanna.airline.cloudinary.CloudinaryService;
import com.sheoanna.airline.role.Role;
import com.sheoanna.airline.role.RoleRepository;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Set;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class ProfileControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @MockitoBean
    private CloudinaryService cloudinaryService;

    private final String BASE_URL = "/api/v1/profiles";

    private User user;

    @BeforeEach
    void setup() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        user = User.builder()
                .username("testuser")
                .password("encodedPassword")
                .roles(Set.of(role))
                .build();

        Profile profile = Profile.builder()
                .email("test@email.com")
                .address("Some Address")
                .phoneNumber("123456789")
                .user(user)
                .build();

        user.setProfile(profile);
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetProfileById() throws Exception {
        Long profileId = user.getProfile().getId();

        mockMvc.perform(get(BASE_URL + "/" + profileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteProfile() throws Exception {
        Long profileId = user.getProfile().getId();

        mockMvc.perform(delete(BASE_URL + "/" + profileId))
                .andExpect(status().isNoContent());
    }
}