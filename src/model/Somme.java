package model;

import java.text.NumberFormat;

public class Somme {
    private int idSomme;
    private String sommeEuros;

    /**
     * Initialise la Somme courante à partir de son id et de
     * @param id l'identifiant de la Somme donnée
     * @param sommeE le contenu de la Somme donnée (qui sera transformée en String)
     */
    public Somme(int id, int sommeE) {
        this.idSomme = id;
        if(id == 15) {
            this.sommeEuros = "1 MILLION €";
        } else {
            this.sommeEuros = NumberFormat.getInstance().format(sommeE) + " €";
        }

    }

    /**
     * Retourne le contenu de la somme (en €) de la somme donnée
     * @return le contenu de la variable d'instance sommeEuros dans la classe Somme
     */
    public String getSomme() {
        return this.sommeEuros;
    }
}
