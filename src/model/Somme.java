package model;

import java.text.NumberFormat;

public class Somme {
    private int idSomme;
    private String somme;

    public Somme(int id, int somme) {
        this.idSomme = id;
        if(id == 15) {
            this.somme = "1 MILLION €";
        } else {
            this.somme = NumberFormat.getInstance().format(somme) + " €";
        }

    }

    public String getSomme() {
        return this.somme;
    }
}
