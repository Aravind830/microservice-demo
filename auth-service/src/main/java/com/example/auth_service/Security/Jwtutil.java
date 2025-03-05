package com.example.auth_service.Security;

import com.example.auth_service.Entity.Auth;
import com.example.auth_service.Entity.Roles;
import com.example.auth_service.Repository.AuthRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Jwtutil {

    @Autowired
    private AuthRepository authRepository;

    String base64Secret = "kD7hGR2jAXyzL1B9o+zNTQ6PtWh2i0pWx/gMPKc5UO9tR9FgVv/N6LgPiZltTyIm28SC3PLDUN8sKZ1xGt4KAQ==";
    Key secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    private final int jwtExpire=3600000;

    public String generateToken(String userName){
        Optional<Auth> auth=authRepository.findByUserName(userName);
        List<Roles> rolesList=auth.orElseThrow().getRoles();

        return Jwts.builder()
                .setSubject(userName)
                .claim("roles",rolesList.stream()
                        .map(roles1 -> roles1.getRoleName())
                        .collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpire))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
