package com.example.employeemanagement.dao;

import com.example.employeemanagement.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//    @Query(value = "SELECT u FROM User u WHERE u.employee.email = :username")
//    User findByUsername(@Param("username") String username);
    User findByUsername(String username);
    User findByToken(String token);
}
