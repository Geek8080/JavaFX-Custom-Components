package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DownloadCard extends AnchorPane{

    public DownloadCard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DownloadCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
            init();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private AnchorPane cardBasePane;

    @FXML
    private HBox nameHBox;

    @FXML
    private Text serialNumberText;

    @FXML
    private Text fileNameText;

    @FXML
    private JFXProgressBar progressBarUI;

    @FXML
    private ImageView shrinkIconImage;

    @FXML
    private VBox typeHolderVBox;

    @FXML
    private ImageView fileTypeIconImage;

    @FXML
    private Text fileTypeText;

    @FXML
    private HBox statHolderHBox;

    @FXML
    private VBox keyHolderVBox;

    @FXML
    private Text progressText;

    @FXML
    private Text transferRateText;

    @FXML
    private Text etaText;

    @FXML
    private VBox valueHolderVBox;

    @FXML
    private Text progressValueText;

    @FXML
    private Text transferRateValueText;

    @FXML
    private Text etaValueText;

    @FXML
    private AnchorPane titlePane;

    @FXML
    private Text titleText;

    @FXML
    private JFXButton pauseResumeButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    void cancel(MouseEvent event) {

    }

    @FXML
    void pause_resume(MouseEvent event) {

    }

    @FXML
    void shrink_expand(MouseEvent event) {

    }



    private void init() {

    }

}
