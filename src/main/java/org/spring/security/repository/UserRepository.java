package org.spring.security.repository;

import org.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository -> CRUD 내장
// @Repository annotation 없어도 IoC 가능 -> extends JpaRepository
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy 규칙 -> Username syntax
    // select * from user where username=?

    public User findByUsername(String username); // Jpa Query methods
}
