package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Jeu {
    private int numJeu;
    private String libelleJeu;
    private List<Question> questions;
    private String[] sommesQuestions;

    private Joker[] jokers;
    private boolean isFinished;

    public Jeu() {
        this.numJeu = 1;
        this.libelleJeu = "Jeu";
        this.questions = List.of(new Question(1, "Laquelle de ces propositions désigne une variété de petit oignon", null,null,null, "C'est évident que c'est la cébette","C'est évident que c'est la cébette",null, null),
                                 new Question(2, "Si une téléspectatrice de « L'Amour est dans le Pré »\n veut rencontrer un agriculteur, elle doit …",null,null,null, "Logiquement en lui écrivant une lettre","Vous voulez qu'il fasse quoi d'autre à part écrire une lettre ?",null, null),
                                 new Question(3,"En voiture, votre passager peut être verbalisé quand il …",null,null,null, "La réponse, tu l'as déjà devant tes yeux","La réponse, tu l'as déjà devant tes yeux",null, null),
                                 new Question(4,"Complétez cette déclaration qui aurait été faite lors de\nla bataille de Fontenoy en 1745 : « Messieurs les anglais … »",null,null,null, null,null,null, null),
                                 new Question(5,"Selon la mythologie, c’est Aristide qui a aidé Thésée,\nenfermée dans le labyrinthe, à trouver …",null,null,null, null,null,null, null),
                                 new Question(6,"Sur la plage, un pavillon jaune orangé\nsignifie que la baignade est …",null,null,null, "Je suis quasiment sûr que la baignade est annoncée dangereuse dans ce cas","De mémoire, il me semble que c'est un drapeau jaune par manière de prévention, donc dangeruse.", null,"- Pavillon jaune pour une baignade dangereuse\n- Pavillon rouge pour une baignade interdite (voire violet si pollution)\n- Pavillon vert pour une baignade sans danger\n- Pavillon rouge et jaune pour une baignade non surveillée (seulement quelques heures de surveillance)"),
                                 new Question(7,"Dans le film « La nuit au musée », quel président américain\nest incarné par Robin Williams ?",null,"rep7.png",null, "Je m'en souviens ! C'est clairement Roosevelt","J'ai vu le film cent fois, et je suis sûr de voir Robin Williams interpréter Theodore Roosevelt sur son cheval.",null, null),
                                 new Question(8,"Quelle est la température de la partie la plus chaude de la flamme d'une bougie ?",null,null,null, "Bonne question ! Honnêtement, je verrais au moins 1 000 degrés. Après, pour 1 500, est-ce trop ou non, je ne sais pas.","Je verrais bien 1 000 degrés de sûr, mais 1 500 est tout à fait possible. J'hésite vraiment entre ces deux là !", null,"Au milieu de la flamme, la température atteint 1 500 degrés pour descendre à 700 degrés à son extrémité supérieure"),
                                 new Question(9,"Combien mesure l’angle d’un virage sur une piste\nde cyclisme sur piste aux Jeux Olympiques ?",null,null,null, "Franchement, à mes yeux, 22 degrés, c'est trop peu, et 62, c'est beaucoup trop. Après, entre les deux restants, j'hésite beaucoup.","En ayant déjà regardé les compétitions de ce sport, je verrais bien un demi-angle droit de pente. Donc ma première impression irait sur 42 degrés, mais je ne suis pas complètement sûr !",null, null),
                                 new Question(10,"Sur le tableau « La Rue Montorgueil » de Monet,\nque voit-on par dizaines sur les façades ?",null,"rep10.png",null, null,null,null, null),
                                 new Question(11,"Quelle glande du corps humain crée de la cortisone ?",null,null,null, null,null,null, null),
                                 new Question(12,"Quelle personnalité n’est jamais entrée à l’Académie française,\nbien qu’elle y aurait postulé 25 fois ?",null,null,null, null,null,null, null),
                                 new Question(13,"Quel jeu de société aurait été inventé par Palamède\npour divertir les Grecs durant le siège de Troie ?",null,null,null, null,null,null, null),
                                 new Question(14,"En 1921, sous quel pseudonyme Winston Churchill\na-t-il vendu six de ses toiles ?",null,null,null, null,null,null, null),
                                 new Question(15,"Dans un livre, qui dit que les animaux sont\n« le véritable test moral de l’humanité » ?",null,null,null, null,null,null, null));
        setRep();

        this.sommesQuestions = new String[]{"100 €","200 €","300 €","500 €","1 000 €",
                                            "2 000 €","4 000 €","8 000 €","12 000 €","24 000 €",
                                            "36 000 €","72 000 €","150 000 €","300 000 €",
                                            "1 MILLION €"};

        this.jokers = new Joker[]{new Joker(1, "50/50"),
                                new Joker(2, "Appel à un ami"),
                                new Joker(3, "Avis du public"),
                                new Joker(4, "Feeling")};

        this.isFinished = false;
    }

    public Jeu(int id) throws SQLException {
        Connection con = new ConnectionDB("flodavid","#Apoal75","qvgdm_javafx").getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM jeu WHERE id_jeu = " + id + ";");

        this.numJeu = id;
        this.libelleJeu = resultSet.getString("libelle_jeu");
    }

    public Question getQuestionNum(int n) {
        try {
            return this.questions.get(n-1);
        } catch (IndexOutOfBoundsException i) {
            System.out.println(i);
            return null;
        }
    }

    public String getSomme(int numQ) {
        return this.sommesQuestions[numQ-1];
    }

    public String getSommePalier(int numQuestion) {
        if(numQuestion <= 5)
            return "O €";
        else if (numQuestion <= 10)
            return this.sommesQuestions[4];
        else if (numQuestion <= 15)
            return this.sommesQuestions[9];
        else
            return this.sommesQuestions[14];
    }

    public Joker getJokerNum(int num) {
        return this.jokers[num];
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public void setFinishGame() {
        this.isFinished = true;
    }
    private void setRep() {
        List<Reponse> rep = List.of(new Reponse(1,"La cébette",true), new Reponse(2,"La cénulle",false), new Reponse(3,"La cétidio",false), new Reponse(4,"La cébien",false),
                                    new Reponse(5,"Faire du rodéo",false), new Reponse(6,"Se déguiser en lapin",false), new Reponse(7,"Lui écrire une lettre",true), new Reponse(8,"Être une peau de vache",false),
                                    new Reponse(9,"Dort pendent le trajet",false), new Reponse(10,"Vous critique trop",false), new Reponse(11,"N’a pas mis sa ceinture",true), new Reponse(12,"Chante du Lara Fabian",false),
                                    new Reponse(13,"Tirez les premiers",true), new Reponse(14,"Apprenez à cuisiner",false), new Reponse(15,"On va vous écosser",false), new Reponse(16,"Buvez donc votre thé",false),
                                    new Reponse(17,"La buvette",false), new Reponse(18,"La sortie",true), new Reponse(19,"Les toilettes",false), new Reponse(20,"Où est la clé",false),
                                    new Reponse(21,"Dangereuse",true), new Reponse(22,"Interdite",false), new Reponse(23,"Sans danger",false), new Reponse(24,"Non surveillée",false),
                                    new Reponse(25,"Dwight Eisenhower",false), new Reponse(26,"Theodore Roosevelt",true), new Reponse(27,"Abraham Lincoln",false), new Reponse(28,"George Washington",false),
                                    new Reponse(29,"Environ 400 degrés",false), new Reponse(30,"Environ 700 degrés",false), new Reponse(31,"Environ 1 000 degrés",false), new Reponse(32,"Environ 1 500 degrés",true),
                                    new Reponse(33,"22 degrés",false), new Reponse(34,"42 degrés",true), new Reponse(35,"52 degrés",false), new Reponse(36,"62 degrés",false),
                                    new Reponse(37,"Des lampions",false), new Reponse(38,"Des pots de fleurs",false), new Reponse(39,"Des drapeaux",true), new Reponse(40,"Des échafaudages",false),
                                    new Reponse(41,"La glande thyroïde",false), new Reponse(42,"La glande surrénale",true), new Reponse(43,"La glande lacrymale",false), new Reponse(44,"Le pancréas",false),
                                    new Reponse(45,"Jean de La Fontaine",false), new Reponse(46,"Marcel Pagnol",false), new Reponse(47,"Jean-Yves Cousteau",false), new Reponse(48,"Émile Zola",true),
                                    new Reponse(49,"Le morpion",false), new Reponse(50,"Les petits chevaux",false), new Reponse(51,"Le jeu de l’oie",false), new Reponse(52,"Le jeu d’échecs",true),
                                    new Reponse(53,"Jean Carzou",false), new Reponse(54,"Charles Morin",true), new Reponse(55,"James Pradier",false), new Reponse(56,"Michel Sima",false),
                                    new Reponse(57,"Milan Kundera",true), new Reponse(58,"G. Garcia Marquez",false), new Reponse(59,"Alexander Soljenitsyne",false), new Reponse(60,"Italo Calvino",false));

        for(int q = 0; q < questions.size(); q++) {
            questions.get(q).setListReponses(List.of(rep.get(4*q),rep.get(4*q+1),rep.get(4*q+2),rep.get(4*q+3)));
        }
    }
}
