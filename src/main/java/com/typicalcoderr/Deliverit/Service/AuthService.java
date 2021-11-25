package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.AuthResponse;
import com.typicalcoderr.Deliverit.dto.ChangePasswordRequest;
import com.typicalcoderr.Deliverit.dto.LoginRequest;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import com.typicalcoderr.Deliverit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Fri
 * Time: 4:08 PM
 */
@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse login(LoginRequest request) throws DeliveritException {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsServiceImplementation.loadUserByUsername((request.getEmail()));

        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new DeliveritException("user not found!"));

        final String token = jwtProvider.generateToken(userDetails);

        return new AuthResponse(token,user.getUserRole(),user.getEmail());

    }

    @Transactional
    public SimpleMessageDto changePassword(ChangePasswordRequest request) throws DeliveritException {

        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If student is not found
        com.typicalcoderr.Deliverit.domain.User found_user = userRepository.findById(user.getUsername()).orElseThrow(()->new DeliveritException("User not found"));

        //Check if old password matches
        if (!passwordEncoder.matches(request.getOldPassword(), found_user.getPassword())) {
            throw new DeliveritException("Old password is incorrect");
        }

        //Change password
        found_user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        //Save in database
        userRepository.save(found_user);

        return new SimpleMessageDto("Password changed successfully", HttpStatus.OK);


    }
}
