package com.game.demo;

import com.game.demo.exception.ResourceLoadException;
import com.game.demo.score.ScoreCounter;
import com.game.demo.util.GameUtil;
import com.game.demo.util.MusicUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Bird: 小鸟对象
 *
 * @author Jiang
 * @since 2022/1/30 1:23 PM
 */
public class Bird {

  /**
   * 小鸟的状态 BIRD_NORMAL 正常状态 BIRD_UP 上升状态 BIRD_FALL 下降状态 BIRD_DEAD_FALL 坠落死亡 BIRD_DEAD 死亡
   */
  private int state;
  public static final int BIRD_NORMAL = 0;
  public static final int BIRD_UP = 1;
  public static final int BIRD_FALL = 2;
  public static final int BIRD_DEAD_FALL = 3;
  public static final int BIRD_DEAD = 4;

  /**
   * 图片数量
   */
  public static final int IMG_COUNT = 8;
  /**
   * 状态数量
   */
  public static final int STATE_COUNT = 4;
  /**
   * 小小鸟图片
   */
  private final BufferedImage[][] images;
  /**
   * 当前小鸟图片
   */
  private BufferedImage image;

  private int wingsState = 0;

  /**
   * 小鸟坐标
   */
  private int x;
  private int y;

  // 小鸟下坠需要的参数
  /**
   * 速度
   */
  private int velocity = 0;
  private static final int ACC_Y = 2;
  private static final int MAX_FALL_SPEED = 15;
  private static final int FLAP_SPEED = 14;

  private final int BOTTOM_Y;
  private final int BIRD_WIDTH;
  private final int BIRD_HEIGHT;

  /**
   * 小鸟的碰撞矩形
   */
  public Rectangle rect;

  private final ScoreCounter scoreCounter = ScoreCounter.getInstance();

  public Bird() {
    // 加载 bird 图片
    this.images = new BufferedImage[STATE_COUNT][IMG_COUNT];
    for (int i = 0; i < STATE_COUNT; i++) {
      for (int j = 0; j < IMG_COUNT; j++) {
        this.images[i][j] = GameUtil.loadImageFrom(Constant.BIRDS_IMG_PATH[i][j]);
      }
    }

    if (images[0][0] == null) {
      throw new ResourceLoadException(
        String.format("路径 %s 下, 文件不存在", Constant.BIRDS_IMG_PATH[0][0]));
    }

    this.image = this.images[0][0];
    BIRD_WIDTH = this.image.getWidth();
    BIRD_HEIGHT = this.image.getHeight();

    // 设置小鸟坐标
    x = Constant.FRAME_WIDTH >> 2;
    y = Constant.FRAME_HEIGHT >> 1;

    // 设置最下角坐标
    BOTTOM_Y = Constant.FRAME_HEIGHT - (GameBackground.BACKGROUND_HEIGHT / 2) - (BIRD_HEIGHT / 2);

    int rectX = x - BIRD_WIDTH / 2;
    int rectY = y - BIRD_HEIGHT / 2;
    this.rect = new Rectangle(rectX, rectY, BIRD_WIDTH, BIRD_HEIGHT);
  }


  public void draw(Graphics g) {
    movement();
    int stateIndex = Math.min(state, BIRD_DEAD_FALL);
    // 计算小鸟中心点
    int imageWidth = this.images[stateIndex][0].getWidth() >> 1;
    int imageHeight = this.images[stateIndex][0].getHeight() >> 1;

    if (velocity > 0) {
      this.image = this.images[BIRD_UP][0];
    }

    // x 位于窗口 1/4 处, y 位于窗口纵向 1/2 处
    g.drawImage(this.image, x - imageWidth, y - imageHeight, null);
  }

  private void movement() {
    // 翅膀状态
    this.wingsState++;
    int imgIndex = Math.min(state, BIRD_DEAD_FALL);
    int wingsStateIndex = wingsState / 10 % IMG_COUNT;
    this.image = this.images[imgIndex][wingsStateIndex];

    // 小鸟下坠
    if (state == BIRD_FALL || state == BIRD_DEAD_FALL) {
      this.fallDown();
      if (this.rect.y > BOTTOM_Y) {
        if (state == BIRD_FALL) {
          MusicUtil.playCrash();
        }
        die();
      }
    }
  }

  private void fallDown() {
    if (velocity < MAX_FALL_SPEED) {
      velocity -= ACC_Y;
    }
    this.y = Math.min((y - velocity), BOTTOM_Y);
    this.rect.y = rect.y - velocity;
  }


  public void flap() {
    if (isDead()) {
      return;
    }
    this.state = BIRD_UP;
    MusicUtil.playFly();

    if (this.y > Constant.TOP_BAR_HEIGHT) {
      this.velocity = FLAP_SPEED;
      wingsState = 0;
    }

  }

  public void fall() {
    if (isDead()) {
      return;
    }
    this.state = BIRD_FALL;
  }

  public void die() {
    this.state = BIRD_DEAD;
    if (scoreCounter.isBestScore()) {
      scoreCounter.saveBestScoreToFile();
    }
    Game.setGameState(Game.GAME_OVER);
  }


  public boolean isDead() {
    return state == BIRD_DEAD || state == BIRD_DEAD_FALL;
  }

  public Rectangle getRect() {
    return this.rect;
  }

  public void birdDeadFall() {
    this.state = BIRD_DEAD_FALL;
    MusicUtil.playCrash();
    this.velocity = 0;
  }

  public void reset() {
    this.state = BIRD_NORMAL;
    this.x = Constant.FRAME_WIDTH >> 2;
    this.y = Constant.FRAME_HEIGHT >> 1;
    this.rect = new Rectangle(x - BIRD_WIDTH / 2, y - BIRD_HEIGHT / 2, BIRD_WIDTH, BIRD_HEIGHT);
  }

  public int getX() {
    return x;
  }
}
