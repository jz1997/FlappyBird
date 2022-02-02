package com.game.demo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Cloud:
 *
 * @author Jiang
 * @since 2022/1/31 4:57 PM
 */
public class Cloud {

  private int x;
  private final int y;

  private int scaleImgWidth;
  private int scaleImgHeight;

  private int speed;

  private BufferedImage image;

  public Cloud(BufferedImage image, int x, int y) {
    this.speed = Constant.GAME_SPEED * 2;
    this.image = image;
    this.x = x;
    this.y = y;

    // 设置云朵缩放比例 1.0 ~ 2.0
    this.initScaleImg();
  }

  public void draw(Graphics g, Bird bird) {
    movement(bird);
    g.drawImage(this.image, x, y, this.scaleImgWidth, this.scaleImgHeight, null);
  }

  private void movement(Bird bird) {
    if (bird.isDead()) {
      this.speed = 1;
    }
    this.x -= this.speed;
  }

  private void initScaleImg() {
    double scale = Math.random() + 1;
    this.scaleImgWidth = (int) (scale * this.image.getHeight());
    this.scaleImgHeight = (int) (scale * this.image.getHeight());
  }

  public boolean isOutOfFrame() {
    return this.x < -1 * this.image.getWidth();
  }
}
