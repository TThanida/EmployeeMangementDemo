package com.example.employeemanagement.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String nickName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String telephone;
    @Type(type = "text")
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private Integer postCode;
    @Column(nullable = false)
    private Date hiredDate;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date modifiedDate;
    @Version
    private int version;
}
