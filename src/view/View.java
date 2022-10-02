package view;

import controllers.ControllerButton;
import controllers.ControllerJoker;
import controllers.ControllerMouse;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class View extends Stage {
    private static final int NQUESTIONS = 15;
    private static final int NJOKERS = 4;
    private static final double COEFF_AGR = 1.7075;
    private int numQ;
    private List<String> reps;
    private Button[] bTextReps = {null, null, null, null};
    private GridPane gpArret, gpLQu, gpSomme, gpImgRep;

    private AudioPlay audioPyramide, audioStartQ, audioValid, audioReveal;
    private Button bInit, bStart, bRepValid, bExplications, bValidPensee;
    private Model model;
    private int width, height;
    private Pane rootPane;

    private ImageView imageQR;
    private ImageView[] imgJokers = {null,null,null,null}, jokersUsed = {new ImageView("images/jokerUtilise.png"),
                                                                        new ImageView("images/jokerUtilise.png"),
                                                                        new ImageView("images/jokerUtilise.png"),
                                                                        new ImageView("images/jokerUtilise.png")};

    public View(Model model) {
        this.model = model;
        this.numQ = 1;

        this.width = model.getWidth();
        this.height = model.getHeight();
        this.rootPane = new Pane();
        this.rootPane.setPrefSize(width,height);

        initGame();
    }

    /**
     * Affiche la page principale du jeu
     */
    public void initGame() {
        FlowPane fpInit = new FlowPane();

        ImageView imgInit = new ImageView("images/logoQVGDM.png");
        imgInit.setFitHeight(height*(2.0/3.0));
        imgInit.setFitWidth(height*(2.0/3.0));

        bInit = new Button("Jouer une partie");
        bInit.setFont(new Font("Copperplate Gothic Bold",24));
        bInit.setStyle("-fx-background-color: orange;");
        fpInit.getChildren().addAll(imgInit,bInit);
        fpInit.setTranslateX(width/2.0 - 256);
        fpInit.setAlignment(Pos.CENTER);
        fpInit.setHgap(20);

        rootPane.getChildren().add(fpInit);
        bInit.setOnAction(new ControllerButton(model,this));
    }

    public void showPyramideGains() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        rootPane.getChildren().clear();
        Label[] labelSomme = new Label[NQUESTIONS];
        Label[] labelNumQ = new Label[NQUESTIONS];
        ImageView[] losangeGain = new ImageView[NQUESTIONS];

        VBox vbNumQ = new VBox();
        VBox vbSomme = new VBox();

        for(int g = NQUESTIONS - 1; g >= 0; g--) {
            labelSomme[g] = new Label(model.getJeu().getSomme(g+1));
            labelNumQ[g] = new Label("" + (g+1));

            labelSomme[g].setFont(new Font("Copperplate Gothic Bold", 40));
            labelNumQ[g].setFont(new Font("Copperplate Gothic Bold", 40));

            if((g+1)%5 == 0) {
                labelSomme[g].setStyle("-fx-text-fill: white;");
                labelNumQ[g].setStyle("-fx-text-fill: white;");
            } else {
                labelSomme[g].setStyle("-fx-text-fill: orange;");
                labelNumQ[g].setStyle("-fx-text-fill: orange;");
            }

            losangeGain[g] = new ImageView("images/los.png");
            losangeGain[g].setTranslateX(575 * COEFF_AGR);
            losangeGain[g].setTranslateY(((30 * g) + 10) * COEFF_AGR);
            losangeGain[g].setFitWidth(19 * COEFF_AGR);
            losangeGain[g].setFitHeight(10 * COEFF_AGR);
            losangeGain[g].setVisible(false);

            vbNumQ.getChildren().add(labelNumQ[g]);
            vbSomme.getChildren().add(labelSomme[g]);
            rootPane.getChildren().add(losangeGain[g]);
        }

        bStart = new Button("Commencer la partie");
        bStart.setOnAction(new ControllerButton(model,this));
        bStart.setFont(new Font("Copperplate Gothic Bold",24));
        bStart.setStyle("-fx-background-color: orange;");
        bStart.setTranslateX(350 * COEFF_AGR);
        bStart.setTranslateY(350 * COEFF_AGR);
        bStart.setOnAction(new ControllerButton(model,this));

        vbSomme.setTranslateX(600 * COEFF_AGR);
        vbNumQ.setTranslateX(525 * COEFF_AGR);
        vbNumQ.setAlignment(Pos.CENTER_RIGHT);
        vbSomme.setAlignment(Pos.CENTER_RIGHT);

        GridPane gpPyr = new GridPane();
        gpPyr.addColumn(0,vbNumQ);
        gpPyr.addColumn(1,vbSomme);
        GridPane.setValignment(vbNumQ, VPos.CENTER);
        GridPane.setValignment(vbSomme, VPos.CENTER);

        ImageView imgGain = new ImageView("images/CaseOrangePyr.png");
        imgGain.setTranslateX(500 * COEFF_AGR);
        imgGain.setTranslateY(420 * COEFF_AGR);
        imgGain.setFitWidth(300 * COEFF_AGR);
        imgGain.setFitHeight(30 * COEFF_AGR);
        audioPyramide = new AudioPlay("C:/Users/fdavid5/Desktop/javafxQVGDM/src/jingles/pyrGains.wav");

        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.J) {
                AtomicInteger i = new AtomicInteger(0);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(actionEvent -> {
                    try {
                        int nextI = i.getAndIncrement();
                        new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/jokers/bruitJoker" + (nextI + 1) + ".wav");
                        ImageView imgJok = new ImageView("images/joker" + (nextI + 1) + ".png");
                        imgJok.setFitWidth(100 * COEFF_AGR);
                        imgJok.setFitHeight(64 * COEFF_AGR);
                        imgJok.setTranslateX((double)nextI * imgJok.getFitWidth() + 2.0);
                        rootPane.getChildren().add(imgJok);
                    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                );
                SequentialTransition timer = new SequentialTransition(pause);
                timer.setCycleCount(4);
                timer.play();
            }
        });

        rootPane.getChildren().addAll(imgGain,gpPyr,bStart);
        setRootPane(rootPane);
    }

    public void beforeQuestion(int numQ) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.numQ = numQ;
        audioPyramide.stop();
        rootPane.getChildren().clear();

        if(numQ == 1)
            new AudioPlay("C:/Users/fdavid5/Desktop/javafxQVGDM/src/jingles/bruitRun.wav").play();

        if(gpImgRep != null)
            rootPane.getChildren().add(gpImgRep);

        Button bPlayQ = new Button();
        rootPane.getChildren().add(bPlayQ);
        bPlayQ.addEventHandler(ActionEvent.ACTION, actionEvent -> {
            if(actionEvent.getSource() == bPlayQ) {
                try {
                    rootPane.getChildren().remove(bPlayQ);
                    playQuestion(numQ);
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setRootPane(rootPane);
    }

    public void playQuestion(int numQ) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        if(numQ >= 5 && audioReveal != null)
            audioReveal.stop();

        rootPane.getChildren().clear();
        afficheQuestion(numQ);
        for (Button bTextRep : bTextReps) bTextRep.setOnAction(new ControllerButton(model, this));

        if(numQ%5 != 1) {
            Label lStopGame = new Label("Arrêt du jeu");
            lStopGame.setStyle("-fx-text-fill: orange; -fx-underline: simple;");
            lStopGame.setFont(new Font("Copperplate Gothic Bold",44));

            gpArret = new GridPane();
            gpArret.getChildren().add(lStopGame);
            gpArret.setAlignment(Pos.CENTER_RIGHT);
            gpArret.setMinSize(rootPane.getWidth(),30 * COEFF_AGR);
            rootPane.getChildren().add(gpArret);

            gpArret.setOnMouseClicked(new ControllerMouse(model,this));
        }
        afficheJokers();
        setRootPane(rootPane);

        if(numQ == 1) {
            audioStartQ = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/question/Qu1-2345.wav");
        } else if(numQ > 5){
            audioStartQ = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/question/Qu" + numQ + ".wav");
        }
    }

    public void afficheJokers() {
        for(int i = 0; i < NJOKERS; i++) {
            imgJokers[i] = new ImageView("images/joker" + (i+1) + ".png");
            imgJokers[i].setFitWidth(120);
            imgJokers[i].setFitHeight(77.5);
            imgJokers[i].setTranslateX((double)i * imgJokers[i].getFitWidth() + 2.0);
            rootPane.getChildren().add(imgJokers[i]);

            jokersUsed[i].setFitWidth(120);
            jokersUsed[i].setFitHeight(77.5);
            jokersUsed[i].setTranslateX((double)i * imgJokers[i].getFitWidth() + 2.0);
            rootPane.getChildren().add(jokersUsed[i]);
            jokersUsed[i].setVisible(true);

            if(model.getJeu().getJokerNum(i).isUsed()) {
                Tooltip.install(jokersUsed[i], new Tooltip(model.getJeu().getJokerNum(i).getNameJoker() + " (utilisé)"));
            } else {
                jokersUsed[i].setVisible(false);
                imgJokers[i].setOnMouseClicked(new ControllerJoker(model,this));
                Tooltip.install(imgJokers[i], new Tooltip(model.getJeu().getJokerNum(i).getNameJoker()));
            }

            FadeTransition ftJokers = new FadeTransition(Duration.seconds(2),imgJokers[i]);
            ftJokers.setFromValue(0);
            ftJokers.setToValue(1);
            ftJokers.play();

            FadeTransition ftIsUsed = new FadeTransition(Duration.seconds(2),jokersUsed[i]);
            ftIsUsed.setFromValue(0);
            ftIsUsed.setToValue(1);
            ftIsUsed.play();

        }
    }

    public void afficheQuestion(int numQ) {
        Label lQu = new Label(model.getJeu().getQuestionNum(numQ).getLibelleQuestion());

        reps = List.of(model.getJeu().getQuestionNum(numQ).getReponses().get(0).getLibelleReponse(),
                model.getJeu().getQuestionNum(numQ).getReponses().get(1).getLibelleReponse(),
                model.getJeu().getQuestionNum(numQ).getReponses().get(2).getLibelleReponse(),
                model.getJeu().getQuestionNum(numQ).getReponses().get(3).getLibelleReponse());

        imageQR = new ImageView("/images/Questions_reponses.png");
        imageQR.setFitWidth(rootPane.getWidth());
        imageQR.setFitHeight(319);
        imageQR.setTranslateY(380);
        rootPane.getChildren().add(imageQR);

        lQu.setStyle("-fx-text-fill: white; -fx-font-size: 34px; -fx-text-alignment: center;");
        lQu.setTranslateY(380);

        gpLQu = new GridPane();
        gpLQu.getChildren().add(lQu);
        gpLQu.setAlignment(Pos.CENTER);
        gpLQu.setMinSize(rootPane.getWidth(),136.5);
        rootPane.getChildren().add(gpLQu);


        char[] idR = {'A','B','C','D'};
        for(int i = 0; i < reps.size(); i++) {
            bTextReps[i] = new Button("⬪ " + idR[i] + " : " + reps.get(i));
            bTextReps[i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: white; -fx-font-size: 34px;");
            bTextReps[i].setTranslateX(85 + 632 * (i % 2));
            if (i < reps.size() / 2)
                bTextReps[i].setTranslateY(531);
            else
                bTextReps[i].setTranslateY(621);

            if(!model.getJeu().isFinished())
                gpLQu.setOnMouseClicked(new ControllerMouse(model, this));
            else
                rootPane.getChildren().add(bTextReps[i]);
            setRootPane(rootPane);
        }
    }

    public void validReponse(int numQ, int idRep) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(numQ > 5) {
            audioStartQ.stop();
            audioValid = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/dernierMot/DM" + numQ + ".wav");
        }

        if(numQ%5 != 1)
            gpArret.setVisible(false);

        for (int i = 0; i < bTextReps.length; i++) {
            bTextReps[i].setOnAction(null);
            imgJokers[i].setVisible(false);
            jokersUsed[i].setVisible(false);
        }

        ImageView imageValidR = new ImageView("images/CaseOrange.png");
        imageValidR.setFitWidth(604.5);//354
        imageValidR.setFitHeight(80);
        imageValidR.setTranslateX(64.5 + 632*(idRep%2));

        bRepValid = new Button(bTextReps[idRep].getText());
        bRepValid.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 34px; -fx-text-fill: black;");
        bRepValid.setTranslateX(85 + 632 * (idRep % 2));

        if(idRep < 2) {
            imageValidR.setTranslateY(531);
            bRepValid.setTranslateY(531);
        } else {
            imageValidR.setTranslateY(621);
            bRepValid.setTranslateY(621);
        }

        rootPane.getChildren().addAll(imageValidR,bRepValid);
        setRootPane(rootPane);

        bRepValid.addEventHandler(ActionEvent.ACTION, actionEvent -> {
            try {
                boolean bool = revealReponse(numQ, idRep);
                if(!bool) {
                    model.getJeu().setFinishGame();
                }
            } catch (InterruptedException | UnsupportedAudioFileException |
                     LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void afficheReponse(int idBonneRep) {
        ImageView imageBonneRep = new ImageView("images/CaseVerte.png");
        imageBonneRep.setFitWidth(604.5);
        imageBonneRep.setFitHeight(80);
        imageBonneRep.setTranslateX(64.5 + 632*(idBonneRep%2));

        Label lBonneRep = new Label(bTextReps[idBonneRep].getText());
        lBonneRep.setStyle("-fx-font-size: 34px; -fx-text-fill: black;");
        lBonneRep.setTranslateX(109.2 + 632*(idBonneRep%2));

        if(idBonneRep < 2) {
            imageBonneRep.setTranslateY(531);
            lBonneRep.setTranslateY(543);
        } else {
            imageBonneRep.setTranslateY(621);
            lBonneRep.setTranslateY(634);
        }

        rootPane.getChildren().addAll(imageBonneRep,lBonneRep);
        setRootPane(rootPane);
    }

    public boolean revealReponse(int numQ, int idR) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        int idBonneRep = model.getJeu().getQuestionNum(numQ).getIdBonneRep();
        ControllerButton newCB = new ControllerButton(model, this);
        EventHandler<ActionEvent> event = actionEvent -> {
            try {
                if (numQ > 5)
                    audioValid.stop();
                else if (numQ == 5)
                    audioStartQ.stop();

                afficheReponse(idBonneRep);

                if (model.getJeu().getQuestionNum(numQ).getExplications() != null) {
                    bExplications = new Button("Explications");
                } else {
                    if (model.getJeu().isFinished() || getNumActualQ() == NQUESTIONS)
                        bExplications = new Button("Terminer la partie");
                    else
                        bExplications = new Button("Question suivante");
                }

                bExplications.setTranslateX(597.5);
                bExplications.setTranslateY(384);
                bExplications.setStyle("-fx-background-color: purple; -fx-text-fill: white; -fx-border-color: white;");
                rootPane.getChildren().add(bExplications);
                bExplications.setOnAction(newCB);

                if (idR == idBonneRep) {
                    audioReveal = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/gagne/Gagne" + numQ + ".wav");
                    System.out.println("Gagné : " + model.getJeu().getSomme(numQ));
                } else {
                    if (numQ <= 5)
                        audioStartQ.stop();
                    audioReveal = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/perdu/Perdu" + numQ + ".wav");
                    model.getJeu().setFinishGame();
                    System.out.println("Perdu : " + model.getJeu().getSommePalier(numQ));
                }
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        };

        PauseTransition pauseImg;
        if(model.getJeu().getQuestionNum(numQ).getImageRep() != null) {
            ImageView imgRep = new ImageView("/model/imagesRep/" + model.getJeu().getQuestionNum(numQ).getImageRep());
            imgRep.setFitHeight(300);
            imgRep.setFitWidth(300);

            gpImgRep = new GridPane();
            gpImgRep.getChildren().add(imgRep);
            gpImgRep.setTranslateX((width - imgRep.getFitWidth())/2.0);
            gpImgRep.setTranslateY((imageQR.getTranslateY() - imgRep.getFitHeight())/2.0);
            rootPane.getChildren().add(gpImgRep);

            FadeTransition fadeImg = new FadeTransition(Duration.seconds(2),imgRep);
            fadeImg.setFromValue(0.0);
            fadeImg.setToValue(1.0);
            fadeImg.play();

            pauseImg = new PauseTransition(Duration.seconds(2));
        } else {
            gpImgRep = null;
            pauseImg = new PauseTransition(Duration.millis(50));
        }
        pauseImg.setOnFinished(event);
        new SequentialTransition(pauseImg).play();
        return idR == idBonneRep;
    }

    public void utiliserJoker(int numQ, int idJ) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int idBonneRep = model.getJeu().getQuestionNum(numQ).getIdBonneRep();
        switch (idJ) {
            case 0 :
                int i1 = new Random(Calendar.getInstance().getTimeInMillis()).nextInt(bTextReps.length);
                int i2 = new Random(Calendar.getInstance().getTimeInMillis()).nextInt(bTextReps.length);

                while (i1 == idBonneRep || i2 == idBonneRep || i1 == i2) {
                    i1 = new Random(Calendar.getInstance().getTimeInMillis()).nextInt(bTextReps.length);
                    i2 = new Random(Calendar.getInstance().getTimeInMillis()).nextInt(bTextReps.length);
                }

                AudioPlay joker5050 = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/jokers/50_50.wav");
                joker5050.play();
                bTextReps[i1].setVisible(false);
                bTextReps[i2].setVisible(false);
                break;
            case 1 :
                audioStartQ.stop();
                String avisAmi = model.getJeu().getQuestionNum(numQ).getAvisAppelAmi();

                AudioPlay audioAppelAmi = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/jokers/appel_ami.wav");

                ButtonType btOKAmi = ButtonType.OK;
                Alert alertAmi = new Alert(Alert.AlertType.NONE, avisAmi, btOKAmi);
                alertAmi.setHeaderText("Avis de votre ami");
                alertAmi.showAndWait();

                audioAppelAmi.stop();
                audioStartQ.restart();
                break;
            case 2 :
                audioStartQ.stop();
                AudioPlay audioPublic = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/jokers/public1.wav");

                jokersUsed[2].setVisible(true);
                jokersUsed[2].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    try {
                        audioPublic.stop();
                        new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/jokers/public2.wav").play();

                        ImageView graph0 = new ImageView("/images/statsPublic/graph0.png");
                        graph0.setFitWidth(180); graph0.setFitHeight(250);
                        graph0.setTranslateX(1000); graph0.setTranslateY(50);
                        rootPane.getChildren().add(graph0);

                        FadeTransition ftJokers = new FadeTransition(Duration.seconds(1),graph0);
                        ftJokers.setFromValue(0);
                        ftJokers.setToValue(1);
                        ftJokers.play();

                        ImageView graphAvis = new ImageView("/images/statsPublic/graph" + numQ + ".png");
                        graphAvis.setFitWidth(180); graphAvis.setFitHeight(250);
                        graphAvis.setTranslateX(1000);  graphAvis.setTranslateY(50);

                        PauseTransition pause = new PauseTransition(Duration.seconds(10.5));
                        pause.setOnFinished(actionEvent -> {
                            rootPane.getChildren().add(graphAvis);
                            FadeTransition ftAvisPublic = new FadeTransition(Duration.seconds(1),graphAvis);
                            ftAvisPublic.setFromValue(0);
                            ftAvisPublic.setToValue(1);
                            ftAvisPublic.play();

                            PauseTransition newPause = new PauseTransition(Duration.seconds(0.5));
                            newPause.setOnFinished(event -> {
                                try {
                                    audioStartQ.restart();
                                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            new SequentialTransition(newPause).play();
                        });
                        new SequentialTransition(pause).play();

                        PauseTransition pauseExit = new PauseTransition(Duration.seconds(5));
                        pauseExit.setOnFinished(actionEvent1 -> {
                            FadeTransition ftExitPublic1 = new FadeTransition(Duration.seconds(1),graphAvis);
                            ftExitPublic1.setFromValue(1);
                            ftExitPublic1.setToValue(0);

                            FadeTransition ftExitPublic2 = new FadeTransition(Duration.seconds(1),graph0);
                            ftExitPublic1.setFromValue(1);
                            ftExitPublic1.setToValue(0);

                            ftExitPublic1.play();
                            ftExitPublic2.play();
                        });
                        new SequentialTransition(pauseExit).play();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            case 3 :
                audioStartQ.stop();
                String avisFeeling = model.getJeu().getQuestionNum(numQ).getFeeling();
                AudioPlay audioFeeling = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/jokers/feeling1.wav");

                ButtonType btOK = ButtonType.OK;
                Alert alertFeeling = new Alert(Alert.AlertType.NONE, avisFeeling, btOK);
                alertFeeling.setHeaderText("Feeling de l'animateur");
                alertFeeling.showAndWait();

                long start = System.currentTimeMillis();
                AudioPlay audioFinFeeling = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/jokers/feeling2.wav");
                audioFeeling.stop();

                while (!audioFinFeeling.isFinished(start)) {
                    System.out.print("");
                }
                audioStartQ.restart();
                break;
            default :
                break;
        }
        model.getJeu().getJokerNum(idJ).setUseJoker();
    }

    public void showSommeGagnee(int numQ) {
        rootPane.getChildren().clear();

        Label lSomme = new Label(model.getJeu().getSomme(numQ));
        lSomme.setFont(new Font("Copperplate Gothic Bold", 100));
        lSomme.setStyle("-fx-text-fill: orange; -fx-text-alignment: center;");
        lSomme.setTranslateY(380);

        ImageView imgSomme = new ImageView("/images/divSomme.png");
        imgSomme.setFitWidth(rootPane.getWidth());
        imgSomme.setFitHeight(138.3);
        imgSomme.setTranslateY(380);
        rootPane.getChildren().add(imgSomme);

        gpSomme = new GridPane();
        gpSomme.getChildren().add(lSomme);
        gpSomme.setAlignment(Pos.CENTER);
        gpSomme.setMinSize(rootPane.getWidth(),136.5);
        rootPane.getChildren().add(gpSomme);

        if(gpImgRep != null)
            rootPane.getChildren().add(gpImgRep);

        setRootPane(rootPane);
    }

    public void stopGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        audioStartQ.stop();
        model.getJeu().setFinishGame();
        rootPane.getChildren().clear();

        afficheQuestion(numQ);
        validRepFin();

        setRootPane(rootPane);
    }

    private void validRepFin() {
        for (int r = 0; r < bTextReps.length; r++) {
            int finalR = r;
            bTextReps[r].addEventHandler(ActionEvent.ACTION, actionEvent -> {
                bTextReps[finalR].setOnAction(null);

                ImageView imgValidPensee = new ImageView("images/CaseOrange.png");
                imgValidPensee.setFitWidth(604.5);
                imgValidPensee.setFitHeight(80);
                imgValidPensee.setTranslateX(64.5 + 632*(finalR %2));

                bValidPensee = new Button(bTextReps[finalR].getText());
                bValidPensee.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 34px; -fx-text-fill: black;");
                bValidPensee.setTranslateX(85 + 632*(finalR %2));

                if(finalR < 2) {
                    imgValidPensee.setTranslateY(531);
                    bValidPensee.setTranslateY(531);
                } else {
                    imgValidPensee.setTranslateY(621);
                    bValidPensee.setTranslateY(621);
                }

                rootPane.getChildren().add(imgValidPensee);
                rootPane.getChildren().add(bValidPensee);
                bValidPensee.setOnAction(new ControllerButton(model,this));
            });
        }
    }

    public int getNumActualQ() {
        return numQ;
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Button[] getAllTextReps() {
        return bTextReps;
    }

    public Button getButtonTextRep(int n) {
        return bTextReps[n];
    }

    public Button getButtonInit() {
        return bInit;
    }

    public Button getButtonStart() {
        return bStart;
    }

    public Button getButtonExplications() {
        return bExplications;
    }

    public Button getButtonValidPensee() {
        return bValidPensee;
    }

    public ImageView getImgJoker(int n) {
        return imgJokers[n];
    }

    public ImageView getImgJokerUsed(int n) {
        return jokersUsed[n];
    }

    public GridPane getGridPaneQu() {
        return gpLQu;
    }

    public GridPane getGridPaneSomme() {
        return gpSomme;
    }

    public GridPane getGridPaneArret() {
        return gpArret;
    }

    public String lose(int numQ) {
        if(numQ <= 5)
            return "0 €";

        return model.getJeu().getSommePalier(numQ);
    }
}
