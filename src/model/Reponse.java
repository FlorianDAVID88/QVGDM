package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reponse {
    private int numReponse;
    private String libelleReponse;
    private boolean repToF;

    public Reponse(int num, String libelle, boolean isTrue) {
        this.numReponse = num;
        this.libelleReponse = libelle;
        this.repToF = isTrue;
    }

    public Reponse(int numQ) throws SQLException {
        Connection con = new ConnectionDB("flodavid","#Apoal75","qvgdm_javafx").getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM reponse WHERE id_question = " + numQ + ";");

        this.numReponse = resultSet.getInt("id_reponse");
        this.libelleReponse = resultSet.getString("libelle_reponse");
        this.repToF = resultSet.getBoolean("true_or_false");
    }

    public int getNumReponse() {
        return this.numReponse;
    }
    public String getLibelleReponse() {
        return this.libelleReponse;
    }

    public boolean getRepIsToF() {
        return this.repToF;
    }


}
