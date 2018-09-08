package loyer.imgBasic;

import static java.lang.System.out;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图像处理工具类
 * @author loyer
 * @coding UTF-8
 */
public class ImgHelper {
  /**
   * 获取图像像素
   * 
   * @param filePath
   *          图像所在路径
   * @return 像素值的二维数组
   */
  public static int[][] getImageRGB(String filePath) {
    int[][] result = null;
    File file = new File(filePath);
    if (!file.exists()) {
      out.println("文件不存在!");
    }
    try {
      BufferedImage bufImg = ImageIO.read(file);
      int width = bufImg.getWidth();
      int height = bufImg.getHeight();
      result = new int[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          result[i][j] = bufImg.getRGB(j, i) & 0xFFFFFF;
        }
      }

    } catch (IOException e) {
      out.println("获取像素失败：" + e.getMessage());
    }
    return result;
  }

  /**
   * 获取sRGB中的RGB值，并返回一个Color对象
   * 
   * @param filePath
   *          要获取的对象
   * @param x
   *          宽
   * @param y
   *          高
   * @return
   */
  public static Color getColor(String filePath, int x, int y) {
    Color color = null;

    File file = new File(filePath);
    if (!file.exists()) {
      out.println("文件不存在!");
    }
    try {
      BufferedImage image = ImageIO.read(file);
      int rgb = image.getRGB(x, y);
      int int8 = (int) Math.pow(2, 8); // 前8位
      int int16 = (int) Math.pow(2, 16); // 前16位
      int int24 = (int) Math.pow(2, 24); // 前24位
      // 分别取0-7位,8-15位,16-23位
      int r = (rgb & (int24 - int16)) >> 16;
      int g = (rgb & (int16 - int8)) >> 8;
      int b = (rgb & (int8 - 1));
      color = new Color(r, g, b);
    } catch (IOException e) {
      out.println("读取文件失败:" + e.getMessage());
    }
    return color;
  }

  /**
   * 获取压缩后的图像
   * 
   * @param imgFile
   *          要压缩的图像
   * @param resizeTimes
   *          压缩倍数
   * @return 压缩后的图像
   */
  public static BufferedImage zoomImage(String imgFile, float resizeTimes) {
    BufferedImage result = null;
    try {
      File file = new File(imgFile);
      if (!file.exists()) {
        out.println("文件不存在");
      }
      BufferedImage img = ImageIO.read(file);
      int height = (int) (img.getHeight() * resizeTimes); // 压缩后的高
      int width = (int) (img.getWidth() * resizeTimes); // 压缩后的宽
      // 生成新图片
      result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      result.getGraphics().drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

    } catch (IOException e) {
      out.println("创建缩略图异常:" + e.getMessage());
    }
    return result;
  }

  /**
   * Image转换成BufferedImage
   * 
   * @param img
   *          待转换的Image对象
   * @return 转换后的BufferedImage对象
   */
  public static BufferedImage convertToBufferedImage(Image img) {
    if (img instanceof BufferedImage) {
      return (BufferedImage) img;
    }
    BufferedImage buffImg = new BufferedImage(img.getHeight(null), img.getWidth(null), BufferedImage.TYPE_INT_ARGB);
    // 将img画到buffImg上
    Graphics2D graph = buffImg.createGraphics();
    graph.drawImage(buffImg, 0, 0, null);
    graph.dispose();

    return buffImg;
  }

  /**
   * 灰度化
   * 
   * @param buffImg
   * @return
   */
  public static BufferedImage grayImage(BufferedImage buffImg) {
    int width = buffImg.getWidth();
    int height = buffImg.getHeight();
    BufferedImage grayBuffImg = new BufferedImage(width, height, buffImg.getType());
    for (int i = 0; i < buffImg.getWidth(); i++) {
      for (int j = 0; j < buffImg.getHeight(); j++) {
        int color = buffImg.getRGB(i, j);
        int r = (color >> 16) & 0xFF; // 取RGB值16-24位
        int g = (color >> 8) & 0xFF; // 取RGB值8-15位
        int b = color & 0xFF; // 取RGB值0-7位
        int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b); // 这里的参数为固定值
        int newPixel = colorToRGB(255, gray, gray, gray);
        grayBuffImg.setRGB(i, j, newPixel);
      }
    }
    return grayBuffImg;
  }

  /**
   * 颜色分量转为RGB值(共32位，前八位为Alpha值，后依次为Red Green Blue)
   * 
   * @param alpha
   * @param red
   * @param green
   * @param blue
   * @return
   */
  private static int colorToRGB(int alpha, int red, int green, int blue) {

    int newPixel = 0;
    newPixel += alpha;
    newPixel = newPixel << 8;
    newPixel += red;
    newPixel = newPixel << 8;
    newPixel += green;
    newPixel = newPixel << 8;
    newPixel += blue;

    return newPixel;
  }

  /**
   * 二值化
   * 
   * @param buffImg
   *          待二值化的图像
   * @return 二值化后的图像
   */
  public static BufferedImage binaryImage(BufferedImage buffImg) {

    int width = buffImg.getWidth();
    int height = buffImg.getHeight();
    float[] rgb = new float[3];
    double[][] point = new double[width][height];
    // 定义黑白量
    final int black = new Color(0, 0, 0).getRGB();
    final int white = new Color(255, 255, 255).getRGB();
    BufferedImage binaryImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int pixel = buffImg.getRGB(i, j);
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        float avg = (rgb[0] + rgb[1] + rgb[2]) / 3;
        point[i][j] = avg;
      }
    }
    // 这里是阈值，白底黑字还是黑底白字，大多数情况下建议白底黑字，后面都以白底黑字为例
    double SW = 192;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (point[x][y] < SW) {
          binaryImg.setRGB(x, y, black);
        } else {
          binaryImg.setRGB(x, y, white);
        }
      }
    }
    return binaryImg;
  }

  /**
   * 自己加周围8个灰度值再除以9，算出其相对灰度值
   * 
   * @param point
   *          坐标
   * @param x
   *          坐标x
   * @param y
   *          坐标y
   * @param w
   *          宽
   * @param h
   *          高
   * @return 相对灰度值
   */
  public static double getGray(double[][] point, int x, int y, int w, int h) {
    double rs = point[x][y] + (x == 0 ? 255 : point[x - 1][y]) + (x == 0 || y == 0 ? 255 : point[x - 1][y - 1])
        + (x == 0 || y == h - 1 ? 255 : point[x - 1][y + 1]) + (y == 0 ? 255 : point[x][y - 1])
        + (y == h - 1 ? 255 : point[x][y + 1]) + (x == w - 1 ? 255 : point[x + 1][y])
        + (x == w - 1 || y == 0 ? 255 : point[x + 1][y - 1]) + (x == w - 1 || y == h - 1 ? 255 : point[x + 1][y + 1]);
    return rs / 9;
  }

  /*
   * 降噪（领域检测法），针对二值化后的图片来说(白底黑字)，噪点就是图片中一堆密集黑色像素点中少许白色像素点(字体里的白点或背景里的黑点)，
   * 这时，解决方法可以遍历像素点，一个像素点周围有8个像素点，如果这个像素点周围有6个以上像素点是黑色就可以把这个像素点也认为是黑色：
   */
  /**
   * 降噪，以1个像素点为单位（实际使用中可以循环降噪，或者把单位可以扩大为多个像素点）
   * @param image 
   * @return
   */
  public static BufferedImage denoise(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
    int white = new Color(255, 255, 255).getRGB();

    if (isWhite(image.getRGB(1, 0)) && isWhite(image.getRGB(0, 1)) && isWhite(image.getRGB(1, 1))) {
      image.setRGB(0, 0, white);
    }
    if (isWhite(image.getRGB(w - 2, 0)) && isWhite(image.getRGB(w - 1, 1)) && isWhite(image.getRGB(w - 2, 1))) {
      image.setRGB(w - 1, 0, white);
    }
    if (isWhite(image.getRGB(0, h - 2)) && isWhite(image.getRGB(1, h - 1)) && isWhite(image.getRGB(1, h - 2))) {
      image.setRGB(0, h - 1, white);
    }
    if (isWhite(image.getRGB(w - 2, h - 1)) && isWhite(image.getRGB(w - 1, h - 2))
        && isWhite(image.getRGB(w - 2, h - 2))) {
      image.setRGB(w - 1, h - 1, white);
    }

    for (int x = 1; x < w - 1; x++) {
      int y = 0;
      if (isBlack(image.getRGB(x, y))) {
        int size = 0;
        if (isWhite(image.getRGB(x - 1, y))) {
          size++;
        }
        if (isWhite(image.getRGB(x + 1, y))) {
          size++;
        }
        if (isWhite(image.getRGB(x, y + 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x - 1, y + 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x + 1, y + 1))) {
          size++;
        }
        if (size >= 5) {
          image.setRGB(x, y, white);
        }
      }
    }
    for (int x = 1; x < w - 1; x++) {
      int y = h - 1;
      if (isBlack(image.getRGB(x, y))) {
        int size = 0;
        if (isWhite(image.getRGB(x - 1, y))) {
          size++;
        }
        if (isWhite(image.getRGB(x + 1, y))) {
          size++;
        }
        if (isWhite(image.getRGB(x, y - 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x + 1, y - 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x - 1, y - 1))) {
          size++;
        }
        if (size >= 5) {
          image.setRGB(x, y, white);
        }
      }
    }

    for (int y = 1; y < h - 1; y++) {
      int x = 0;
      if (isBlack(image.getRGB(x, y))) {
        int size = 0;
        if (isWhite(image.getRGB(x + 1, y))) {
          size++;
        }
        if (isWhite(image.getRGB(x, y + 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x, y - 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x + 1, y - 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x + 1, y + 1))) {
          size++;
        }
        if (size >= 5) {
          image.setRGB(x, y, white);
        }
      }
    }

    for (int y = 1; y < h - 1; y++) {
      int x = w - 1;
      if (isBlack(image.getRGB(x, y))) {
        int size = 0;
        if (isWhite(image.getRGB(x - 1, y))) {
          size++;
        }
        if (isWhite(image.getRGB(x, y + 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x, y - 1))) {
          size++;
        }
        // 斜上下为空时，去掉此点
        if (isWhite(image.getRGB(x - 1, y + 1))) {
          size++;
        }
        if (isWhite(image.getRGB(x - 1, y - 1))) {
          size++;
        }
        if (size >= 5) {
          image.setRGB(x, y, white);
        }
      }
    }

    // 降噪，以1个像素点为单位
    for (int y = 1; y < h - 1; y++) {
      for (int x = 1; x < w - 1; x++) {
        if (isBlack(image.getRGB(x, y))) {
          int size = 0;
          // 上下左右均为空时，去掉此点
          if (isWhite(image.getRGB(x - 1, y))) {
            size++;
          }
          if (isWhite(image.getRGB(x + 1, y))) {
            size++;
          }
          // 上下均为空时，去掉此点
          if (isWhite(image.getRGB(x, y + 1))) {
            size++;
          }
          if (isWhite(image.getRGB(x, y - 1))) {
            size++;
          }
          // 斜上下为空时，去掉此点
          if (isWhite(image.getRGB(x - 1, y + 1))) {
            size++;
          }
          if (isWhite(image.getRGB(x + 1, y - 1))) {
            size++;
          }
          if (isWhite(image.getRGB(x + 1, y + 1))) {
            size++;
          }
          if (isWhite(image.getRGB(x - 1, y - 1))) {
            size++;
          }
          if (size >= 8) {
            image.setRGB(x, y, white);
          }
        }
      }
    }
    return image;
  }

  public static boolean isBlack(int colorInt) {
    Color color = new Color(colorInt);
    if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
      return true;
    }
    return false;
  }

  public static boolean isWhite(int colorInt) {
    Color color = new Color(colorInt);
    if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
      return true;
    }
    return false;
  }

  public static int isBlack(int colorInt, int whiteThreshold) {
    final Color color = new Color(colorInt);
    if (color.getRed() + color.getGreen() + color.getBlue() <= whiteThreshold) {
      return 1;
    }
    return 0;
  }
}
