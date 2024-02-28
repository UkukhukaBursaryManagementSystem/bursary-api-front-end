//package com.ukukhula.bursaryapi.security;
//
//import com.ukukhula.bursaryapi.entities.User;
//import com.ukukhula.bursaryapi.services.UserService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class CustomUserDetails implements UserDetailsService {
//    private final UserService userService;
//
//    public CustomUserDetails(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userService.getUserByEmail(username);
//
//        return UserPrincipal.builder().
//
//    }
//}
