package com.sample.API_Gateway_service.Filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.List;

@Component
public class JwtUtil {


    @Value("${base64Secret}")
    private String base64;

//    Key secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64));
//    byte[] decodedKey = Base64.getDecoder().decode(base64);
//    Key secretKey = Keys.hmacShaKeyFor(decodedKey);

    public JwtUtil(){}

    public String getNameByToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(base64);
        return Keys.hmacShaKeyFor(keyBytes);
    }
//    public Key getSecretKey(){
//        byte[] decodedKey = Base64.getDecoder().decode(base64);
//        return Keys.hmacShaKeyFor(decodedKey);
//    }

    public List<String> getRolesByToken(String token){
        String roles= Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody().get("roles",String.class);
        return List.of(roles);
    }

    public void isValidate(String token){
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
    }
}
