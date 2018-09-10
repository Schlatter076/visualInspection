package loyer.imgBasic;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageViewer extends JComponent {

  private static final long serialVersionUID = 1L;
  final JSlider slider;
  final ImageComponent image;
  final JScrollPane scrollPane;

  public ImageViewer(String path) throws IOException {
    slider = new JSlider(0, 1000, 500);
    image = new ImageComponent(path);
    scrollPane = new JScrollPane(image);

    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        image.setZoom(2. * slider.getValue() / slider.getMaximum());
      }
    });

    this.setLayout(new BorderLayout());
    this.add(slider, BorderLayout.NORTH);
    this.add(scrollPane);
  }
  public ImageViewer(BufferedImage buffImg) {
    slider = new JSlider(0, 1000, 500);
    image = new ImageComponent(buffImg);
    scrollPane = new JScrollPane(image);
    
    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        image.setZoom(2. * slider.getValue() / slider.getMaximum());
      }
    });

    this.setLayout(new BorderLayout());
    this.add(slider, BorderLayout.NORTH);
    this.add(scrollPane);
  }

}
