package com.game.demo;

import com.game.demo.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * WelcomeAnimation:
 * <p>
 * 欢迎动画页
 *
 * @author Jiang
 * @since 2022/1/30 4:31 PM
 */
public class WelcomeAnimation {

  private final BufferedImage titleImage;
  private final BufferedImage noticeImage;

  private int circleNumber = 0;

  public WelcomeAnimation() {
    titleImage = GameUtil.loadImageFrom(Constant.TITLE_IMG_PATH);
    noticeImage = GameUtil.loadImageFrom(Constant.NOTICE_IMG_PATH);
  }

  public void draw(Graphics g) {
    int titleImgX = (Constant.FRAME_WIDTH - titleImage.getWidth()) >> 1;
    int titleImgY = (Constant.FRAME_HEIGHT) / 3;
    // 绘制 title image
    g.drawImage(titleImage, titleImgX, titleImgY, null);

    // 绘制 notice image
    int noticeImgX = (Constant.FRAME_WIDTH - noticeImage.getWidth()) >> 1;
    int noticeImgY = Constant.FRAME_HEIGHT / 5 * 3;

    circleNumber++;

    // 显示
    if (circleNumber <= Constant.NOTICE_CIRCLE) {
      g.drawImage(noticeImage, noticeImgX, noticeImgY, null);
    }

    // 不显示
    if (circleNumber == Constant.NOTICE_CIRCLE_CLEAR_COUNT * Constant.NOTICE_CIRCLE) {
      circleNumber = 0;
    }
  }
}


