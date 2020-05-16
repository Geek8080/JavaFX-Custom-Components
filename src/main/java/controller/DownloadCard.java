package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import utils.Downloadable;

import java.util.Timer;
import java.util.TimerTask;


public class DownloadCard extends AnchorPane {

    public DownloadCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DownloadCard.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.setManaged(true);

        this.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        this.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        try {
            fxmlLoader.load();
            JFXDepthManager.setDepth(this, 2);

            //depthManager.setDepth(shrinkIconImage, 3);
            //init();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    private boolean isCollapsed = false;
    private Downloadable downloadableObject;
    private boolean isPaused = false;

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
    private AnchorPane shrinkPane;

    @FXML
    public void cancel(MouseEvent event) {
        downloadableObject.cancel();
    }

    @FXML
    public void pause_resume(MouseEvent event) {
        if (isPaused) {
            pauseResumeButton.setDisable(true);
            downloadableObject.resume();
            isPaused = false;
            pauseResumeButton.setDisable(false);
            pauseResumeButton.setText("Pause");
        } else {
            pauseResumeButton.setDisable(true);
            downloadableObject.pause();
            isPaused = true;
            pauseResumeButton.setDisable(true);
            pauseResumeButton.setText("Resume");
        }
    }

    @FXML
    void shrink_expand(MouseEvent event) {

        Thread rotate = new Thread(() -> {
            RotateTransition rt = new RotateTransition();
            rt.setAxis(Rotate.Z_AXIS);
            rt.setByAngle(180);
            rt.setDuration(Duration.millis(500));
            rt.setNode(shrinkIconImage);
            rt.play();
        });

        Timer timerAction = new Timer();
        TimerTask task;

        if (isCollapsed) {

            titleOnExpansion();

            task = new TimerTask() {
                int height = 72;

                @Override
                public void run() {

                    if (height <= 284) {
                        cardBasePane.setPrefHeight(height);
                        setPrefHeight(height);
                        height++;
                    } else {
                        this.cancel();
                    }

                }

            };

        } else {

            titleOnCollapse();

            task = new TimerTask() {

                int height = 284;

                @Override
                public void run() {

                    if (height >= 72) {
                        cardBasePane.setPrefHeight(height);
                        //Uncomment if this is not working
                        setPrefHeight(height);
                        height--;
                    } else {
                        this.cancel();
                    }

                }

            };

        }

        timerAction.scheduleAtFixedRate(task, 0, 2);
        rotate.start();

    }

    private void titleOnExpansion() {

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setNode(titlePane);
        fadeTransition1.setDuration(Duration.millis(500));
        fadeTransition1.setFromValue(1.0);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.setOnFinished(event -> titlePane.setVisible(false));

        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setNode(nameHBox);
        fadeTransition2.setDuration(Duration.millis(500));
        fadeTransition2.setFromValue(0.0);
        fadeTransition2.setToValue(1.0);
        nameHBox.setVisible(true);

        FadeTransition fadeTransition3 = new FadeTransition();
        fadeTransition3.setNode(shrinkPane);
        fadeTransition3.setDuration(Duration.millis(300));
        fadeTransition3.setFromValue(0.0);
        fadeTransition3.setToValue(1.0);
        fadeTransition3.setDelay(Duration.millis(300));
        shrinkPane.setVisible(true);

        fadeTransition1.play();
        fadeTransition2.play();
        fadeTransition3.play();

        isCollapsed = false;
    }

    private void titleOnCollapse() {

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setNode(titlePane);
        fadeTransition1.setDuration(Duration.millis(500));
        fadeTransition1.setFromValue(0.0);
        fadeTransition1.setToValue(1.0);
        titlePane.setVisible(true);

        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setNode(nameHBox);
        fadeTransition2.setDuration(Duration.millis(500));
        fadeTransition2.setFromValue(1.0);
        fadeTransition2.setToValue(0.0);
        fadeTransition2.setOnFinished(event -> nameHBox.setVisible(false));

        FadeTransition fadeTransition3 = new FadeTransition();
        fadeTransition3.setNode(shrinkPane);
        fadeTransition3.setDuration(Duration.millis(100));
        fadeTransition3.setFromValue(1.0);
        fadeTransition3.setToValue(0.0);
        fadeTransition3.setOnFinished(event -> shrinkPane.setVisible(false));

        fadeTransition1.play();
        fadeTransition2.play();
        fadeTransition3.play();

        isCollapsed = true;
    }

    public void addDownloadableObject(Downloadable downloadableObject) {
        this.downloadableObject = downloadableObject;
        init();
    }

    private void init() {
        serialNumberText.setText(downloadableObject.getSerialNo() + ".");
        fileNameText.setText(downloadableObject.getFileName());
        titleText.setText(downloadableObject.getFileName());
        fileTypeText.setText(downloadableObject.getFileType());
        setProgressValue();
        transferRateValueText.setText("0 B/s");
        etaValueText.setText("INF");
        updateFileTypeImage();
        updateETA_TransferRate();
    }

    public void updateFileTypeImage() {
        fileTypeIconImage.setImage((javafx.scene.image.Image) downloadableObject.getFileTypeIcon());
    }

    public void setProgressValue() {
        progressBarUI.setProgress(downloadableObject.getProgress());
        progressValueText.setText(downloadableObject.getProgressValuesAsString());
    }

    public void updateETA_TransferRate() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            double change = 0;
            String speed = "";
            @Override
            public void run() {
                double sp = downloadableObject.getDownloaded();
                change = sp-change;
                sp = change/(8*1024);
                if(sp>1024){
                    speed = (sp/1024) + " MB/s";
                }else{
                    speed = (sp) + " KB/s";
                }
                double eta = (downloadableObject.getSize() - downloadableObject.getDownloaded())/sp;
                String unit = "";
                if(eta>3600){
                    unit += (eta/3600) + " hr ";
                    eta%=3600;
                }
                if(eta>60){
                    unit += (eta/60) + " min ";
                    eta%=60;
                }
                unit += eta + " s";
                etaValueText.setText(unit);
                transferRateValueText.setText(speed);
            }
        }, 3, 1000);
    }

    public void updateProgressValue(){
        progressValueText.setText(downloadableObject.getProgressValuesAsString());
    }

    public void setPausabelCard(boolean isPausable){
        pauseResumeButton.setDisable(false);
    }

}
