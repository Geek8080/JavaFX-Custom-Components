package card.controller;

import card.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumnBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import card.utils.Downloadable;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;


public class DownloadCard extends AnchorPane {


    public Timer getTimer() {
        return timer;
    }

    private Timer timer;

    public DownloadCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DownloadCard.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Main.logger.info("Successfully assigned Controller to the FXML and set the root.");

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

            Main.logger.error("Failed to load the FXML.(" + ex.getMessage() + ")", ex);
        }
    }

    private boolean isCollapsed = false;
    private Downloadable downloadableObject;
    private boolean isPaused = false;
    private long prevDownloaded = 0;

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
    private Text status;

    @FXML
    public void cancel(MouseEvent event) {
        downloadableObject.cancel();
    }

    @FXML
    public void pause_resume(MouseEvent event) {
        if (isPaused) {
            pauseResumeButton.setDisable(true);
            downloadableObject.resume();
            Main.logger.info("Resumed the download Successfully: " + downloadableObject.getFileName());
            isPaused = false;
            pauseResumeButton.setDisable(false);
            pauseResumeButton.setText("Pause");
        } else {
            pauseResumeButton.setDisable(true);
            downloadableObject.pause();
            Main.logger.info("Paused the download Successfully: " + downloadableObject.getFileName());
            isPaused = true;
            pauseResumeButton.setDisable(false);
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

        Main.logger.info("Declared rotation animation.");

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

        Main.logger.info("Added timer task to schedule with delay of 0ms.");

        timerAction.scheduleAtFixedRate(task, 0, 2);

        Main.logger.info("Started the rotation animation.");

        rotate.start();

    }

    private void titleOnExpansion() {

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setNode(titlePane);
        fadeTransition1.setDuration(Duration.millis(500));
        fadeTransition1.setFromValue(1.0);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.setOnFinished(event -> titlePane.setVisible(false));

        Main.logger.info("Defined an animation to fade out titlePane.");

        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setNode(nameHBox);
        fadeTransition2.setDuration(Duration.millis(500));
        fadeTransition2.setFromValue(0.0);
        fadeTransition2.setToValue(1.0);
        nameHBox.setVisible(true);

        Main.logger.info("Defined an animation to fade in nameHBox.");

        FadeTransition fadeTransition3 = new FadeTransition();
        fadeTransition3.setNode(shrinkPane);
        fadeTransition3.setDuration(Duration.millis(300));
        fadeTransition3.setFromValue(0.0);
        fadeTransition3.setToValue(1.0);
        fadeTransition3.setDelay(Duration.millis(300));
        shrinkPane.setVisible(true);

        Main.logger.info("Defined an animation to fade in shrinkPane(Main Content- Info).");

        fadeTransition1.play();
        fadeTransition2.play();
        fadeTransition3.play();

        Main.logger.info("Played fade animations for tilePane, nameHBox and shrinkPane.");

        isCollapsed = false;

        Main.logger.info("Changed state of card to expanded.");
    }

    private void titleOnCollapse() {

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setNode(titlePane);
        fadeTransition1.setDuration(Duration.millis(500));
        fadeTransition1.setFromValue(0.0);
        fadeTransition1.setToValue(1.0);
        titlePane.setVisible(true);

        Main.logger.info("Defined an animation to fade in titlePane.");

        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setNode(nameHBox);
        fadeTransition2.setDuration(Duration.millis(500));
        fadeTransition2.setFromValue(1.0);
        fadeTransition2.setToValue(0.0);
        fadeTransition2.setOnFinished(event -> nameHBox.setVisible(false));

        Main.logger.info("Defined an animation to fade out nameHBox.");

        FadeTransition fadeTransition3 = new FadeTransition();
        fadeTransition3.setNode(shrinkPane);
        fadeTransition3.setDuration(Duration.millis(100));
        fadeTransition3.setFromValue(1.0);
        fadeTransition3.setToValue(0.0);
        fadeTransition3.setOnFinished(event -> shrinkPane.setVisible(false));

        Main.logger.info("Defined an animation to fade out shrinkPane(Main Content- Info).");

        fadeTransition1.play();
        fadeTransition2.play();
        fadeTransition3.play();

        Main.logger.info("Played fade animations for tilePane, nameHBox and shrinkPane.");

        isCollapsed = true;

        Main.logger.info("Changed state of card to collapsed.");
    }

    public void addDownloadableObject(Downloadable downloadableObject) {
        this.downloadableObject = downloadableObject;
        try{
            init();
        }catch(Exception ex){
            Main.logger.fatal("Encountered an error while initialising the card with new download object.(" + ex.getMessage() + ")", ex);
        }
    }

    private void init() throws Exception {
        serialNumberText.setText(downloadableObject.getSerialNo() + ".");
        fileNameText.setText(downloadableObject.getFileName());
        titleText.setText(downloadableObject.getFileName());
        fileTypeText.setText(downloadableObject.getFileType());
        setProgressValue();
        transferRateValueText.setText("0 B/s");
        etaValueText.setText("INF");
        //updateFileTypeImage();
        updateETA_TransferRate();
        //downloadableObject.start();
    }

    public void updateFileTypeImage() {
        fileTypeIconImage.setImage(SwingFXUtils.toFXImage(getBufferedImage(), null));
    }

    private BufferedImage getBufferedImage(){
        Icon icon = this.downloadableObject.getFileTypeIcon();
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0,0);
        g.setColor(Color.WHITE);
        g.dispose();

        return bi;
    }

    public void setProgressValue() throws Exception{
        progressBarUI.setProgress(downloadableObject.getProgress());

        Platform.runLater(() -> {
            progressValueText.setText(downloadableObject.getProgressValuesAsString());
        });
    }

    public void updateETA_TransferRate() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            double change = 0;
            String speed = "";
            @Override
            public void run() {
                if(downloadableObject.getDownloaded()==downloadableObject.getSize()){
                    this.cancel();
                }
                double sp = downloadableObject.getDownloaded();
                change = sp-prevDownloaded;
                prevDownloaded = downloadableObject.getDownloaded();
                sp = change/(1024);
                if(sp>1024){
                    speed = String.format("%.3f MB/s",(sp/1024));
                }else{
                    speed = String.format("%.3f KB/s",(sp));
                }
                double eta = ((double)(downloadableObject.getSize() - downloadableObject.getDownloaded()))/change;
                String unit = "";
                if(eta>3600){
                    unit += ((int)eta/3600) + " hr ";
                    eta%=3600;
                }
                if(eta>60){
                    unit += ((int)eta/60) + " min ";
                    eta%=60;
                }
                unit += (int)eta + " s";
                etaValueText.setText(unit);
                transferRateValueText.setText(speed);
            }
        }, 3, 1000);

    }

    public void setStatus(String str){
        this.status.setText(str);
    }

    public void updateProgressValue(){
        progressValueText.setText(downloadableObject.getProgressValuesAsString());
    }

    public void setPausabelCard(boolean isPausable){
        pauseResumeButton.setDisable(false);
    }

    public void setMerging(){
        Platform.runLater(() -> {
            this.status.setText("Completing Download...");
            this.cancelButton.setDisable(true);
            this.pauseResumeButton.setDisable(true);
            this.progressValueText.setText("100%");
            this.etaValueText.setText("Few seconds");
            this.transferRateValueText.setText("-");
            this.progressBarUI.setProgress(JFXProgressBar.INDETERMINATE_PROGRESS);
        });
    }

    public void setCompleted(){
        Platform.runLater(() -> {
            this.progressBarUI.setProgress(1);
            this.status.setText("Download Completed...");
            this.cancelButton.setDisable(false);
            this.cancelButton.setText("OK");
            this.pauseResumeButton.setVisible(false);
            this.progressBarUI.setProgress(1);
            this.getChildren().removeAll();
        });
    }

    public void hide(){
        Platform.runLater(() -> {
            this.shrinkPane.setMinHeight(0);
            this.shrinkPane.setMinWidth(0);
            this.shrinkPane.setPrefHeight(0);
            this.shrinkPane.setPrefWidth(0);
        });
    }

}
