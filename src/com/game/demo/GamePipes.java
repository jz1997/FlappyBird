package com.game.demo;

import com.game.demo.score.ScoreCounter;
import com.game.demo.util.GameUtil;
import com.game.demo.util.MusicUtil;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * GamePipes: 水管展示层
 *
 * @author Jiang
 * @since 2022/1/31 6:58 PM
 */
public class GamePipes {

  private List<Pipe> pipes;

  private static final int PIPES_BETWEEN_WIDTH = 160;

  private final ScoreCounter scoreCounter = ScoreCounter.getInstance();

  public GamePipes() {
    this.pipes = new ArrayList<>();
  }

  public void draw(Graphics g, Bird bird) {
    removePipe();
    generatePipe(bird);
    for (Pipe pipe : pipes) {
      pipe.draw(g, bird);
    }

    // 检测碰撞
    isCollide(bird);
    // 记录分数
    plusScore(bird);
  }

  public void isCollide(Bird bird) {
    if (bird.isDead()) {
      return;
    }
    for (Pipe pipe : pipes) {
      if (pipe.isCollied(bird.getRect())) {
        // 碰撞了
        bird.birdDeadFall();
        return;
      }
    }
  }

  /**
   * 记录分数
   */
  public void plusScore(Bird bird) {
    if (bird.isDead()) {
      return;
    }

    for (Pipe pipe : pipes) {
      if (!pipe.isScore() && pipe.getRightX() <= bird.getX()) {
        MusicUtil.playStore();
        scoreCounter.increaseScore();
        pipe.setScored();
        return;
      }
    }
  }

  /**
   * 生成管道
   *
   * @param bird /
   */
  private void generatePipe(Bird bird) {
    // 小鸟死亡不在绘制
    if (bird.isDead()) {
      return;
    }
    boolean flag = false;
    if (pipes.isEmpty()) {
      flag = true;
    } else {
      Pipe lastPipe = pipes.get(pipes.size() - 1);
      if (lastPipe.isInFrame()) {
        flag = true;
      }
    }

    if (flag) {
      int topHeight = GameUtil.getRandomNumber(Pipe.MIN_HEIGHT, Pipe.MAX_HEIGHT);
      Pipe pipe = new Pipe(Constant.FRAME_WIDTH + PIPES_BETWEEN_WIDTH, topHeight);
      pipes.add(pipe);
    }
  }

  /**
   * 移除管道
   */
  private void removePipe() {
    if (pipes.isEmpty()) {
      return;
    }
    pipes.removeIf(Pipe::isOutOfFrame);
  }

  public void reset() {
    this.pipes = new ArrayList<>();
  }
}
