package com.abhaya.vehicle.tracking.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.abhaya.vehicle.tracking.data.model.Roles;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(com.abhaya.vehicle.tracking.data.model.UserDetails user) 
    {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                mapToGrantedAuthorities(user.getRoles()),
                user.getPassword(),
                user.getIsEnabled(),
                user.getMobileNumber(),
                user.getFirstName(),
                user.getLastName(),
                user.getLastPasswordResetDate()
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Roles> authorities) 
    {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
    }
}
