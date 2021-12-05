package com.typicalcoderr.Deliverit.security;

import com.typicalcoderr.Deliverit.Service.AuthService;
import com.typicalcoderr.Deliverit.Service.UserDetailsServiceImplementation;
import com.typicalcoderr.Deliverit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Wed
 * Time: 6:03 PM
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Qualifier("userDetailsServiceImplementation")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Get bearer token
        String token = getTokenFromRequest(request);

        if(StringUtils.hasText(token)){

            String username = jwtProvider.getUsernameFromToken(token);

            UserDetails userDetails  = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set UsernamePasswordAuthenticationToken object
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request, response);

//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null) {
//            filterChain.doFilter(request, response);
//            return;
//        } else {
//            try {
//
//                String token = authHeader.substring("Bearer ".length());
//                String usernameFromToken = jwtProvider.getUsernameFromToken(token);
//                UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
//                Boolean isTokenValid = jwtProvider.validateToken(token, userDetails);
//
//                if (isTokenValid && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UsernamePasswordAuthenticationToken uPToken = new UsernamePasswordAuthenticationToken(
//                            userDetails.getUsername(), null, userDetails.getAuthorities()
//                    );
//                    uPToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(uPToken);
//                } else {
//                    SecurityContextHolder.getContext().setAuthentication(null);
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            } finally {
//                filterChain.doFilter(request, response);
//            }
//        }








    }

    //Utility function to get token from request
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer_token = request.getHeader("Authorization");

        if(StringUtils.hasText(bearer_token) && bearer_token.startsWith("Bearer ")) {
            return bearer_token.substring(7); //Remove "Bearer " part
        }
        return bearer_token;
    }


}
