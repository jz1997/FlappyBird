package com.game.demo;

import com.game.demo.exception.ResourceLoadException;
import com.game.demo.util.GameUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * TopPipe: 上面的管道
 *
 * @author Jiang
 * @since 2022/2/1 12:42 PM
 */
public class TopPipe {

  /**
   * 水管管体图片
   */
  private static final BufferedImage NORMAL_IMAGE;

  /**
   * 水管头部图片
   */
  private static final BufferedImage HEAD_IMAGE;

  private final int PIPE_WIDTH;
  private final int PIPE_HEIGHT;
  private final int PIPE_HEAD_WIDTH;
  private final int PIPE_HEAD_HEIGHT;

  /**
   * x 坐标
   */
  private int x;

  private int speed;

  private final int height;


  private final Rectangle rect;

  static {
    String normalPath = Constant.PIPE_IMG_PATH[0];
    String headPath = Constant.PIPE_IMG_PATH[1];
    NORMAL_IMAGE = GameUtil.loadImageFrom(normalPath);
    HEAD_IMAGE = GameUtil.loadImageFrom(headPath);
    if (NORMAL_IMAGE == null || HEAD_IMAGE == null) {
      throw new ResourceLoadException(
        "资源不存在: " + normalPath + ", " + headPath);
    }
  }


  public TopPipe(int x, int height, int speed) {
    this.speed = speed;
    this.height = height;
    this.x = x;

    PIPE_WIDTH = NORMAL_IMAGE.getWidth();
    PIPE_HEIGHT = NORMAL_IMAGE.getHeight();
    PIPE_HEAD_WIDTH = HEAD_IMAGE.getWidth();
    PIPE_HEAD_HEIGHT = HEAD_IMAGE.getHeight();

    // 设置碰撞矩形
    int realHeight =
      ((height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1) * PIPE_HEIGHT + PIPE_HEAD_HEIGHT;
    this.rect = new Rectangle(x, 0, PIPE_WIDTH, realHeight);
  }

  public void draw(Graphics g) {
    int normalPipeCount = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
    // 绘制管体
    for (int i = 0; i < normalPipeCount; i++) {
      g.drawImage(NORMAL_IMAGE, x, i * PIPE_HEIGHT, null);
    }
    // 绘制头部
    g.drawImage(HEAD_IMAGE, x - (Math.abs(PIPE_WIDTH - PIPE_HEAD_WIDTH) >> 1),
      normalPipeCount * PIPE_HEIGHT, null);

    g.setColor(Color.red);
    g.drawRect(rect.x, rect.y, rect.width, rect.height);
  }

  public void movement() {
    this.x -= this.speed;
    this.rect.x -= this.speed;
  }

  /**
   * 检查是否碰撞
   *
   * @param r {@link Rectangle} 目标矩形
   * @return true 碰撞了 false 未碰撞
   */
  public boolean isCollide(Rectangle r) {
    return this.rect.intersects(r);
  }

  public boolean isOutOfFrame() {
    return this.x < -1 * PIPE_WIDTH;
  }

  public boolean isInFrame() {
    return this.x < Constant.FRAME_WIDTH - this.PIPE_WIDTH;
  }

  public int getPipeWidth() {
    return this.PIPE_WIDTH;
  }

  public int getRightX() {
    return x + Math.max(PIPE_WIDTH, PIPE_HEAD_WIDTH);
  }
}
