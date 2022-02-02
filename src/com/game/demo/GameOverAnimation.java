package com.game.demo;

import com.game.demo.score.ScoreCounter;
import com.game.demo.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * GameOverAnimation: 游戏结束控件
 *
 * @author Jiang
 * @since 2022/1/30 6:08 PM
 */
public class GameOverAnimation {

  private final BufferedImage gameOverImage;
  private final BufferedImage scoreImage;
  private final BufferedImage againImage;

  private int againNoticeNumber = 0;

  private final ScoreCounter scoreCounter = ScoreCounter.getInstance();

  public GameOverAnimation() {
    this.gameOverImage = GameUtil.loadImageFrom(Constant.GAME_OVER_IMG_PATH);
    this.scoreImage = GameUtil.loadImageFrom(Constant.SCORE_IMG_PATH);
    this.againImage = GameUtil.loadImageFrom(Constant.AGAIN_IMG_PATH);
  }

  public void draw(Graphics g) {
    // 绘制 game over image
    int overImgX = (Constant.FRAME_WIDTH - this.gameOverImage.getWidth()) >> 1;
    int overImgY = Constant.FRAME_HEIGHT / 4;
    g.drawImage(gameOverImage, overImgX, overImgY, null);

    // 绘出分数牌
    int scoreImgX = (Constant.FRAME_WIDTH - this.scoreImage.getWidth()) >> 1;
    int scoreImgY = Constant.FRAME_HEIGHT / 3;
    g.drawImage(scoreImage, scoreImgX, scoreImgY, null);

    // 绘制 again image
    againNoticeNumber++;
    if (againNoticeNumber < Constant.AGAIN_NOTICE_CIRCLE) {
      int againImgX = (Constant.FRAME_WIDTH - this.againImage.getWidth()) / 2;
      int againImgY = Constant.FRAME_HEIGHT / 5 * 3;
      g.drawImage(againImage, againImgX, againImgY, null);
    }

    if (againNoticeNumber == Constant.AGAIN_NOTICE_CIRCLE_CLEAR_COUNT * Constant.NOTICE_CIRCLE) {
      this.againNoticeNumber = 0;
    }

    // 绘制分数
    String scoreText = String.valueOf(scoreCounter.getScore());
    int scoreTextWidth = GameUtil.getStringWidth(Constant.SCORE_FONT, scoreText);
    int scoreTextHeight = GameUtil.getStringHeight(Constant.SCORE_FONT, scoreText);
    int scoreTextX =
      (Constant.FRAME_WIDTH - scoreImage.getWidth() / 2 >> 1) - (scoreTextWidth >> 1) + 5;
    int scoreTextY = scoreImgY + (this.scoreImage.getHeight() >> 1) + (scoreTextHeight >> 1);

    g.setColor(Color.white);
    g.setFont(Constant.SCORE_FONT);
    g.drawString(scoreText, scoreTextX, scoreTextY);

    // 绘制总分数
    String scoreTotalText = String.valueOf(scoreCounter.getBestScore());
    int scoreTotalTextWidth = GameUtil.getStringWidth(Constant.SCORE_FONT, scoreTotalText);

    int scoreTotalTextX =
      ((Constant.FRAME_WIDTH + scoreImage.getWidth() / 2) >> 1) - (scoreTotalTextWidth / 2) - 5;

    g.drawString(scoreTotalText, scoreTotalTextX, scoreTextY);
  }
}
