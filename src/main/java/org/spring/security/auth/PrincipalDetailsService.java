package org.spring.security.auth;

import org.spring.security.model.User;
import org.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Security Setting -> loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadByUsername 메서드 실행

@Service
public class PrincipalDetailsService implements UserDetailsService {

    // param username -> SHOULD EQUAL loginForm inputType name!

    @Autowired
    private UserRepository userRepository;

    // Security Session -> Authentication Type -> UserDetails Type (PrincipalDetails)
    // returned UserDetails -> Authentication 내부로 전달됨
    // returned Authentication -> Security Session 내부로 전달됨
    // Security Session (내부 Authentication (내부 UserDetails) )

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
