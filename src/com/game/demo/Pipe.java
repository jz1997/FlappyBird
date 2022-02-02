package com.game.demo;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Pipe: 水管图片
 *
 * @author Jiang
 * @since 2022/1/31 6:21 PM
 */
public class Pipe {

  private final TopPipe topPipe;

  private final BottomPipe bottomPipe;

  /**
   * 是否已经记录分数
   */
  private boolean isScore;

  // 最小高度 1 / 8
  public static int MIN_HEIGHT = Constant.FRAME_HEIGHT >> 3;
  // 最大高度 5 / 8
  public static int MAX_HEIGHT = (Constant.FRAME_HEIGHT >> 3) * 5;

  public Pipe(int x, int topHeight) {
    int bottomHeight = this.getBottomHeight(topHeight);
    this.topPipe = new TopPipe(x, topHeight, Constant.GAME_SPEED);
    this.bottomPipe = new BottomPipe(x, bottomHeight, Constant.GAME_SPEED);
  }

  public void draw(Graphics g, Bird bird) {
    this.topPipe.draw(g);
    this.bottomPipe.draw(g);

    if (bird.isDead()) {
      return;
    }
    this.topPipe.movement();
    this.bottomPipe.movement();
  }

  /**
   * 检查是否碰撞, top、bottom pipe 其中一个碰撞了就算碰撞
   *
   * @param r 检测区域
   * @return true 碰撞、false 不碰撞
   */
  public boolean isCollied(Rectangle r) {
    return this.topPipe.isCollide(r) || this.bottomPipe.isCollide(r);
  }

  public boolean isOutOfFrame() {
    return topPipe.isOutOfFrame() && bottomPipe.isOutOfFrame();
  }

  public boolean isInFrame() {
    return topPipe.isInFrame() && bottomPipe.isInFrame();
  }

  private int getBottomHeight(int topHeight) {
    return Constant.FRAME_HEIGHT - Constant.TOP_BAR_HEIGHT - Constant.GROUND_HEIGHT -
      (Constant.FRAME_HEIGHT >> 2) - topHeight;
  }

  public boolean isScore() {
    return isScore;
  }

  public void setScore(boolean score) {
    isScore = score;
  }

  public void setScored() {
    isScore = true;
  }

  public int getRightX() {
    return Math.max(topPipe.getRightX(), bottomPipe.getRightX());
  }
}
