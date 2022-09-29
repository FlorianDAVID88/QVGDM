package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Joker {
    protected int idJoker;
    protected String nameJoker;
    protected boolean jokerUsed;

    public Joker(int id, String name) {
        this.idJoker = id;
        this.nameJoker = name;
        this.jokerUsed = false;
    }

    public Joker(int id) throws SQLException {
        Connection con = new ConnectionDB("flodavid","#Apoal75","qvgdm_javafx").getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM joker WHERE id =" + id + ";");

        this.idJoker = id;
        this.nameJoker = resultSet.getString("libelle_joker");
        this.jokerUsed = resultSet.getBoolean("is_used");
    }

    public boolean isUsed() {
        return this.jokerUsed;
    }

    public String getNameJoker() {
        return this.nameJoker;
    }

    public void setUseJoker() {
        this.jokerUsed = true;
    }
}
