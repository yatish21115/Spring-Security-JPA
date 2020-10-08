package com.login.springsecurityjpa;

import com.login.springsecurityjpa.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleActiveRepository extends JpaRepository<User,Integer> {
    public User findByroles(String roles);
}
