package com.team2.inventory.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty(message="Please provide your name")
    private String names;
    @Column (unique=true, nullable = false)
    @NotEmpty(message="Please provide your address")
    private String username;
    @Column(unique=true, nullable=false)
    private String email;
    @NotEmpty
    @Length(min = 8, message="Please provide a password with at least 8 characters")
    private String password;
    private Role role;

    public User() {
    }

    public User(String names,String email, String username, String password, Role role) {
        this.names = names;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id)
    {

        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", names='" + names + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", Role=" + role +
                '}';
    }
}
