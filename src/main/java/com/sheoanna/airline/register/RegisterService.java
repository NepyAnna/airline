package com.sheoanna.airline.register;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sheoanna.airline.encryptions.IEncryptFacade;
import com.sheoanna.airline.role.RoleService;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserDto;
import com.sheoanna.airline.users.UserRepository;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final RoleService roleService;
     private final IEncryptFacade encryptFacade;

     public RegisterService(UserRepository userRepository, RoleService roleService, IEncryptFacade encryptFacade) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encryptFacade = encryptFacade;
    }

    public Map<String, String> save(UserDto userData) {

        String passwordDecoded = encryptFacade.decode("base64",userData.password());
        /*Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(userData.password());
        String passwordDecoded = new String(decodedBytes);*/

       // System.out.println("<------------ " + passwordDecoded);
        
        String passwordEncoded = encryptFacade.encode("bcrypt", passwordDecoded);
        /* 
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncoded = encoder.encode(passwordDecoded);*/

        User newUser = new User(userData.username(), passwordEncoded);
        newUser.setRoles(roleService.assignDefaultRole());

        userRepository.save(newUser);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Success");

        return response;
    }

}
