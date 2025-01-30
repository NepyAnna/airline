package com.sheoanna.airline.users;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
        testUser.setIdUser(1L);
    }
    
    @Test
    void index_ShouldReturnListOfUsers() throws Exception {
        
        when(userService.findAll()).thenReturn(List.of(testUser));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    void show_ShouldReturnUser_WhenExists() throws Exception {
        when(userService.findById(1L)).thenReturn(testUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"));
    }
    
    @Test
    void delete_ShouldReturnNoContent_WhenUserExists() throws Exception {
        doNothing().when(userService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
