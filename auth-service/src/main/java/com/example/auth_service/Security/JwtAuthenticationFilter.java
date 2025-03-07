package com.example.auth_service.Security;

import com.example.auth_service.Service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final Jwtutil jwtutil;

    private final AuthService authService;

    public JwtAuthenticationFilter(AuthService authService,Jwtutil jwtutil) {
        this.authService = authService;
        this.jwtutil=jwtutil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")){
            token=token.substring(7);
            String employeeName=jwtutil.getNameByToken(token);
            if (employeeName != null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = authService.loadUserByUsername(employeeName);
                if (jwtutil.isValidate(token)){
                    UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.setContext(createEmptyContext().setAuthentication(authToken));
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);

                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
