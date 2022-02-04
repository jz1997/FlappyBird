package com.game.demo.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * GameUtil:
 *
 * @author Jiang
 * @since 2022/1/30 1:43 PM
 */
public class GameUtil {

  private final static Logger log = Logger.getLogger("GameUtil");

  public static BufferedImage loadImageFrom(String path) {
    BufferedImage image;
    try {
      image = ImageIO.read(new FileInputStream(path));
    } catch (IOException e) {
      log.severe(() -> String.format(
        "loadImageFrom() called with exception => parameters = [path = %s], exception = %s", path,
        e));
      return null;
    }
    return image;
  }

  public static int getStringWidth(Font font, String str) {
    FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
    Rectangle2D stringBounds = font.getStringBounds(str, frc);
    return (int) stringBounds.getWidth();
  }

  public static int getStringHeight(Font font, String str) {
    FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
    Rectangle2D stringBounds = font.getStringBounds(str, frc);
    return (int) stringBounds.getWidth();
  }

  /**
   * 生成 [min, max) 之间的随机数
   *
   * @param min 最小值
   * @param max 最大值
   * @return [min, max) 之间的随机数
   */
  public static int getRandomNumber(int min, int max) {
    return (int) (Math.random() * (max - min) + min);
  }
}
