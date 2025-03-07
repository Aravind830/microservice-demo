package com.example.auth_service.Service;

import com.example.auth_service.Entity.Auth;
import com.example.auth_service.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Override//Authorize a Employee to Project
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth=authRepository.findByUserName(username)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found "+ username));
        return new org.springframework.security.core.userdetails.User(auth.getUserName(),auth.getPassword(),
                auth.getRoles().stream().map(roles->new SimpleGrantedAuthority("ROLE_"+roles.getRoleName())).collect(Collectors.toList()));
    }
}
