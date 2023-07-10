package com.example.backend.controller;

import com.example.backend.DTOI.LoginUserDTOI;
import com.example.backend.DTOI.RegisterUserDTOI;
import com.example.backend.DTOO.LoginUserDTOO;
import com.example.backend.DTOO.RegisterUserDTOO;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginUserDTOO> loginUser(@RequestBody LoginUserDTOI dtoi) {
        try {
            LoginUserDTOO dataToRet = authService.loginUser(dtoi);

            return ResponseEntity.status(HttpStatus.OK).body(dataToRet);
        }
        catch(Error e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserDTOO> registerUser(@RequestBody RegisterUserDTOI dtoi) {
        RegisterUserDTOO dataToRet = authService.registerUser(dtoi);

        if (!dataToRet.getError()) {
            return ResponseEntity.status(HttpStatus.OK).body(dataToRet);
        }

        return ResponseEntity.badRequest().body(dataToRet);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            Boolean isDeleted = authService.deleteUser(Long.parseLong(id));

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        catch(NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
