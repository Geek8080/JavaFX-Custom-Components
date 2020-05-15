import controller.DownloadCard;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class Main extends Application {

    public static final Logger mainLogger = LogManager.getLogger(Main.class.getName());

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
