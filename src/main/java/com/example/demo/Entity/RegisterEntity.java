package com.example.demo.Entity;

import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customer")
public class RegisterEntity {
    @Id
    private String username;
    private String accno;
    private long balance;

    public long getBalance() {
        return balance;
    }

    public void assignBal(long balance) {
        this.balance = balance;
    }


    public String getAccno() {
        return accno;
    }


    public void assignAcc(String accno) {
        this.accno = accno;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    private String email;
    private String password;
}
