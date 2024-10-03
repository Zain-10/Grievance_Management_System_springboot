package org.example.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('USER','ASSIGNEE','SUPERVISOR') DEFAULT 'USER'")
    private Roles role = Roles.USER;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        role = Roles.USER;
    }

    public void setPassword(String password) {
        this.password=password;
    }

}
