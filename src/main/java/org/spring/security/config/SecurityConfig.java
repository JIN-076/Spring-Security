package org.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity // Spring Security Filter (=SecurityConfig) Registered to Spring FilterChain
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured annotation enable, preAuthorize, postAuthorize annotation enable
public class SecurityConfig {

    @Bean // returned Obj register IoC
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity.csrf().disable(); // csrf() is deprecated
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/user/**").authenticated() // 인증만 되면 접근 가능
                        .requestMatchers("/manager/**").access(
                                new WebExpressionAuthorizationManager(
                                        "hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')"))
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
        ).formLogin((formLogin) -> formLogin.loginPage("/loginForm")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login") // /login 주소가 호출이 되면 Security 가 낚아채 대신 로그인 진행
                        .defaultSuccessUrl("/"));
        return httpSecurity.build();
//        httpSecurity.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults());
//        return httpSecurity.build();
    }
}
