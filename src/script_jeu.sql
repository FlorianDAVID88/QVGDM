CREATE TABLE CATEGORIE_QUESTION(
    id_categorie INT AUTO_INCREMENT NOT NULL,
    libelle_categorie VARCHAR(255),
    PRIMARY KEY(id_categorie)
);

CREATE TABLE JEU(
    id_jeu INT AUTO_INCREMENT NOT NULL,
    libelle_jeu VARCHAR(255),
    PRIMARY KEY(id_jeu)
);

CREATE TABLE QUESTION(
    id_question INT AUTO_INCREMENT NOT NULL,
    libelle_question VARCHAR(255),
    somme VARCHAR(255),
    image_qu VARCHAR(255),
    image_expl VARCHAR(255),
    image_rep VARCHAR(255),
    avis_appelAmi VARCHAR(255),
    img_avisPublic VARCHAR(255),
    avis_feeling VARCHAR(255),
    explications VARCHAR(255),
    id_jeu INT NOT NULL,
    id_categorie INT NOT NULL,
    PRIMARY KEY(id_question),
    CONSTRAINT fk_question_jeu
        FOREIGN KEY(id_jeu)
        REFERENCES JEU(id_jeu),

    CONSTRAINT fk_question_categorie
        FOREIGN KEY(id_categorie)
        REFERENCES CATEGORIE_QUESTION(id_categorie)
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
(NULL),(),(),(),(),(),(),(),();

INSERT INTO JEU(libelle_jeu) VALUES
(),(),(),(),(),();

INSERT INTO QUESTION(libelle_question, somme, image_qu, image_expl, image_rep, avis_appelAmi, img_avisPublic, avis_feeling, explications, id_jeu, id_categorie) VALUES
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