package com.service;

import com.dto.AuthenticationRequest;
import com.dto.AuthenticationResponse;
import com.userdetail.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenService jwtTokenService;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(),
            authenticationRequest.getPassword()
        );

        final Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        final UserPrincipal userPrincipal = (UserPrincipal) authenticate.getPrincipal();

        return AuthenticationResponse.builder()
            .username(userPrincipal.getUsername())
            .token(jwtTokenService.generateToken(userPrincipal))
            .build();
    }
}
