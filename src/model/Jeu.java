package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private static final int NQUESTIONS = 15;
    private static final int NJOKERS = 4;
    private int numJeu;
    private String libelleJeu;
    private List<Question> questions;
    private Somme[] sommesQuestions;

    private Joker[] jokers;
    private boolean isFinished;

    /**
     * Initialise le Jeu courant (utilisé en attendant
     * que la base de données fonctionne convenablement)
     */
    public Jeu() {
        this.numJeu = 1;
        this.libelleJeu = "Jeu";
        this.questions = List.of(new Question(1, "À que philosophe grec doit-on la citation :\n« Ce que je sais, c’est que je ne sais rien » ?", null, null, null, "SOCRATE !! Je vois que je sers à quelque chose !", "Socrate, évidemment !", null, null),
                                 new Question(2, "Quel personnage retrouve-t-on parmi les cartes\nd’un jeu de tarot ?", null, null, null, "Tu m'appelles pour ça ?? T'imagines la reine-claude, la Reine d'Angleterre ou la Reine des Neiges", "Reine de trèfle, forcément", null, null),
                                 new Question(3, "Sur le tableau « Le Déjeuner des canotiers »\nd’Auguste Renoir, les personnages …", null, null, null, "Ils sont attablés. Tu as vraiment cru qu'ils allaient faire un selfie en mangeant un burger, le tout en twerkant ??", "Ils sont attablés. Vous avez cru qu'ils allaient faire un selfie en twerkant et en mangeant un burger ??", null, null),
                                 new Question(4, "Quel nom porte le ragout épicé qui fait la réputation\nde la cuisine antillaise ?", null, null, null, "Colombo de poulet, enfin !!", "Colombo de poulet, enfin !!", null, null),
                                 new Question(5, "Qui rêve, depuis longtemps, de\n« devenir calife à la place du calife ?", null, null, null, "Tu connais pas Iznogoud (le film) ?", "Forcément, c'est Iznogoud !", null, null),
                                 new Question(6, "Lors de la cérémonie des César 2017,\nquel mythique acteur français reçut un vibrant hommage ?", "jpbq6.png", "jpbr6.png", null, "C'était le regretté Jean-Paul Belmondo", "Jean-Paul Belmondo. Magnifique souvenir, c'était un moment vibrant et émouvant", null, null),
                                 new Question(7, "Quel est le seul pays européen\noù il y a quasiment autant de vélos que d’habitants ?", null, null, null, "Les Pays-Bas à coup sûr !", "Je crois que c'est les Pays-Bas bien que j'avais vu que le Danemark était aussi un grand pays de vélo avec le grand départ du Tour de France 2022 à Copenhague.", null, "Aux Pays-Bas, 99,1 % des habitants ont un vélo et assurent 27 % de tous leurs déplacements en vélo. Un autre chiffre ? La distance moyenne parcourue par jour et par personne est de 2,5 km.\n - Au Danemark, 80,1 % de la population roule à vélo.\n - En Suède, 63,7% des habitants\n - En Suisse, 48,8% des habitants"),
                                 new Question(8, "Laquelle de ces répliques n’entend-on pas dans le film\n« Le Père Noël est une ordure » ?", null, null, "clavier_lbfds8.png", "Il me semble que « Mais bouffez le !!! », c'est dans « Les Bronzés font du ski »", "Il me semble que « Mais bouffez le !!! », c'est dans « Les Bronzés font du ski »", null, "« Mais bouffez le !!! » : fameuse réplique de Christian Clavier dans les « Les Bronzés font du ski » lorsque Copain, le cochon fait de l'anémie."),
                                 new Question(9, "Si vous êtes un ponot ou une ponote, vous habitez alors …", null, null, null, "Pontarlier et Privas, je suis sûr que non, et peut-être que Pont-à-Mousson, c'est un piège", "Pontarlier et Privas, pour y être déjà allé, c'est sûr que non, mais pour les deux restants, je ne sais pas !", null, "Les habitants du Puy-en Velay sont les ponot(e)s\nPour Pontarlier, ce sont les \nPour Pont-à-Mousson, ce sont les \nPour Privas, ce sont les "),
                                 new Question(10, "Quel département français compte le plus grand nombre de campings ?", null, null, null, "J'hésite sérieusement entre la Charente-Maritime et la Vendée", "S'il y avait eu les Pyrénées-Orientales, j'aurais dit ça ! Mais là, j'hésite vraiment !", null, "On compte pas moins de 355 campings en Vendée, 304 en Charente-Maritime, 273 en Ardèche et 254 dans le Finistère"),
                                 new Question(11, "Dans quel pays retrouve-t-on ce magnifique\nsapin de Noël ?", "sapin_italie11.png", null, null, "J'ai pas l'image sous les yeux donc je ne peux pas te dire !", "En regardant la photo, j'ai l'impression de voir un décor norvégien. Mais c'est juste une simple impression, je ne suis pas sûr du tout !", null, "Ce sapin est situé dans le village médiéval de Gubbio, à 230km au nord de Rome. Il mesure 750 m de haut et 450 m de large et est composé de près de 1000 ampoules reliés par 8 500 mètres de câbles électriques disposées à flanc de colline.\nIl est inscrit au Guinness des records comme étant plus grand arbre de Noël du monde."),
                                 new Question(12, "La vente de denrées par 12, comme les huîtres\nou les œufs, vient du fait que l’on comptait avec les …", null, null, null, "Je ne sais pas du tout !", "Bonne question ! J'en sais rien !", null, "C'est une tradition, depuis le Moyen Âge, qui viendrait du fait que les marchands comptaient avec leurs mains. Plus précisément, sur une main, ils utilisaient le pouce et comptaient les phalanges des quatre autres doigts, soit douze phalanges.\nÀ partir de là, la douzaine est devenue une unité de mesure."),
                                 new Question(13, "Lequel de ces empereurs byzantins a régné\nen dernier ?", null, null, null, "Je n'y connais rien là-dessus !", "Sympa d'attendre ce genre de question pour prendre mon joker ! Aucune idée !", null, " - Théophile a régné de 829 à 842\n - Staurakios a régné en 811 (durant un peu plus de 2 mois)\n - Christophe Lécapène a régné de 921 à 931\n - Léon VI le Sage a régné de 886 à 912"),
                                 new Question(14, "Dans quelle discipline sportive a-t-on eu\nle premier champion du monde ?", null, null, null, "Le cricket, ça parait facile. Peut-être le cyclisme ou la boxe, je ne sais pas !", "I dont know !", null, "Le boxeur John L. Sullivan (1858-1918) est considéré comme le premier champion du monde en 1882."),
                                 new Question(15, "Sur le drapeau actuel des Nations Unies, quel pays\nest représenté le plus près du sommet du drapeau ?", null, "nu15.png", null, "J'ai jamais vu le drapeau, je ne peux pas te dire !", "………………", null, null));

        setRep();

        this.sommesQuestions = new Somme[]{new Somme(1,100),new Somme(2,200),new Somme(3,300),
                                            new Somme(4,500), new Somme(5,1000), new Somme(6,2000),
                                            new Somme(7,4000), new Somme(8,8000), new Somme(9,12000),
                                            new Somme(10,24000), new Somme(11,36000), new Somme(12,72000),
                                            new Somme(13,150000),new Somme(14,300000), new Somme(15,1000000)};

        this.jokers = new Joker[]{new Joker(1, "50/50"),
                                new Joker(2, "Appel à un ami"),
                                new Joker(3, "Avis du public"),
                                new Joker(4, "Feeling")};

        this.isFinished = false;
    }

    /**
     * Initialise le Joker courant avec son id et son nom à partir de l'id avec la base de données MySQL
     * @param id l'identifiant du jeu
     * @throws SQLException s'il y a une erreur dans la base de données ou dans une requête SQL
     */
    public Jeu(int id) throws SQLException {
        Connection con = new ConnectionDB("flodavid","#Apoal75","qvgdm_javafx").getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM jeu WHERE id_jeu = " + id + ";");

        this.numJeu = id;
        this.libelleJeu = resultSet.getString("libelle_jeu");
        this.isFinished = false;

        this.questions = new ArrayList<>(NQUESTIONS);
        for(int q = 0; q < NQUESTIONS; q++) {
            this.questions.add(q,new Question(q+1));
        }

        this.sommesQuestions = new Somme[NQUESTIONS];
        for (int s = 0; s < NQUESTIONS; s++) {
            resultSet = stmt.executeQuery("SELECT contenu_somme FROM SOMME WHERE id_somme = " + (s+1) + ";");
            this.sommesQuestions[s+1] = new Somme(s+1,resultSet.getInt("contenu_somme"));
        }

        this.jokers = new Joker[NJOKERS];
        for (int j = 0; j < NJOKERS; j++) {
            resultSet = stmt.executeQuery("SELECT libelle_joker FROM JOKER WHERE id_somme = " + (j+1) + ";");
            this.jokers[j] = new Joker(j+1,resultSet.getString("libelle_joker"));
        }
    }

    /**
     * Retourne la question numéro n
     * @param n le numéro de la Question demandée
     * @return le libellé (String) de la question numéro n
     */
    public Question getQuestionNum(int n) {
        try {
            return this.questions.get(n-1);
        } catch (IndexOutOfBoundsException i) {
            System.out.println(i);
            return null;
        }
    }

    /**
     * Retourne la somme correspondante à la question numéro numQ
     * @param numQ le numéro de la Question demandée
     * @return la somme en jeu pour la question numQ
     */
    public String getSomme(int numQ) {
        return this.sommesQuestions[numQ-1].getSomme();
    }

    /**
     * Renvoie la somme gagnée lorsqu'on a perdu à la question numQuestion
     * (correspond soit à 0€ ou à un des deux premiers paliers du jeu)
     * @param numQuestion le numéro de la question où on a perdu
     * @return la somme remportée lorsqu'on a perdu à la question
     */
    public String getSommePalier(int numQuestion) {
        if(numQuestion <= 5)
            return "O €";
        else if (numQuestion <= 10)
            return this.sommesQuestions[4].getSomme();
        else
            return this.sommesQuestions[9].getSomme();
    }

    /**
     * Retourn le Joker numéro num dans le Jeu courant
     * @param num l'identifiant du Joker donné (entre 0 et 3)
     * @return le Joker N°num
     */
    public Joker getJokerNum(int num) {
        return this.jokers[num];
    }

    /**
     * Retourne si la partie est terminée ou non
     * @return true si la partie est terminée, false sinon
     */
    public boolean isFinished() {
        return this.isFinished;
    }

    /**
     * La partie se termine
     */
    public void setFinishGame() {
        this.isFinished = true;
    }

    /**
     * Ajoute les Réponses aux Questions données dans le constructeur
     * (utilisé en attendant que la base de données fonctionne convenablement)
     */
    private void setRep() {
        List<Reponse> rep = List.of(new Reponse(1,"Nikos Aliagas",false), new Reponse(2,"Nabilla",false), new Reponse(3,"Jean-Claude Van Damme",false), new Reponse(4,"Socrate",true),
                                    new Reponse(5,"La reine des Neiges",false), new Reponse(6,"La reine d'Angleterre",false), new Reponse(7,"La reine-claude",false), new Reponse(8,"La reine de trèfle",true),
                                    new Reponse(9,"Prennent un selfie",false), new Reponse(10,"Mangent un burger",false), new Reponse(11,"Dansent le twerk",false), new Reponse(12,"Sont attablés",true),
                                    new Reponse(13,"Colombo de poulet",true), new Reponse(14,"Derrick de poisson",false), new Reponse(15,"Starsky de veau",false), new Reponse(16,"Maigret de canard",false),
                                    new Reponse(17,"Fabien Roussel",false), new Reponse(18,"Jean-Luc Mélenchon",false), new Reponse(19,"Jean Lassalle",false), new Reponse(20,"Iznogoud",true),
                                    new Reponse(21,"Michel Galabru",false), new Reponse(22,"Claude Brasseur",false), new Reponse(23,"Jean-Paul Belmondo",true), new Reponse(24,"Jean Dujardin",false),
                                    new Reponse(25,"Le Danemark",false), new Reponse(26,"La Suisse",false), new Reponse(27,"La Suède",false), new Reponse(28,"Les Pays-Bas",true),
                                    new Reponse(29,"Ça se mange sans fin !",false), new Reponse(38,"Mais bouffez le !",true), new Reponse(39,"C’est tout la sécu ça !",false), new Reponse(40,"Non Pierre c’est un gilet !",false),
                                    new Reponse(33,"Pontarlier",false), new Reponse(34,"Pont-à-Mousson",false), new Reponse(35,"Privas",false), new Reponse(36,"Le Puy-en-Velay",true),
                                    new Reponse(37,"L’Ardèche",false), new Reponse(38,"La Charente-Maritime",false), new Reponse(39,"Le Finistère",false), new Reponse(40,"La Vendée",true),
                                    new Reponse(41,"En Italie",true), new Reponse(42,"En Finlande",false), new Reponse(43,"En Autriche",false), new Reponse(44,"En Norvège",false),
                                    new Reponse(45,"Dieux de l’Olympe",false), new Reponse(46,"Phalanges de 4 doigts",true), new Reponse(47,"Livres de la Bible",false), new Reponse(48,"Heures d’une horloge",false),
                                    new Reponse(49,"Théophile",false), new Reponse(50,"Staurakios",false), new Reponse(51,"Christophe Lécapène",true), new Reponse(52,"Léon VI le Sage",false),
                                    new Reponse(53,"Le cyclisme",false), new Reponse(54,"Le cricket",false), new Reponse(55,"La boxe",true), new Reponse(56,"Ski alpin",false),
                                    new Reponse(57,"La Nouvelle-Zélande",true), new Reponse(58,"Les États-Unis",false), new Reponse(59,"Le Chili",false), new Reponse(60,"L'Afrique du Sud",false));

        for(int q = 0; q < questions.size(); q++) {
            questions.get(q).setListReponses(List.of(rep.get(4*q),rep.get(4*q+1),rep.get(4*q+2),rep.get(4*q+3)));
        }
    }
}
