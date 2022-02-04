package com.game.demo;

import com.game.demo.exception.ResourceLoadException;
import com.game.demo.util.GameUtil;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * AbstractPipe: 水管抽象类
 *
 * @author Jiang
 * @since 2022/2/4 1:00 AM
 */
public abstract class AbstractPipe {

  protected static final BufferedImage TOP_HEAD_IMG;
  protected static final BufferedImage BODY_IMG;
  protected static final BufferedImage BOTTOM_HEAD_IMG;

  static {
    TOP_HEAD_IMG = GameUtil.loadImageFrom(Constant.PIPE_TOP_HEAD_IMG_PATH);
    BODY_IMG = GameUtil.loadImageFrom(Constant.PIPE_BODY_IMG_PATH);
    BOTTOM_HEAD_IMG = GameUtil.loadImageFrom(Constant.PIPE_BOTTOM_HEAD_IMG_PATH);
    if (TOP_HEAD_IMG == null || BODY_IMG == null || BOTTOM_HEAD_IMG == null) {
      throw new ResourceLoadException("PIPE图片资源部存在");
    }
  }

  protected int x;

  // 该谁管是否已经计分
  protected boolean isScore;

  // 上下管道间距
  protected static final int TOP_BOTTOM_SPACING = Constant.FRAME_HEIGHT / 4;


  public AbstractPipe(int x) {
    this.x = x;
    this.isScore = false;
  }

  /**
   * 绘制水管和移动水管
   *
   * @param g    画笔
   * @param bird 小鸟
   */
  public void draw(Graphics g, Bird bird) {
    this.drawPipe(g, bird);
    this.movement(bird);
  }

  /**
   * 绘制水管
   *
   * @param g    画笔
   * @param bird 小鸟
   */
  public abstract void drawPipe(Graphics g, Bird bird);

  /**
   * 水管移动
   *
   * @param bird 小鸟
   */
  public abstract void movement(Bird bird);

  /**
   * 检查是否碰撞了
   *
   * @param rect {@link Rectangle} /
   * @return /
   */
  public abstract boolean isCollied(Rectangle rect);

  /**
   * 是否完全进入窗口
   *
   * @return /
   */
  public boolean isInFrame() {
    return this.x < Constant.FRAME_WIDTH - Math.max(getBodyWidth(), getHeadWidth());
  }

  /**
   * 是否完全移除窗口
   *
   * @return /
   */
  public boolean isOutOfFrame() {
    return this.x < -1 * Math.max(getBodyWidth(), getHeadWidth());
  }

  /**
   * 获取水管最右边的坐标
   *
   * @return /
   */
  public int getRightX() {
    return this.x + Math.max(getBodyWidth(), getHeadWidth());
  }

  /**
   * 是否可以加分
   *
   * @return /
   */
  public boolean canScore() {
    return !isScore;
  }

  /**
   * 已加分
   */
  public void setScored() {
    this.isScore = true;
  }


  public int getBodyWidth() {
    assert BODY_IMG != null;
    return BODY_IMG.getWidth();
  }

  public int getBodyHeight() {
    assert BODY_IMG != null;
    return BODY_IMG.getHeight();
  }

  public int getHeadWidth() {
    assert TOP_HEAD_IMG != null;
    return TOP_HEAD_IMG.getWidth();
  }

  public int getHeadHeight() {
    assert TOP_HEAD_IMG != null;
    return TOP_HEAD_IMG.getHeight();
  }
}
