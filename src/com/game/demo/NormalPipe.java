package com.game.demo;

import com.game.demo.util.GameUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Pipe: 水管图片
 *
 * @author Jiang
 * @since 2022/1/31 6:21 PM
 */
public class NormalPipe extends AbstractPipe {

  // 上下水管高度
  private final int topHeight;
  private final int bottomHeight;


  // 最小高度 1 / 8
  public static int MIN_HEIGHT = Constant.FRAME_HEIGHT >> 3;
  // 最大高度 5 / 8
  public static int MAX_HEIGHT = (Constant.FRAME_HEIGHT >> 3) * 5;

  // 碰撞矩形
  private Rectangle topRectangle;
  private Rectangle bottomRectangle;

  public NormalPipe(int x, int topHeight) {
    super(x);

    this.topHeight = topHeight;
    // 下部分的高度为 窗口高度 - 上部分水管高度 - 地面高度 - 间距
    this.bottomHeight = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT
      - TOP_BOTTOM_SPACING - getRealHeight(topHeight);

    this.initRectangle();
  }

  public static NormalPipe createRandomPipe(int x) {
    int topHeight = GameUtil.getRandomNumber(NormalPipe.MIN_HEIGHT, NormalPipe.MAX_HEIGHT);
    return new NormalPipe(x, topHeight);
  }

  private void initRectangle() {
    this.topRectangle = new Rectangle(x, 0, getBodyWidth(), getRealHeight(topHeight));
    int startY = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - getRealHeight(bottomHeight);
    this.bottomRectangle = new Rectangle(x, startY, getBodyWidth(), getRealHeight(bottomHeight));
  }

  @Override
  public void drawPipe(Graphics g, Bird bird) {
    // 绘制上部水管
    this.drawTop(g);
    // 绘制下部分水管
    this.drawBottom(g);

    g.setColor(Color.red);
    g.drawRect(topRectangle.x, topRectangle.y, topRectangle.width, topRectangle.height);
    g.drawRect(bottomRectangle.x, bottomRectangle.y, bottomRectangle.width, bottomRectangle.height);
  }

  /**
   * 绘制上部分水管
   *
   * @param g 画笔
   */
  private void drawTop(Graphics g) {
    int bodyCount = getBodyCount(topHeight);
    int headXOffset = Math.abs(getHeadWidth() - getBodyWidth()) / 2;
    // draw pipe body
    for (int i = 0; i < bodyCount; i++) {
      g.drawImage(BODY_IMG, x, i * getBodyHeight(), null);
    }
    g.drawImage(TOP_HEAD_IMG, x - headXOffset, getBodyHeight() * bodyCount, null);
  }

  /**
   * 绘制下部分水管
   *
   * @param g 画笔
   */
  private void drawBottom(Graphics g) {
    int bodyCount = getBodyCount(bottomHeight);
    int headXOffset = Math.abs(getHeadWidth() - getBodyWidth()) / 2;
    int endY = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT;

    // draw pipe body
    for (int i = 0; i < bodyCount; i++) {
      g.drawImage(BODY_IMG, x, endY - (i + 1) * getBodyHeight(), null);
    }

    // draw head pipe
    g.drawImage(BOTTOM_HEAD_IMG, x - headXOffset,
      endY - bodyCount * getBodyHeight() - getHeadHeight(), null);
  }

  @Override
  public void movement(Bird bird) {
    if (bird.isDead()) {
      return;
    }
    this.x -= Constant.GAME_SPEED;
    this.topRectangle.x -= Constant.GAME_SPEED;
    this.bottomRectangle.x -= Constant.GAME_SPEED;
  }

  @Override
  public boolean isCollied(Rectangle rect) {
    return topRectangle.intersects(rect) || bottomRectangle.intersects(rect);
  }

  private int getRealHeight(int height) {
    int bodyCount = getBodyCount(height);
    return bodyCount * getBodyHeight() + getHeadHeight();
  }

  private int getBodyCount(int topHeight) {
    return (topHeight - getHeadHeight()) / getBodyHeight() + 1;
  }
}
