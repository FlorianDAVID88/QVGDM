package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Model;
import view.AudioPlay;
import view.View;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Optional;

public class ControllerButton implements EventHandler<ActionEvent> {
    Model model;
    View view;
    Button newBExpli;


    public ControllerButton(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            if(actionEvent.getSource() == view.getButtonInit())
                view.showPyramideGains();
            else if(actionEvent.getSource() == view.getButtonStart()) {
                view.beforeQuestion(1);
            } else if(actionEvent.getSource() == view.getButtonExplications()) {
                String expl = model.getJeu().getQuestionNum(view.getNumActualQ()).getExplications();
                if(expl != null) {
                    ButtonType bNextQu;
                    if(model.getJeu().isFinished() || view.getNumActualQ() == 15)
                        bNextQu = new ButtonType("Terminer la partie", ButtonBar.ButtonData.FINISH);
                    else
                        bNextQu = new ButtonType("Question suivante", ButtonBar.ButtonData.NEXT_FORWARD);

                    Alert alertExplications = new Alert(Alert.AlertType.NONE, expl, bNextQu);
                    alertExplications.setHeaderText("Explications");
                    alertExplications.showAndWait();
                }

                if(model.getJeu().isFinished() || view.getNumActualQ() == 15) {
                    view.showSommeGagnee(view.getNumActualQ() - 1);
                    view = new View(model);
                } else {
                    view.showSommeGagnee(view.getNumActualQ());
                    view.getGridPaneSomme().setOnMouseClicked(new ControllerMouse(model, view));
                }
            }


            for(int i = 0; i < view.getAllTextReps().length; i++) {
                if(actionEvent.getSource() == view.getButtonTextRep(i)) {
                    ButtonType bYes = ButtonType.YES;
                    ButtonType bNo = ButtonType.NO;
                    Alert alertDernierMot = new Alert(Alert.AlertType.NONE, "Est-ce votre dernier mot ?", bNo, bYes);
                    Optional<ButtonType> choice = alertDernierMot.showAndWait();

                    if(choice.get() == bYes) {
                        view.validReponse(view.getNumActualQ(), i);
                    }
                }
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        if(actionEvent.getSource() == view.getButtonValidPensee()) {
            int idBonneRep = model.getJeu().getQuestionNum(view.getNumActualQ()).getIdBonneRep();
            view.afficheReponse(idBonneRep);

            if (model.getJeu().getQuestionNum(view.getNumActualQ()).getExplications() != null) {
                newBExpli = new Button("Explications");
            } else {
                if(model.getJeu().isFinished() || view.getNumActualQ() == 15)
                    newBExpli = new Button("Terminer la partie");
                else
                    newBExpli = new Button("Question suivante");
            }

            newBExpli.setTranslateX(350);
            newBExpli.setTranslateY(225);
            newBExpli.setStyle("-fx-background-color: purple; -fx-text-fill: white; -fx-border-color: white;");
            view.getRootPane().getChildren().add(newBExpli);

            newBExpli.setOnAction(new ControllerButton(model,view));
        }

        if(actionEvent.getSource() == newBExpli) {
            String expl = model.getJeu().getQuestionNum(view.getNumActualQ()).getExplications();
            if(expl != null) {
                ButtonType bNextQu;
                if(model.getJeu().isFinished() || view.getNumActualQ() == 15)
                    bNextQu = new ButtonType("Terminer la partie", ButtonBar.ButtonData.FINISH);
                else
                    bNextQu = new ButtonType("Question suivante", ButtonBar.ButtonData.NEXT_FORWARD);

                Alert alertExplications = new Alert(Alert.AlertType.NONE, expl, bNextQu);
                alertExplications.setHeaderText("Explications");
                alertExplications.showAndWait();
            }
            view.getRootPane().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getSource() == KeyCode.ENTER) {
                    try {
                        view.showSommeGagnee(view.getNumActualQ() - 1);
                        AudioPlay audioFinish;
                        if(view.getNumActualQ() >= 12) {
                            audioFinish = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/fin2.wav");
                        } else if(view.getNumActualQ() <= 5) {
                            audioFinish = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/fin0.wav");
                        } else {
                            audioFinish = new AudioPlay("C:/Users/fdavid5/Desktop/QVGDMJavaFx/src/jingles/fin1.wav");
                        }
                        audioFinish.play();
                        view.getGridPaneSomme().setOnMouseClicked(new ControllerMouse(model,view));
                    } catch (UnsupportedAudioFileException | LineUnavailableException |
                             IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
