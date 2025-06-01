package org.dataClasses;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "UserData")
public class UserData {
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

    public UserData(){
    }
    public UserData(String name, String email, int age){
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
    public void setAge(int age) {
        this.age = age;
    }
    public int getId() {return id;}

    @Override
    public String toString() {
        return "User: id=" + id + ", name=" + name + ", email=" + email + ", age=" + age;
    }
    public boolean equals (Object obj){
        if (obj == this) return true;
        if (!(obj instanceof UserData)) return false;
        return id == ((UserData)obj).id;
    }
}
