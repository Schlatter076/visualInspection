package loyer.client;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import loyer.imgBasic.ImageViewer;

public class ImageViewTool {

  public JFrame frame;
  private ImageViewer imageViewer;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ImageViewTool window = new ImageViewTool("L:\\JavaCode\\JavaHostComputer\\src\\", "run.jpg");
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public ImageViewTool(String filePath, String fileName) {
    initialize(filePath, fileName);
  }

  private void initialize(String filePath, String fileName) {
    frame = new JFrame("图片查看器");
    try {
      imageViewer = new ImageViewer(filePath + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    frame.add(imageViewer);
    frame.setBounds(100, 100, 691, 436);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    Toolkit tk = Toolkit.getDefaultToolkit();
    Image img = tk.getImage(frame.getClass().getResource("/Kyokuto.png"));
    frame.setIconImage(img);
  }

}
