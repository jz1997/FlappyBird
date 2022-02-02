package com.game.demo;

import com.game.demo.exception.ResourceLoadException;
import com.game.demo.util.GameUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * BottomPipe:
 *
 * @author Jiang
 * @since 2022/2/1 1:37 PM
 */
public class BottomPipe {

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

  private final int pipeBodyCount;
  private final int realHeight;

  private final Rectangle rect;

  // 初始化静态变量
  static {
    String normalImgPath = Constant.PIPE_IMG_PATH[0];
    String headImgPath = Constant.PIPE_IMG_PATH[2];

    NORMAL_IMAGE = GameUtil.loadImageFrom(normalImgPath);
    HEAD_IMAGE = GameUtil.loadImageFrom(headImgPath);

    if (NORMAL_IMAGE == null || HEAD_IMAGE == null) {
      throw new ResourceLoadException(
        "资源不存在: " + normalImgPath + ", " + headImgPath);
    }
  }


  public BottomPipe(int x, int height, int speed) {
    this.speed = speed;
    this.height = height;
    this.x = x;

    PIPE_WIDTH = NORMAL_IMAGE.getWidth();
    PIPE_HEIGHT = NORMAL_IMAGE.getHeight();
    PIPE_HEAD_WIDTH = HEAD_IMAGE.getWidth();
    PIPE_HEAD_HEIGHT = HEAD_IMAGE.getHeight();

    // 初始化 rectangle
    this.pipeBodyCount = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
    this.realHeight = pipeBodyCount * PIPE_HEIGHT + PIPE_HEAD_HEIGHT;
    this.rect = new Rectangle(x, Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - realHeight,
      PIPE_WIDTH, realHeight);
  }

  public void draw(Graphics g) {
    // 绘制管体
    for (int i = 0; i < this.pipeBodyCount; i++) {
      g.drawImage(NORMAL_IMAGE, x,
        Constant.FRAME_HEIGHT - (i + 1) * PIPE_HEIGHT - Constant.GROUND_HEIGHT, null);
    }
    // 绘制头部
    g.drawImage(HEAD_IMAGE, x - (Math.abs(PIPE_WIDTH - PIPE_HEAD_WIDTH) >> 1),
      Constant.FRAME_HEIGHT - realHeight - Constant.GROUND_HEIGHT, null);

    // 绘制矩形
    g.setColor(Color.red);
    g.drawRect(rect.x, rect.y, rect.width, rect.height);
  }

  public void movement() {
    this.x -= this.speed;
    this.rect.x -= this.speed;
  }

  /**
   * 价差矩形是否碰撞
   *
   * @param r {@link Rectangle} 目标矩形
   * @return true 碰撞了、false 未碰撞
   */
  public boolean isCollide(Rectangle r) {
    return this.rect.intersects(r);
  }

  public boolean isOutOfFrame() {
    return this.x < -1 * PIPE_WIDTH;
  }

  public boolean isInFrame() {
    return this.x < Constant.FRAME_WIDTH - PIPE_WIDTH;
  }

  public int getRightX() {
    return x + Math.max(PIPE_WIDTH, PIPE_HEAD_WIDTH);
  }
}
