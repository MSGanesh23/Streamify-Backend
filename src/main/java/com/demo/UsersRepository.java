package com.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {

    @Query("select count(U) from Users U where U.email=:email")
    public int validateEmail(@Param("email") String email);

    @Query("select count(u) from Users u where u.username=:username and u.password=:password")
    public int validateCredentials(@Param("username") String username, @Param("password") String password);

    Users findByusername(String username);
}
