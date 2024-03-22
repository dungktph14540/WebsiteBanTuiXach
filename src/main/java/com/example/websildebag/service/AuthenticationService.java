package com.example.websildebag.service;

import com.example.websildebag.entity.Roles;
import com.example.websildebag.entity.User_Role;
import com.example.websildebag.entity.Users;
import com.example.websildebag.repository.RoleRepository;
import com.example.websildebag.repository.UserRepository;
import com.example.websildebag.repository.UserRoleRepository;
import com.example.websildebag.security.AuthenticationRequest;
import com.example.websildebag.security.AuthenticationResponse;
import com.example.websildebag.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) { // luu
        Roles role = roleRepository.findByRoleName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Roles(null,"ROLE_USER")));
        Users user = Users.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User_Role userRole = User_Role.builder()
                .users(user)
                .roles(role)
                .build();
        userRepository.save(user);
        userRoleRepository.save(userRole);
        var jwtToken = jwtService.generateToken(userRole);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user= userRoleRepository.findByUsers_Username(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}
