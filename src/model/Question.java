package model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Question {
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
