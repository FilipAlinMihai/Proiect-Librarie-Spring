package com.example.LibrarieSpring.entity;

import javax.persistence.*;

@Entity
public class Colectie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nume;
    @ManyToOne
    @JoinColumn(name = "ID_Utilizator", referencedColumnName = "ID")
    private Utilizator utilizator;

    public Colectie()
    {

    }
    public Colectie(String nume)
    {
        this.nume=nume;

    }

    public Colectie(String nume,Utilizator utilizator)
    {
        this.nume=nume;
        this.utilizator=utilizator;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }
}
