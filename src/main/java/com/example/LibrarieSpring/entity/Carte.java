package com.example.LibrarieSpring.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Carte.cautaDupaId", query = "SELECT p from Carte p where p.id = :id")
@NamedQuery(name = "Carte.stergeDupaId",query ="delete from Carte t where t.id = :id")
@NamedQuery(name = "Carte.cartiFinalizate",query ="select t from Carte t where t.nrcitite = t.nrpagini and t.utilizator= :utilizator")
public class Carte {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String titlu;
    private String autor;
    private int nrpagini;
    private int nrcitite;
    @ManyToOne
    @JoinColumn(name = "UTILIZATOR_ID", referencedColumnName = "ID")
    private Utilizator utilizator;


    public Carte()
    {

    }

    public Carte(String nume)
    {
        this.titlu =nume;
    }

    public Carte(String t,String a,int nr)
    {
        titlu=t;
        autor=a;
        nrpagini=nr;
        nrcitite=0;
    }

    public Carte(int citite,String t,String a)
    {
        titlu=t;
        autor=a;
        nrcitite=citite;
    }

    public Carte(String t,String a,int nr,Utilizator utilizator)
    {
        titlu=t;
        autor=a;
        nrpagini=nr;
        nrcitite=0;
        this.utilizator=utilizator;
    }

//    public Carte(String e,String t,String a,int nr)
//    {
//        email=e;
//        titlu=t;
//        autor=a;
//        nrpagini=nr;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNrpagini() {
        return nrpagini;
    }

    public void setNrpagini(int nrpagini) {
        this.nrpagini = nrpagini;
    }

    public int getNrcitite() {
        return nrcitite;
    }

    public void setNrcitite(int nrcitite) {
        this.nrcitite = nrcitite;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public String toString()
    {
        return "Cartea "+titlu+" scrisa de "+autor+" areun total de "+nrpagini+" pagini.";
    }

    public String afisareCarte()
    {
        return "Cartea "+titlu+" scrisa de "+autor+" areun total de "+nrpagini+" pagini. Seafla in colectia lui "+utilizator.toString();
    }

    public String getProcent()
    {
        String proc="";


        double pr=((double)this.nrcitite/this.nrpagini)*100;
        proc=String.format("%.3f",pr);
        proc=proc+"%";

        return proc;
    }
}
