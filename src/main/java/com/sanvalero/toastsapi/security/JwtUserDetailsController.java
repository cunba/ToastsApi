package com.sanvalero.toastsapi.security;

import com.sanvalero.toastsapi.model.UserModel;
import com.sanvalero.toastsapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class JwtUserDetailsController implements UserDetailsService {

    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Flux<UserModel> user = us.findByUsername(username);

        if (user.equals(null)) {
            throw new UsernameNotFoundException("UserDTO '" + username + "' not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.last().block().getPassword())
                .authorities("ROLE_" + user.last().block().getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}