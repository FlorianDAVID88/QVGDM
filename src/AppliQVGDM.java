import controllers.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class AppliQVGDM extends Application {
    private static final int width = (int) Screen.getPrimary().getBounds().getWidth();
    private static final int height = (int) Screen.getPrimary().getBounds().getHeight();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Qui Veut Gagner des Millions");
        stage.getIcons().add(new Image(this.getClass().getResource("images/logoQVGDM.png").toString()));
        stage.setMaximized(true);

        Model model = new Model(width, height);
        View view = new View(model);
        Controller control = new Controller(model,view);

        Scene scene = new Scene(view.getRootPane(),view.getWidth(), view.getHeight());
        view.getRootPane().setStyle("-fx-background-image: url('images/imgfond.jpg'); -fx-background-repeat: no-repeat; -fx-background-size: " + width + " " + height + "; -fx-background-position: center center;");

        stage.setScene(scene);
        stage.show();
        control.startAnimation();
    }
}
