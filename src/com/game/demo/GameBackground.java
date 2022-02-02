package com.game.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * GameBackground: 游戏背景
 *
 * @author Jiang
 * @since 2022/1/28 4:29 PM
 */
public class GameBackground {

  private static BufferedImage backgroundImg;
  public static int BACKGROUND_HEIGHT;

  private final int speed;

  private int layerX;

  static {
    try {
      backgroundImg = ImageIO.read(new FileInputStream("resources/img/background.png"));
      BACKGROUND_HEIGHT = backgroundImg.getHeight();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public GameBackground() {
    this.speed = Constant.GAME_SPEED;
    this.layerX = 0;
  }

  public void draw(Graphics g, Bird bird) {
    g.setColor(new Color(0x4bc4cf));
    g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

    int imgWidth = backgroundImg.getWidth();
    int imgHeight = backgroundImg.getHeight();

    int drawCount = Constant.FRAME_WIDTH / imgWidth + 2;

    for (int i = 0; i < drawCount; i++) {
      g.drawImage(backgroundImg, imgWidth * i - this.layerX, Constant.FRAME_HEIGHT - imgHeight,
        null);
    }

    if (bird.isDead()) {
      return;
    }

    this.move();
  }

  private void move() {
    this.layerX += speed;
    if (this.layerX > backgroundImg.getWidth()) {
      this.layerX = 0;
    }
  }
}
