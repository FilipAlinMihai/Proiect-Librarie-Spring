package com.example.LibrarieSpring.entity;

import com.example.LibrarieSpring.repository.ColectieRepository;

import javax.persistence.*;

@Entity
//@Table( uniqueConstraints={@UniqueConstraint( name = "idx_col2_col3",  columnNames ={"carte","colectie"})})
public class ElementColectie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "Carte_ID", referencedColumnName = "ID")
    private Carte carte;
    @ManyToOne
    @JoinColumn(name = "Colectie_ID", referencedColumnName = "ID")
    private Colectie colectie;

    public ElementColectie()
    {

    }

    public ElementColectie(Carte carte, Colectie colectie)
    {
        this.carte=carte;
        this.colectie=colectie;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public Colectie getColectie() {
        return colectie;
    }

    public void setColectie(Colectie colectie) {
        this.colectie = colectie;
    }
}
