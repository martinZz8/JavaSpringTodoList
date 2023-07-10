package com.example.backend.controller;

import com.example.backend.DTOI.LoginUserDTOI;
import com.example.backend.DTOI.RegisterUserDTOI;
import com.example.backend.DTOO.LoginUserDTOO;
import com.example.backend.DTOO.RegisterUserDTOO;
import com.example.backend.enums.ERole;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.service.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginUserDTOO> loginUser(@RequestBody LoginUserDTOI dtoi) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoi.getUsername(), dtoi.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginUserDTOO dataToRet = new LoginUserDTOO(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );

        return ResponseEntity.status(HttpStatus.OK).body(dataToRet);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserDTOO> registerUser(@RequestBody RegisterUserDTOI dtoi) {
        if (userRepository.existsByUsername(dtoi.getUsername())) {
            return ResponseEntity.badRequest().body(new RegisterUserDTOO("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(dtoi.getEmail())) {
            return ResponseEntity.badRequest().body(new RegisterUserDTOO("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(
                dtoi.getUsername(),
                dtoi.getEmail(),
                encoder.encode(dtoi.getPassword()),
                dtoi.getFirstName(),
                dtoi.getLastName()
        );

        // Find "USER" role to fill the user object
        Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new RegisterUserDTOO("User registered successfully!"));
    }
}
