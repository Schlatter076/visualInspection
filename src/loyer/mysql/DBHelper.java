package loyer.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * 数据库帮助类
 * @author loyer
 * @coding UTF-8
 */
public class DBHelper {
  
  private static final String URL = "jdbc:mysql://localhost:3306/vitualinspection?useSSL=false&serverTimezone=UTC";
  private static final String USER = "root";
  private static final String PWD = "123456";
  private static PreparedStatement ptmt = null;
  private static Connection conn = null;
  private static ResultSet rs = null;

  /**
   * 获取文件
   * 
   * @param inFile
   * @return
   * @throws FileNotFoundException
   */
  public static FileInputStream getImageByte(String inFile) throws FileNotFoundException {
    FileInputStream imageByte = null;
    File file = new File(inFile);
    imageByte = new FileInputStream(file);
    return imageByte;
  }
  /**
   * 把图片存进数据库visualinspection
   * @param filePath
   * @param imgName
   * @return
   */
  public static boolean writeToDB(String filePath, String imgName) {
    try {
      Connection conn = getConnection();
      InputStream in = getImageByte(filePath);
      String sql = "insert into imgbackup(fname,fcontent,date) values(?, ?, ?)";
      PreparedStatement ptmt = conn.prepareStatement(sql);
      
      ptmt.setString(1, imgName);
      ptmt.setBinaryStream(2, in, in.available());
      ptmt.setString(3, LocalDate.now().toString());
      ptmt.execute();
      return true;
    } catch (SQLException | IOException e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * 从数据库中读出指定的图片
   * @param id keyID
   * @param date 日期
   * @param filePath 文件存储路径，如："C:\\test\\1.jpg"存储到C盘test文件夹下，或"resource/0.png"存储到项目resource文件夹
   * @return 布尔值
   * @throws Exception
   */
  public static boolean readFromDB(int id, String date, String filePath) throws Exception{
    try {
      DBHelper.getConnection();
      String sql = "select * from imgbackup where keyID='"+id+"'and date='"+date+"'";
      ResultSet res = Search(sql, null);
      if (res.next()) {
        InputStream inputStream = res.getBinaryStream("fcontent");
        
        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
          fileOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        fileOutputStream.close();
      }
      return true;
    } catch(IOException | SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * 连接到数据库
   * 
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public static Connection getConnection() {
    Connection connection = null; // 连接对象
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(URL, USER, PWD);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  /**
   * 提供数据库查询方法
   * 
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public static ResultSet Search(String sql, String str[]) throws Exception {
    try {
      conn = getConnection();
      ptmt = conn.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          ptmt.setString(i + 1, str[i]);
        }
      }
      rs = ptmt.executeQuery();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return rs;
  }

  /**
   * 提供数据库增删修改方法
   * 
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public static int AddU(String sql, String str[]) throws Exception {
    int getBack = 0;
    try {
      conn = getConnection();
      ptmt = conn.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          ptmt.setString(i + 1, str[i]);
        }
      }
      getBack = ptmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return getBack;
  }

  /**
   * 数据库关闭连接方法
   * 
   * @param res
   * @param pstmt
   * @param connection
   */
  public static void close(ResultSet res, PreparedStatement pstmt, Connection connection) {
    if (res != null) {
      try {
        res.close();
        res = null;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (pstmt != null) {
      try {
        pstmt.close();
        pstmt = null;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (connection != null) {
      try {
        connection.close();
        connection = null;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
