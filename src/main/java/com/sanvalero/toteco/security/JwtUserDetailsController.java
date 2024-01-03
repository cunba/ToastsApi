package com.sanvalero.toteco.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanvalero.toteco.model.UserModel;
import com.sanvalero.toteco.service.UserService;

@Service
public class JwtUserDetailsController implements UserDetailsService {

    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final List<UserModel> user = us.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("UserDTO '" + username + "' not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.get(0).getPassword())
                .authorities("ROLE_" + user.get(0).getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}