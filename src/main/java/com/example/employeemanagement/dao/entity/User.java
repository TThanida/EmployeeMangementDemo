package com.example.employeemanagement.dao.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; //set employee e-mail as username
    private String password;
    private String token;
    private Date tokenCreatedDate;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date modifiedDate;
    @Version
    private int version;
}
