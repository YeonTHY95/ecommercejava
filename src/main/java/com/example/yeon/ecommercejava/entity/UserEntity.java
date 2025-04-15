package com.example.yeon.ecommercejava.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id ;

    @Column(unique=true)
    private String username ;

    private String password ;
    private Integer age ;
    private String sex ;
    private String phoneNumber ;
    private String role ;
    private LocalDateTime date_joined = LocalDateTime.now() ;

//    public UserEntity(Long id, String username, String password, Integer age, String sex, String phoneNumber, String role, Date date_joined) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.age = age;
//        this.sex = sex;
//        this.phoneNumber = phoneNumber;
//        this.role = role;
//        this.date_joined = date_joined;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(LocalDateTime date_joined) {
        this.date_joined = date_joined;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", date_joined=" + date_joined +
                '}';
    }
}
