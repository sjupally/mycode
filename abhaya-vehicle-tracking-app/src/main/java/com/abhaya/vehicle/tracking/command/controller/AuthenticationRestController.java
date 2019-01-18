package com.abhaya.vehicle.tracking.command.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.security.JwtAuthenticationRequest;
import com.abhaya.vehicle.tracking.security.JwtTokenUtil;
import com.abhaya.vehicle.tracking.security.JwtUser;
import com.abhaya.vehicle.tracking.services.UserService;
import com.abhaya.vehicle.tracking.util.JwtAuthenticationResponse;
import com.abhaya.vehicle.tracking.vos.UsersDetailsVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthenticationRestController 
{
    @Value("${jwt.header}")
    private String tokenHeader;
    

    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserService userService;

    @RequestMapping(value = "/${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException 
    {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        if (userDetails != null)
        {
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        }
        throw new UsernameNotFoundException(String.format("No user found with username '%s'.", authenticationRequest.getUsername()));
    }
    
    @RequestMapping(value = "v1/readUser", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public UsersDetailsVO getAuthenticatedUser(Authentication authentication,Principal principal) 
    {
        return userService.readUserData(principal.getName());
    }
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) 
    {
    	log.info("---- START ---- REQUEST::"+ request);
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) 
        {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } 
        else 
        {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
