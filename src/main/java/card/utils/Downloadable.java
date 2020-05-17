package card.utils;

import javax.swing.*;

public interface Downloadable {

    //int serialNo = -1;
    //String fileName = "";
    //String fileType = "";
    //double progressPercent = 0.0d;
    //long downloadedBit = 0l;
    //long sizeBit = 0l;
    //double transferRate = 0.0d;
    //long etaTimeInMillis = Long.MAX_VALUE;
    //boolean isPausable = false;
    //String fileExtension = "";
    //Icon fileTypeIcon = null;

    int getSerialNo();

    String getFileName();

    String getFileType();

    double getProgress();

    String getProgressValuesAsString();

    double getDownloaded();

    double getSize();

    Icon getFileTypeIcon();

    void cancel();

    void pause();

    void resume();

}
