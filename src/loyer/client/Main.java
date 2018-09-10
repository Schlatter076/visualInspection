package loyer.client;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.plaf.MenuBarUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Main {

  private JFrame frame;
  private JMenuBar menuBar;
  private JMenu menu1;
  private JMenuItem openItem;
  private JMenuItem saveItem;
  private JMenuItem exitItem;
  private JMenu menu2;  
  private JMenuItem menu2_1;
  private JMenuItem menu2_2;
  private JMenuItem menu2_3;
  private JMenuItem menu2_4;
  private JMenuItem menu2_5;
  private JMenuItem menu2_6;
  private JMenu menu2_m;
  private JMenuItem menu2_m_1; 
  private JMenuItem menu2_m_2; 
  private JMenu menu3;
  private JMenuItem menu3_1; 
  private JMenuItem menu3_2;
  private JMenuItem menu3_3;  
  private JMenuItem menu3_4; 
  private JMenu menu4; 
  private JMenuItem menu4_1;  
  private JMenuItem menu4_2; 
  private JMenuItem menu4_3;
  private JMenuItem menu4_4;
  private JMenuItem menu4_5;
  
  private FileDialog openDialog, saveDialog;
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Main window = new Main();
          window.frame.setVisible(true);
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
    frame = new JFrame("CCD视觉检测系统");
    frame.getContentPane().setBackground(Color.WHITE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        ifClose();
      }
    });
    
    final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    frame.setBounds(0, 0, WIDTH, HEIGHT);
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(frame.getClass().getResource("/Kyokuto.png")));
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
    menuBar = new JMenuBar();
    menuBar.setForeground(Color.BLACK);
    menuBar.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.setBackground(Color.WHITE);
    frame.setJMenuBar(menuBar);
    
    menu1 = new JMenu("文件(F)");
    menu1.setIcon(new ImageIcon(Main.class.getResource("/1.png")));
    menu1.setBackground(Color.WHITE);
    menu1.setForeground(Color.BLACK);
    menu1.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.add(menu1);
    
    openItem = new JMenuItem("打开(O)");
    openItem.addActionListener(new OpenItemListener());
    openItem.setBackground(Color.WHITE);
    openItem.setFont(new Font("宋体", Font.PLAIN, 12));
    menu1.add(openItem);
    
    openDialog = new FileDialog(frame, "打开文件", FileDialog.LOAD);
    
    saveItem = new JMenuItem("保存(S)");
    saveItem.addActionListener(new SaveItemListener());
    saveItem.setBackground(Color.WHITE);
    saveItem.setFont(new Font("宋体", Font.PLAIN, 12));
    menu1.add(saveItem);
    menu1.addSeparator();
    
    saveDialog = new FileDialog(frame, "保存文件", FileDialog.SAVE);
    
    exitItem = new JMenuItem("退出(exit)");
    exitItem.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        ifClose();
      }
    });
    exitItem.setBackground(Color.WHITE);
    exitItem.setFont(new Font("宋体", Font.PLAIN, 12));
    menu1.add(exitItem);
    
    menu2 = new JMenu("空域增强");
    menu2.setBackground(Color.WHITE);
    menu2.setForeground(Color.BLACK);
    menu2.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.add(menu2);
    
    menu2_1 = new JMenuItem("对比度扩展");
    menu2_1.setBackground(Color.WHITE);
    menu2_1.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2.add(menu2_1);
    menu2.addSeparator();
    
    menu2_2 = new JMenuItem("直方图变换增强");
    menu2_2.setBackground(Color.WHITE);
    menu2_2.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2.add(menu2_2);
    
    menu2_3 = new JMenuItem("显示直方图");
    menu2_3.setBackground(Color.WHITE);
    menu2_3.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2.add(menu2_3);
    menu2.addSeparator();
    
    menu2_4 = new JMenuItem("阈值滤波");
    menu2_4.setBackground(Color.WHITE);
    menu2_4.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2.add(menu2_4);
    
    menu2_5 = new JMenuItem("均值滤波");
    menu2_5.setBackground(Color.WHITE);
    menu2_5.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2.add(menu2_5);
    menu2.addSeparator();
    
    menu2_6 = new JMenuItem("中值滤波");
    menu2_6.setBackground(Color.WHITE);
    menu2_6.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2.add(menu2_6);
    menu2.addSeparator();
    
    menu2_m = new JMenu("模板滤波");
    menu2_m.setBackground(Color.WHITE);
    menu2_m.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2.add(menu2_m);
    
    menu2_m_1 = new JMenuItem("低通滤波");
    menu2_m_1.setBackground(Color.WHITE);
    menu2_m_1.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2_m.add(menu2_m_1);
    
    menu2_m_2 = new JMenuItem("高通滤波");
    menu2_m_2.setBackground(Color.WHITE);
    menu2_m_2.setFont(new Font("宋体", Font.PLAIN, 12));
    menu2_m.add(menu2_m_2);
    
    menu3 = new JMenu("频域增强");
    menu3.setBackground(Color.WHITE);
    menu3.setForeground(Color.BLACK);
    menu3.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.add(menu3);
    
    menu3_1 = new JMenuItem("Butterworth低通滤波");
    menu3_1.setBackground(Color.WHITE);
    menu3_1.setFont(new Font("宋体", Font.PLAIN, 12));
    menu3.add(menu3_1);
    
    menu3_2 = new JMenuItem("Butterworth高通滤波");
    menu3_2.setBackground(Color.WHITE);
    menu3_2.setFont(new Font("宋体", Font.PLAIN, 12));
    menu3.add(menu3_2);
    menu3.addSeparator();
    
    menu3_3 = new JMenuItem("指数低通滤波");
    menu3_3.setBackground(Color.WHITE);
    menu3_3.setFont(new Font("宋体", Font.PLAIN, 12));
    menu3.add(menu3_3);
    
    menu3_4 = new JMenuItem("指数高通滤波");
    menu3_4.setBackground(Color.WHITE);
    menu3_4.setFont(new Font("宋体", Font.PLAIN, 12));
    menu3.add(menu3_4);
    
    menu4 = new JMenu("图像锐化");
    menu4.setBackground(Color.WHITE);
    menu4.setForeground(Color.BLACK);
    menu4.setFont(new Font("宋体", Font.PLAIN, 14));
    menuBar.add(menu4);
    
    menu4_1 = new JMenuItem("Kirsch算子");
    menu4_1.setBackground(Color.WHITE);
    menu4_1.setFont(new Font("宋体", Font.PLAIN, 12));
    menu4.add(menu4_1);
    menu4.addSeparator();
    
    menu4_2 = new JMenuItem("Laplace算子");
    menu4_2.setBackground(Color.WHITE);
    menu4_2.setFont(new Font("宋体", Font.PLAIN, 12));
    menu4.add(menu4_2);
    menu4.addSeparator();
    
    menu4_3 = new JMenuItem("Prewitt算子");
    menu4_3.setBackground(Color.WHITE);
    menu4_3.setFont(new Font("宋体", Font.PLAIN, 12));
    menu4.add(menu4_3);
    menu4.addSeparator();
    
    menu4_4 = new JMenuItem("Roberts算子");
    menu4_4.setBackground(Color.WHITE);
    menu4_4.setFont(new Font("宋体", Font.PLAIN, 12));
    menu4.add(menu4_4);
    menu4.addSeparator();
    
    menu4_5 = new JMenuItem("Sobel算子");
    menu4_5.setBackground(Color.WHITE);
    menu4_5.setFont(new Font("宋体", Font.PLAIN, 12));
    menu4.add(menu4_5);
  }
  /**
   * 是否退出系统
   */
  private void ifClose() {
    int num = JOptionPane.showConfirmDialog(null, "确认退出系统？", "提示", JOptionPane.YES_NO_OPTION);
    if(num == JOptionPane.YES_OPTION) {
      System.exit(0);
    }
  }
  /**
   * 打开菜单事件
   * @author loyer
   *  
   */
  private class OpenItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      openDialog.setVisible(true);
      String dirPath = openDialog.getDirectory();  //获取路径
      String fileName = openDialog.getFile(); //获取文件名
      if(dirPath == null || fileName == null) {
        return;
      }
      ImageViewTool imgView = new ImageViewTool(dirPath, fileName);
      imgView.frame.setVisible(true);
      
    }
  }
  /**
   * 打开菜单事件
   * @author loyer
   *  
   */
  private class SaveItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      saveDialog.setVisible(true);
      String dirPath = saveDialog.getDirectory();  //获取路径
      String fileName = saveDialog.getFile(); //获取文件名
      if(dirPath == null || fileName == null) {
        return;
      }
      File file = new File(dirPath, fileName);
      try {
        BufferedWriter bW = new BufferedWriter(new FileWriter(file));
        //OutputStreamWriter ow = new OutputStreamWriter(new outP)
      } catch (IOException e1) {
        throw new RuntimeException("文件保存失败");
      }
      
    }
  }
  
}
