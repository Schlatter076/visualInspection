package loyer.test;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import loyer.imgBasic.ImageViewer;
import loyer.imgBasic.ImgHelper;

public class Test {

  public static void main(String[] args) throws Exception {
    /*String filePath = "resource/run.jpg";
    String imgName = "flower";
    DBHelper.writeToDB(filePath, imgName);*/
    //DBHelper.readFromDB(1, "2018-09-08", "resource/copy.jpg");
    //Image img = Toolkit.getDefaultToolkit().getImage("L:\\visualInspection\\resource\\mirror.jpg");
    //BufferedImage buffImg = ImgHelper.convertToBufferedImage(img);
    ImageViewer window = new ImageViewer("L:\\visualInspection\\resource\\mirror.jpg");
    window.setVisible(true);
    
  }

}
