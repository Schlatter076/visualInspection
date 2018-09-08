package loyer.test;

import loyer.mysql.DBHelper;

public class Test {

  public static void main(String[] args) throws Exception {
    /*String filePath = "resource/run.jpg";
    String imgName = "flower";
    DBHelper.writeToDB(filePath, imgName);*/
    DBHelper.readFromDB(1, "2018-09-08", "resource/copy.jpg");
    
  }

}
