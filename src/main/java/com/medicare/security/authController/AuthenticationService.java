package com.medicare.security.authController;




import com.medicare.entity.Role;
import com.medicare.entity.User;
import com.medicare.repo.UserRepo;
import com.medicare.security.authModel.AuthRequest;
import com.medicare.security.authModel.AuthResponse;
import com.medicare.security.authModel.SignUp;
import com.medicare.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(SignUp request){
        User user= User.builder()
                .name(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)

                .build();
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new UsernameNotFoundException("User Email already existls");
        }

        userRepo.save(user);


        String jwtToken =jwtService.generateToken(user);

        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse authenticate(AuthRequest request) {

        System.err.println(request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),

                        request.getPassword()
                )

        );

        var user=userRepo.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        String jwtToken =jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

}
