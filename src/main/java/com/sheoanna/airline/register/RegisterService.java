package com.sheoanna.airline.register;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sheoanna.airline.encryptions.IEncryptFacade;
import com.sheoanna.airline.role.RoleService;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserDto;
import com.sheoanna.airline.users.UserRepository;
import com.sheoanna.airline.users.exceptions.UserAlreadyExistsException;

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

        if (userRepository.findByUsername(userData.username()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userData.username() + " already exists");
        }

        String passwordDecoded = encryptFacade.decode("base64", userData.password());

        String passwordEncoded = encryptFacade.encode("bcrypt", passwordDecoded);

        User newUser = new User(userData.username(), passwordEncoded);
        newUser.setRoles(roleService.assignDefaultRole());

        userRepository.save(newUser);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Success");

        return response;
    }

}
