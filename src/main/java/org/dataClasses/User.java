package org.dataClasses;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "Age", nullable = false)
    private int age;

    @Column(name = "Registration_date", nullable = false)
    private LocalDate created_at;

    public User(){
    }
    public User (String name, String email, int age){
        this.name = name;
        this.email = email;
        this.age = age;
        created_at = LocalDate.now();
    }
    public LocalDate getRegistrationDate() {
        return created_at;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
}
