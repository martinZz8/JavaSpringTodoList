package com.example.backend.service;

import com.example.backend.DTOI.LoginUserDTOI;
import com.example.backend.DTOI.RegisterUserDTOI;
import com.example.backend.DTOO.LoginUserDTOO;
import com.example.backend.DTOO.RegisterUserDTOO;
import com.example.backend.DTOO.UserDTOO;
import com.example.backend.enums.ERole;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.service.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
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

    @Autowired
    @Qualifier("entityToDTOConversionService")
    private ConversionService conversionService;

    public LoginUserDTOO loginUser(LoginUserDTOI dtoi) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoi.getUsername(), dtoi.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new LoginUserDTOO(
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles
        );
    }

    public RegisterUserDTOO registerUser(RegisterUserDTOI dtoi) {
        if (userRepository.existsByUsername(dtoi.getUsername())) {
            return new RegisterUserDTOO(true, "Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(dtoi.getEmail())) {
            return new RegisterUserDTOO(true, "Error: Email is already in use!");
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
        Optional<Role> o_userRole = roleRepository.findByName(ERole.ROLE_USER);

        if (!o_userRole.isPresent()) {
            return new RegisterUserDTOO(true, "Error: Role is not found.");
        }

        user.setRoles(new HashSet<>(Arrays.asList(o_userRole.get())));
        userRepository.save(user);

        return new RegisterUserDTOO(true, "User registered successfully!");
    }

    public Optional<UserDTOO> getUserById(Long id) {
        Optional<User> o_user = userRepository.findById(id);

        if (o_user.isPresent()) {
            UserDTOO dto = conversionService.userToDTOO(o_user.get());

            return Optional.of(dto);
        }

        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        Optional<User> o_user = userRepository.findById(id);

        if (o_user.isPresent()) {
            userRepository.delete(o_user.get());

            return true;
        }

        return false;
    }
}
