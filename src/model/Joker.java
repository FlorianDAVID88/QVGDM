package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Joker {
    protected int idJoker;
    protected String nameJoker;
    protected boolean jokerUsed;

    /**
     * Initialise le Joker courant avec son id et son nom
     * @param id l'identifiant du joker
     * @param name son nom
     */
    public Joker(int id, String name) {
        this.idJoker = id;
        this.nameJoker = name;
        this.jokerUsed = false;
    }

    /**
     * Initialise le Joker courant avec son id et son nom à partir de l'id avec la base de données MySQL
     * @param id l'identifiant du joker
     * @throws SQLException s'il y a une erreur dans la base de données ou dans une requête SQL
     */
    public Joker(int id) throws SQLException {
        Connection con = new ConnectionDB("flodavid","#Apoal75","qvgdm_javafx").getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM joker WHERE id =" + id + ";");

        this.idJoker = id;
        this.nameJoker = resultSet.getString("libelle_joker");
        this.jokerUsed = resultSet.getBoolean("is_used");
    }

    /**
     * Retourne si le Joker donné a été déjà utilisé ou non
     * @return true s'il a été utilisé, false sinon
     */
    public boolean isUsed() {
        return this.jokerUsed;
    }

    /**
     * Retourne le nom du Joker donné
     * @return le contenu de la variable d'instance nameJoker de la classe Joker
     */
    public String getNameJoker() {
        return this.nameJoker;
    }

    /**
     * Le Joker donné vient d'être utilisé
     */
    public void setUseJoker() {
        this.jokerUsed = true;
    }
}
