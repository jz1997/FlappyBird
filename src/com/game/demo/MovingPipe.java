package com.game.demo;

import com.game.demo.util.GameUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * MovingPipe:
 *
 * @author Jiang
 * @since 2022/2/3 10:58 PM
 */
public class MovingPipe extends AbstractPipe {


  private final int topHeight;
  private final int bottomHeight;

  // 移动管道最大和最小的长度
  public static int MIN_HEIGHT = Constant.FRAME_HEIGHT / 6;
  public static int MAX_HEIGHT = Constant.FRAME_HEIGHT / 4;

  // 上部分水管开始的 Y 坐标范围
  public static int MIN_Y = Constant.TOP_BAR_HEIGHT;
  public static int MAX_Y = Constant.FRAME_HEIGHT / 6;

  // 上下移动属性
  private static final int MAX_MOVE_Y = 50;
  private int moveY = 0;
  private static final int MOVE_DOWN = 0;
  private static final int MOVE_UP = 1;
  private int moveDirect;

  private int y;

  // 碰撞检测矩形
  private Rectangle topRectangle;
  private Rectangle bottomRectangle;


  public MovingPipe(int x, int topHeight, int bottomHeight) {
    super(x);
    this.topHeight = topHeight;
    this.bottomHeight = bottomHeight;
    this.x = x;
    this.y = GameUtil.getRandomNumber(MIN_Y, MAX_Y);
    this.moveDirect = MOVE_UP;

    // 初始化碰撞矩形
    this.initRectangle();
  }

  public static MovingPipe createRandomPipe(int x) {
    int topHeight = GameUtil.getRandomNumber(MovingPipe.MIN_HEIGHT, MovingPipe.MAX_HEIGHT);
    int bottomHeight = GameUtil.getRandomNumber(MovingPipe.MIN_HEIGHT, MovingPipe.MAX_HEIGHT);
    return new MovingPipe(x, topHeight, bottomHeight);
  }

  private void initRectangle() {
    int topBodyCount = (topHeight - 2 * getHeadHeight()) / getBodyHeight() + 1;
    int realTopHeight = topBodyCount * getBodyHeight() + 2 * getHeadHeight();
    this.topRectangle = new Rectangle(x, y, getBodyWidth(), realTopHeight);

    int bottomBodyCount = (bottomHeight - 2 * getHeadHeight()) / getBodyHeight() + 1;
    int realBottomHeight = bottomBodyCount * getBodyHeight() + 2 * getHeadHeight();
    this.bottomRectangle = new Rectangle(x, y + realTopHeight + TOP_BOTTOM_SPACING, getBodyWidth(),
      realBottomHeight);
  }


  @Override
  public void drawPipe(Graphics g, Bird bird) {

    // 绘制上部分管道
    int topBodyCount = (topHeight - 2 * getHeadHeight()) / getBodyHeight() + 1;
    int realTopHeight = topBodyCount * getBodyHeight() + 2 * getHeadHeight();
    int topStartY = y;
    this.drawSubPipe(g, topStartY, topBodyCount, realTopHeight);

    // 绘制下部分管道
    int bottomBodyCount = (bottomHeight - 2 * getHeadHeight()) / getBodyHeight() + 1;
    int realBottomHeight = bottomBodyCount * getBodyHeight() + 2 * getHeadHeight();
    int bottomStartY = y + realTopHeight + TOP_BOTTOM_SPACING;
    this.drawSubPipe(g, bottomStartY, bottomBodyCount, realBottomHeight);

    // todo: 待删除
    g.setColor(Color.red);
    g.drawRect(topRectangle.x, topRectangle.y, topRectangle.width, topRectangle.height);
    g.drawRect(bottomRectangle.x, bottomRectangle.y, bottomRectangle.width, bottomRectangle.height);
  }

  @Override
  public void movement(Bird bird) {
    // 小鸟死亡不进行绘制
    if (bird.isDead()) {
      return;
    }
    this.x -= Constant.GAME_SPEED;
    this.topRectangle.x -= Constant.GAME_SPEED;
    this.bottomRectangle.x -= Constant.GAME_SPEED;

    if (moveDirect == MOVE_DOWN) {
      this.moveY--;
      this.y--;
      this.topRectangle.y--;
      this.bottomRectangle.y--;
      if (moveY <= 0) {
        this.moveDirect = MOVE_UP;
      }
    } else if (this.moveDirect == MOVE_UP) {
      this.moveY++;
      this.y++;
      this.topRectangle.y++;
      this.bottomRectangle.y++;
      if (moveY >= MAX_MOVE_Y) {
        this.moveDirect = MOVE_DOWN;
      }
    }
  }


  private void drawSubPipe(Graphics g, int y, int bodyCount, int height) {

    int headXOffset = Math.abs(getBodyWidth() - getHeadWidth()) / 2;

    // 绘制 上顶部
    g.drawImage(BOTTOM_HEAD_IMG, x - headXOffset, y, null);

    // 绘制 管体
    for (int i = 0; i < bodyCount; i++) {
      int startY = y + getHeadHeight() + i * getBodyHeight();
      g.drawImage(BODY_IMG, x, startY, null);
    }

    // 绘制 下顶部
    g.drawImage(TOP_HEAD_IMG, x - headXOffset, y + height - getHeadHeight(), null);
  }

  @Override
  public boolean isCollied(Rectangle rect) {
    return this.topRectangle.intersects(rect) || this.bottomRectangle.intersects(rect);
  }
}
