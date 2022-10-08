CREATE TABLE CATEGORIE_QUESTION(
    id_categorie INT AUTO_INCREMENT NOT NULL,
    libelle_categorie VARCHAR(255),
    PRIMARY KEY(id_categorie)
);

CREATE TABLE SOMME(
    id_somme INT AUTO_INCREMENT NOT NULL,
    contenu_somme INT,
    PRIMARY KEY(id_somme)
);

CREATE TABLE JEU(
    id_jeu INT AUTO_INCREMENT NOT NULL,
    libelle_jeu VARCHAR(255),
    PRIMARY KEY(id_jeu)
);

CREATE TABLE QUESTION(
    id_question INT AUTO_INCREMENT NOT NULL,
    libelle_question VARCHAR(255),
    image_qu VARCHAR(255),
    image_expl VARCHAR(255),
    image_rep VARCHAR(255),
    avis_appelAmi VARCHAR(255),
    img_avisPublic VARCHAR(255),
    avis_feeling VARCHAR(255),
    explications VARCHAR(255),
    id_jeu INT NOT NULL,
    id_categorie INT NOT NULL,
    id_somme INT NOT NULL,
    PRIMARY KEY(id_question),
    CONSTRAINT fk_question_jeu
        FOREIGN KEY(id_jeu)
        REFERENCES JEU(id_jeu),

    CONSTRAINT fk_question_categorie
        FOREIGN KEY(id_categorie)
        REFERENCES CATEGORIE_QUESTION(id_categorie),

    CONSTRAINT fk_question_somme
        FOREIGN KEY(id_somme)
        REFERENCES SOMME(id_somme)
);

CREATE TABLE REPONSE(
    id_reponse INT AUTO_INCREMENT NOT NULL,
    libelle_reponse VARCHAR(255) NOT NULL,
    true_or_false BOOLEAN,
    id_question INT,
    PRIMARY KEY(id_reponse),
    CONSTRAINT fk_reponse_question
        FOREIGN KEY(id_question)
        REFERENCES QUESTION(id_question)
);

CREATE TABLE JOKER(
    id_joker INT AUTO_INCREMENT NOT NULL,
    libelle_joker VARCHAR(255),
    is_used BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id_joker)
);

CREATE TABLE comprend(
    id_jeu INT,
    id_joker INT,
    PRIMARY KEY(id_jeu, id_joker),
    CONSTRAINT fk_comprend_jeu
        FOREIGN KEY(id_jeu)
        REFERENCES JEU(id_jeu),

    CONSTRAINT fk_comprend_joker
        FOREIGN KEY(id_joker)
        REFERENCES JOKER(id_joker)
);

INSERT INTO CATEGORIE_QUESTION(libelle_categorie) VALUES
(NULL),('Sports'),('Littérature'),('Géographie'),('Histoire'),(),(),(),();

INSERT INTO SOMME(contenu_somme) VALUES
(100),(200),(300),(500),(1000),(2000),(4000),(8000),(12000),(24000),(36000),(72000),(150000),(300000),(1000000);

INSERT INTO JEU(libelle_jeu) VALUES
(),(),(),(),(),();

INSERT INTO QUESTION(libelle_question, image_qu, image_expl, image_rep, avis_appelAmi, img_avisPublic, avis_feeling, explications, id_categorie, id_somme) VALUES
('Laquelle de ces propositions désigne une variété de petit oignon ?', NULL, NULL, NULL, 'C’est évident que c’est la cébette', 'graph1.png','C’est évident que c’est la cébette', NULL, 1, 1),

('Si une téléspectatrice de « L’Amour est dans le Pré »\n veut rencontrer un agriculteur, elle doit …', NULL, NULL, NULL, 'Logiquement en lui écrivant une lettre', 'graph1.png', 'Vous voulez qu’il fasse quoi d’autre à part écrire une lettre ?', NULL, 1, 2),

('En voiture, votre passager peut être verbalisé quand il …', NULL, NULL, NULL, 'La réponse, tu l’as déjà devant tes yeux', 'graph3.png', 'La réponse, tu l’as déjà devant tes yeux', NULL, 1, 3),

    #new Question(5,"Selon la mythologie, c’est Aristide qui a aidé Thésée,\nenfermée dans le labyrinthe, à trouver …",null,null,null, null,null,null, null),
    #new Question(6,"Sur la plage, un pavillon jaune orangé\nsignifie que la baignade est …",null,null,null, "Je suis quasiment sûr que la baignade est annoncée dangereuse dans ce cas","De mémoire, il me semble que c'est un drapeau jaune par manière de prévention, donc dangeruse.", null,"- Pavillon jaune pour une baignade dangereuse\n- Pavillon rouge pour une baignade interdite (voire violet si pollution)\n- Pavillon vert pour une baignade sans danger\n- Pavillon rouge et jaune pour une baignade non surveillée (seulement quelques heures de surveillance)"),
    #new Question(7,"Dans le film « La nuit au musée », quel président américain\nest incarné par Robin Williams ?",null,"rep7.png",null, "Je m'en souviens ! C'est clairement Roosevelt","J'ai vu le film cent fois, et je suis sûr de voir Robin Williams interpréter Theodore Roosevelt sur son cheval.",null, null),
    #new Question(8,"Quelle est la température de la partie la plus chaude de la flamme d'une bougie ?",null,null,null, "Bonne question ! Honnêtement, je verrais au moins 1 000 degrés. Après, pour 1 500, est-ce trop ou non, je ne sais pas.","Je verrais bien 1 000 degrés de sûr, mais 1 500 est tout à fait possible. J'hésite vraiment entre ces deux là !", null,"Au milieu de la flamme, la température atteint 1 500 degrés pour descendre à 700 degrés à son extrémité supérieure"),
    #new Question(9,"Combien mesure l’angle d’un virage sur une piste\nde cyclisme sur piste aux Jeux Olympiques ?",null,null,null, "Franchement, à mes yeux, 22 degrés, c'est trop peu, et 62, c'est beaucoup trop. Après, entre les deux restants, j'hésite beaucoup.","En ayant déjà regardé les compétitions de ce sport, je verrais bien un demi-angle droit de pente. Donc ma première impression irait sur 42 degrés, mais je ne suis pas complètement sûr !",null, null),
    #new Question(10,"Sur le tableau « La Rue Montorgueil » de Monet,\nque voit-on par dizaines sur les façades ?",null,"rep10.png",null, null,null,null, null),
    #new Question(11,"Quelle glande du corps humain crée de la cortisone ?",null,null,null, null,null,null, null),
    #new Question(12,"Quelle personnalité n’est jamais entrée à l’Académie française,\nbien qu’elle y aurait postulé 25 fois ?",null,null,null, null,null,null, null),
    #new Question(13,"Quel jeu de société aurait été inventé par Palamède\npour divertir les Grecs durant le siège de Troie ?",null,null,null, null,null,null, null),
    #new Question(14,"En 1921, sous quel pseudonyme Winston Churchill\na-t-il vendu six de ses toiles ?",null,null,null, null,null,null, null),
    #new Question(15,"Dans un livre, qui dit que les animaux sont\n« le véritable test moral de l’humanité » ?",null,null,null, null,null,null, null));
('Complétez cette déclaration qui aurait été faite lors de\nla bataille de Fontenoy en 1745 : « Messieurs les anglais … »', NULL, NULL, NULL, 'La réponse est logique : "Tirez les premiers !"', 'graph4.png', 'La réponse est logique : "Tirez les premiers !', NULL, 1, 4),

(),

(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
();

INSERT INTO REPONSE(libelle_reponse, true_or_false, id_question) VALUES
#new Reponse(1,"La cébette",true), #new Reponse(2,"La cénulle",false), #new Reponse(3,"La cétidio",false), #new Reponse(4,"La cébien",false),
#new Reponse(5,"Faire du rodéo",false), #new Reponse(6,"Se déguiser en lapin",false), #new Reponse(7,"Lui écrire une lettre",true), #new Reponse(8,"Être une peau de vache",false),
#new Reponse(9,"Dort pendent le trajet",false), #new Reponse(10,"Vous critique trop",false), #new Reponse(11,"N’a pas mis sa ceinture",true), #new Reponse(12,"Chante du Lara Fabian",false),
#new Reponse(13,"Tirez les premiers",true), #new Reponse(14,"Apprenez à cuisiner",false), #new Reponse(15,"On va vous écosser",false), #new Reponse(16,"Buvez donc votre thé",false),
#new Reponse(17,"La buvette",false), #new Reponse(18,"La sortie",true), #new Reponse(19,"Les toilettes",false), #new Reponse(20,"Où est la clé",false),
#new Reponse(21,"Dangereuse",true), #new Reponse(22,"Interdite",false), #new Reponse(23,"Sans danger",false), #new Reponse(24,"Non surveillée",false),
#new Reponse(25,"Dwight Eisenhower",false), #new Reponse(26,"Theodore Roosevelt",true), #new Reponse(27,"Abraham Lincoln",false), #new Reponse(28,"George Washington",false),
#new Reponse(29,"Environ 400 degrés",false), #new Reponse(30,"Environ 700 degrés",false), #new Reponse(31,"Environ 1 000 degrés",false), #new Reponse(32,"Environ 1 500 degrés",true),
#new Reponse(33,"22 degrés",false), #new Reponse(34,"42 degrés",true), #new Reponse(35,"52 degrés",false), #new Reponse(36,"62 degrés",false),
#new Reponse(37,"Des lampions",false), #new Reponse(38,"Des pots de fleurs",false), #new Reponse(39,"Des drapeaux",true), #new Reponse(40,"Des échafaudages",false),
#new Reponse(41,"La glande thyroïde",false), #new Reponse(42,"La glande surrénale",true), #new Reponse(43,"La glande lacrymale",false), #new Reponse(44,"Le pancréas",false),
#new Reponse(45,"Jean de La Fontaine",false), #new Reponse(46,"Marcel Pagnol",false), #new Reponse(47,"Jacques-Yves Cousteau",false), #new Reponse(48,"Émile Zola",true),
#new Reponse(49,"Le morpion",false), #new Reponse(50,"Les petits chevaux",false), #new Reponse(51,"Le jeu de l’oie",false), #new Reponse(52,"Le jeu d’échecs",true),
#new Reponse(53,"Jean Carzou",false), #new Reponse(54,"Charles Morin",true), #new Reponse(55,"James Pradier",false), #new Reponse(56,"Michel Sima",false),
#new Reponse(57,"Milan Kundera",true), #new Reponse(58,"G. Garcia Marquez",false), #new Reponse(59,"Alexander Soljenitsyne",false), #new Reponse(60,"Italo Calvino",false));

(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
(),
();

INSERT INTO JOKER(libelle_joker) VALUES ('50/5O'),('Appel à un ami'),('Avis du public'),('Feeling');