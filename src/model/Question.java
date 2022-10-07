package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private static final int NREPONSES = 4;
    private int numQuestion;
    private String libelleQuestion;
    private String imageQu;
    private List<Reponse> reponses;
    private String imageRep;
    private String imageExplic;

    private String avisAppelAmi;
    private String feeling;
    private String imgAvisPublic;
    private String explications;

    public Question(int num, String libelle, String imageQu, String imageRep, String imExpl, String avisAmi, String feeling, String avisPublic, String explications) {
        this.numQuestion = num;
        this.libelleQuestion = libelle;
        this.imageQu = imageQu;
        this.imageRep = imageRep;
        this.imageExplic = imExpl;
        this.avisAppelAmi = avisAmi;
        this.feeling = feeling;
        this.imgAvisPublic = avisPublic;
        this.explications = explications;
        this.reponses = null;
    }

    public Question(int numQ) throws SQLException {
        Connection con = new ConnectionDB("flodavid","#Apoal75","qvgdm_javafx").getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM question WHERE id_question = " + numQ + ";");

        this.numQuestion = numQ;
        this.libelleQuestion = resultSet.getString("libelle_question");
        this.imageQu = resultSet.getString("image_qu");
        this.imageRep = resultSet.getString("image_rep");
        this.imageExplic = resultSet.getString("image_expl");
        this.avisAppelAmi = resultSet.getString("avis_appelAmi");
        this.feeling = resultSet.getString("avis_feeling");
        this.imgAvisPublic = resultSet.getString("img_avisPublic");
        this.explications = resultSet.getString("explications");

        this.reponses = new ArrayList<>(NREPONSES);
        for(int r = 0; r < NREPONSES; r++) {
            reponses.add(r,new Reponse(NREPONSES * (this.numQuestion - 1) + r));
        }
    }

    public String getLibelleQuestion() {
        return this.libelleQuestion;
    }

    public List<Reponse> getReponses() {
        return this.reponses;
    }

    public int getIdBonneRep() {
        for (int r = 0; r < reponses.size(); r++) {
            if (reponses.get(r).getRepIsToF())
                return r;
        }
        return 0;
    }

    public String getExplications() {
        return explications;
    }

    public String getAvisAppelAmi() {
        return avisAppelAmi;
    }

    public String getImageRep() {
        return imageRep;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setListReponses(List<Reponse> reponsesQ) {
        this.reponses = reponsesQ;
    }

}
