package com.example.LibrarieSpring.entity;

import javax.persistence.*;

@Entity
public class Utilizator {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(unique=true)
    private String email;
    private String parola;

    public Utilizator()
    {

    }

    public Utilizator(String email,String parola)
    {
        this.parola=parola;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String toString()
    {
        return "Utilizatorul cu adresa "+email+" si parola "+parola;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
