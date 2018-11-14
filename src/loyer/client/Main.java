package loyer.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

import loyer.imgBasic.ImageComponent;
import loyer.imgBasic.ImgHelper;
import javax.swing.SwingConstants;

public class Main {

  private JFrame frame;
  private JMenuBar menuBar;

  private FileDialog openDialog, saveDialog;
  private JScrollPane scrollPane;
  private JButton grayButt;
  private JButton binaryButt;
  private JButton button;
  private JSlider slider;
  private JButton exitButt;
  private JButton openButt;
  private JButton saveButt;
  private ImageComponent image;
  private BufferedImage img;
  private List<Integer> mydatap01 = new ArrayList<Integer>();
  private List<Integer> mydatap02 = new ArrayList<Integer>();
  private List<Integer> mydatap03 = new ArrayList<Integer>();
  private int dataCountOfLi = 1000;
  private Point point; // c# Point F
  private Graphics graphics; // c# Graphics
  private JLabel picLabel; // c# pictureBox
  private ImageIcon picImage; // c# pictureBox.image
  
  private JLabel label = new JLabel();

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Main window = new Main();
          window.frame.setVisible(true);
          window.addListener();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public Main() {
    initialize();
  }

  private void initialize() {
    
    
    try {
      //将界面风格设置成和系统一置
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    }//*/
    
    frame = new JFrame("CCD视觉检测系统");
    frame.setBackground(new Color(245, 245, 245));
    frame.getContentPane().setBackground(new Color(245, 245, 245));

    final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    frame.setBounds(0, 0, WIDTH, HEIGHT - 50);
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(frame.getClass().getResource("/Kyokuto.png")));
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    label.setHorizontalAlignment(SwingConstants.CENTER);
    scrollPane = new JScrollPane(label);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setBounds(880, 71, 545, 527);
    frame.getContentPane().add(scrollPane);

    grayButt = new JButton("灰度化");
    grayButt.setBounds(698, 100, 95, 23);
    grayButt.setFont(new Font("宋体", Font.PLAIN, 12));
    grayButt.setBackground(new Color(245, 245, 245));
    grayButt.setOpaque(false);
    frame.getContentPane().add(grayButt);

    binaryButt = new JButton("二值化");
    binaryButt.setBounds(698, 144, 95, 23);
    binaryButt.setFont(new Font("宋体", Font.PLAIN, 12));
    binaryButt.setBackground(new Color(245, 245, 245));
    binaryButt.setOpaque(false);
    frame.getContentPane().add(binaryButt);

    button = new JButton("区域连通");
    button.setBounds(698, 202, 95, 23);
    button.setFont(new Font("宋体", Font.PLAIN, 12));
    button.setBackground(new Color(245, 245, 245));
    button.setOpaque(false);
    frame.getContentPane().add(button);

    slider = new JSlider();
    slider.setBounds(10, 740, 664, 26);
    slider.setForeground(new Color(245, 245, 245));
    slider.setBackground(new Color(245, 245, 245));
    frame.getContentPane().add(slider);

    menuBar = new JMenuBar();
    menuBar.setForeground(Color.BLACK);
    menuBar.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.setBackground(Color.WHITE);
    frame.setJMenuBar(menuBar);

    exitButt = new JButton("退出系统(Exit)");

    exitButt.setBackground(new Color(245, 245, 245));
    exitButt.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.add(exitButt);

    openButt = new JButton("打开(Open)");
    openButt.setBackground(new Color(245, 245, 245));
    openButt.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.add(openButt);

    saveButt = new JButton("保存(Save)");
    saveButt.setBackground(new Color(245, 245, 245));
    saveButt.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.add(saveButt);

    openDialog = new FileDialog(frame, "打开文件", FileDialog.LOAD);
    //openDialog.setFile("*.jpg;*.jpeg;*.gif;*.png");
    
    /*
    openDialog.setFilenameFilter(new FilenameFilter() {
      
      @Override
      public boolean accept(File dir, String name) {
        String[] f = {".jpg", ".jpeg", "gif", ".png"};
        if(!dir.isDirectory()) {
          for(String s : f) {
            if(name.endsWith(s)) return true;
          }
        }
        return false;
      }
    });//*/
    

    saveDialog = new FileDialog(frame, "保存文件", FileDialog.SAVE);
  }

  /**
   * 给各组件添加事件
   */
  public void addListener() {
    // 窗口退出
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        ifClose();
      }
    });
    // 退出按钮事件
    exitButt.addActionListener(e -> ifClose());
    // 灰度化按钮事件
    grayButt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        img = ImgHelper.grayImage(img);
        //label.setIcon(new ImageIcon(img));
        scrollPane.setViewportView(new ImageComponent(img));
      }
    });
    // 二值化按钮事件
    binaryButt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        img = ImgHelper.binaryImage(img);
        //label.setIcon(new ImageIcon(img));
        scrollPane.setViewportView(new ImageComponent(img));
      }
    });
    // 区域联通按钮事件
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }
    });
    // 打开文件事件
    openButt.addActionListener(e -> {
      openDialog.setVisible(true);
      try {
        img = ImageIO.read(new File(openDialog.getDirectory() + openDialog.getFile()));
        image = new ImageComponent(img);
      } catch (IOException e1) {
        JOptionPane.showMessageDialog(null, "错误xx：" + e1.getLocalizedMessage());
      }
      //label.setIcon(new ImageIcon(img));
      scrollPane.setViewportView(image);
    });
    // 保存文件事件
    saveButt.addActionListener(e -> {
      saveDialog.setVisible(true);
      FileOutputStream out = null;
      try {
        out = new FileOutputStream(saveDialog.getDirectory() + saveDialog.getFile() + ".jpg");
        ImageIO.write(img, "JPEG", out);
        out.flush();
      } catch (FileNotFoundException e1) {
        JOptionPane.showMessageDialog(null, "错误xx：" + e1.getLocalizedMessage());
      } catch (IOException e1) {
        JOptionPane.showMessageDialog(null, "错误xx：" + e1.getLocalizedMessage());
      } finally {
        try {
          out.close();
        } catch (IOException e1) {
          JOptionPane.showMessageDialog(null, "错误xx：" + e1.getLocalizedMessage());
        }
      }
    });

  }

  /**
   * 是否退出系统
   */
  private void ifClose() {
    int num = JOptionPane.showConfirmDialog(null, "确认退出系统？", "提示", JOptionPane.YES_NO_OPTION);
    if (num == JOptionPane.YES_OPTION) {
      System.exit(0);
    }
  }
  
}
