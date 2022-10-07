package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reponse {
    private int numReponse;
    private String libelleReponse;
    private boolean repToF;

    /**
     * Initialise la Reponse courante avec son id, son libellé et s'il est bonne ou mauvaise
     * @param num l'identifiant de la Reponse donnée
     * @param libelle le libellé de la Reponse donnée
     * @param isTrue true si la Reponse donnée est juste, false sinon
     */
    public Reponse(int num, String libelle, boolean isTrue) {
        this.numReponse = num;
        this.libelleReponse = libelle;
        this.repToF = isTrue;
    }

    /**
     * Initialise la Reponse courante à partir de la question numQ correspondante avec la base de données MySQL
     * @param numQ le numéro de la Question correspondante
     * @throws SQLException s'il y a une erreur dans la base de données ou dans une requête SQL
     */
    public Reponse(int numQ) throws SQLException {
        Connection con = new ConnectionDB("flodavid","#Apoal75","qvgdm_javafx").getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM reponse WHERE id_question = " + numQ + ";");

        this.numReponse = resultSet.getInt("id_reponse");
        this.libelleReponse = resultSet.getString("libelle_reponse");
        this.repToF = resultSet.getBoolean("true_or_false");
    }

    /**
     * Renvoie l'identifiant de la Reponse donnée
     * @return le contenu de la variable d'instance numReponse de la classe Reponse
     */
    public int getNumReponse() {
        return this.numReponse;
    }

    /**
     * Renvoie le contenu de la Reponse donnée
     * @return le contenu de la variable d'instance libelleReponse de la classe Reponse
     */
    public String getLibelleReponse() {
        return this.libelleReponse;
    }

    /**
     * Renvoie si la Reponse donnée est juste ou non
     * @return true si c'est une bonne réponse, false sinon
     */
    public boolean getRepIsToF() {
        return this.repToF;
    }


}
