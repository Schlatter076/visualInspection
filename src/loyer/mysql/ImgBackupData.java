package loyer.mysql;

/**
 * imgbackup表的实体
 * @author loyer
 * @coding UTF-8
 */
public class ImgBackupData {
  private int keyID;
  private String imgName;
  private String date;
  
  public ImgBackupData() {}
  public ImgBackupData(int id, String name, String date) {
    setKeyID(id);
    setImgName(name);
    setDate(date);
  }
  public int getKeyID() {
    return keyID;
  }
  public void setKeyID(int keyID) {
    this.keyID = keyID;
  }
  public String getImgName() {
    return imgName;
  }
  public void setImgName(String imgName) {
    this.imgName = imgName;
  }
  public String getDate() {
    return date;
  }
  public void setDate(String date) {
    this.date = date;
  }
}
