package com.example.LibrarieSpring.entity;

public class ElementDeAdaugat {

    private  String nume;
    private  String titlu;
    private  String autor;


    public ElementDeAdaugat()
    {

    }

    public ElementDeAdaugat(String nume,String titlu,String autor)
    {
        this.nume=nume;
        this.autor=autor;
        this.titlu=titlu;
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

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
