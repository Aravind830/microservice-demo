package com.example.auth_service.Controller;

import com.example.auth_service.Entity.Auth;
import com.example.auth_service.Entity.Roles;
import com.example.auth_service.Repository.AuthRepository;
import com.example.auth_service.Repository.RolesRepository;
import com.example.auth_service.Security.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Jwtutil jwtutil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Auth auth){
        if (authRepository.findByUserName(auth.getUserName()).isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(auth.getUserName() + " Already Exists..!");
        }
        Auth user=new Auth();
        user.setUserName(auth.getUserName());
        user.setPassword(passwordEncoder.encode(auth.getPassword()));
        List<Roles> userRoles=new ArrayList<>();
        for (Roles role:auth.getRoles()){
            Roles rol=rolesRepository.findByRoleName(role.getRoleName())
                    .orElseThrow(()->new UsernameNotFoundException("Role Not Found"));
            userRoles.add(rol);
        }
        user.setRoles(userRoles);
        authRepository.save(user);
        return ResponseEntity.ok("Registered SuccessFully..!!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Auth auth){
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(auth.getUserName(), auth.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(jwtutil.generateToken(auth.getUserName()));
    }
}
