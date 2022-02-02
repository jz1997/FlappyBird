package com.game.demo;

import com.game.demo.exception.ResourceLoadException;
import com.game.demo.util.GameUtil;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * GameClouds:
 *
 * @author Jiang
 * @since 2022/1/31 5:18 PM
 */
public class GameClouds {

  private List<Cloud> clouds = new ArrayList<>();
  private static List<BufferedImage> cloudImages;

  /**
   * 100 ms 生成一次
   */
  private static final int CLOUD_INTERVAL = 100;

  /**
   * 最后一次生成云朵的时间
   */
  private long lastGenerateCloudTime;

  public GameClouds() {
    this.lastGenerateCloudTime = System.currentTimeMillis();
    this.loadCloudImages();
  }

  public void draw(Graphics g, Bird bird) {
    this.generateCloud();
    removeOutOfFrameCloud();
    for (Cloud cloud : clouds) {
      cloud.draw(g, bird);
    }
  }

  private void loadCloudImages() {
    cloudImages = new ArrayList<>();
    for (String path : Constant.CLOUD_IMG_PATH) {
      BufferedImage image = Optional.ofNullable(GameUtil.loadImageFrom(path))
        .orElseThrow(() -> new ResourceLoadException(String.format("资源部存在: %s", path)));
      cloudImages.add(image);
    }
  }

  private void generateCloud() {
    // 时间是否到了
    boolean canGenerate = System.currentTimeMillis() - this.lastGenerateCloudTime >= CLOUD_INTERVAL;
    if (!canGenerate) {
      return;
    }

    // 是否生成
    double randomVal = Math.random();
    if (randomVal > Constant.CLOUD_BORN_PERCENT) {
      return;
    }

    // 生成的类型
    int index = GameUtil.getRandomNumber(0, 2);
    int x = Constant.FRAME_WIDTH;
    int y = GameUtil.getRandomNumber(Constant.TOP_BAR_HEIGHT, Constant.FRAME_HEIGHT / 3);
    Cloud cloud = new Cloud(cloudImages.get(index), x, y);
    this.clouds.add(cloud);

    this.lastGenerateCloudTime = System.currentTimeMillis();
  }

  /**
   * 删除 frame 之外的 云朵
   */
  private void removeOutOfFrameCloud() {
    clouds.removeIf(Cloud::isOutOfFrame);
  }


  public void reset() {
    this.clouds = new ArrayList<>();
  }

}
