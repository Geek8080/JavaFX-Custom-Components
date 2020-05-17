package card;

import card.controller.DownloadCard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {

    public static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DownloadCard card = new DownloadCard();
        primaryStage.setScene(new Scene(card));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }
}
