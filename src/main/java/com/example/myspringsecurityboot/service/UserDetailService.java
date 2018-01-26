package com.example.myspringsecurityboot.service;

import com.example.myspringsecurityboot.model.Role;
import com.example.myspringsecurityboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService{
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userService.getUserByUsername(s);
        Set<GrantedAuthority> grantedAuthoritySet=new HashSet<GrantedAuthority>();
        user.getRoles().forEach((Role r)-> grantedAuthoritySet.add(new SimpleGrantedAuthority(r.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthoritySet);
    }
}
