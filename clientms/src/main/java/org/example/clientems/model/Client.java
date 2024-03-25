package org.example.clientems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends Person {

    private String password;

    private Boolean status;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}