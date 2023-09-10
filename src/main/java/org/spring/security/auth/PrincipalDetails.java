package org.spring.security.auth;

// Security -> /login 주소 요청 시에 낚아채서 로그인을 진행
// 로그인이 완료되면 security session 생성 (Security ContextHolder)
// Obj Type -> Authentication Type obj
// In Authentication, need User info
// User obj type -> UserDetails Type obj

// In Security Session -> Authentication Type obj -> UserDetails Type obj (PrincipalDetails) -> User obj

import org.spring.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user; // Composition

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User 권한을 리턴하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 바뀌도록 설정
        // user.getLoginDate();
        // 현재시간 - 로그인시간 -> 1년 초과 시, return false

        return true;
    }
}
