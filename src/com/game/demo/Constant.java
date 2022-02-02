package com.game.demo;

import java.awt.*;

/**
 * Constant: 常量
 *
 * @author Jiang
 * @since 2022/1/28 4:18 PM
 */
public class Constant {

  // 窗口大小 开始

  public static final int FRAME_WIDTH = 420;
  public static final int FRAME_HEIGHT = 640;

  // 窗口大小 结束

  // 窗口的初始化坐标位置 开始

  /**
   * 纵坐标
   */
  public static final int FRAME_Y = 100;
  /**
   * 横坐标
   */
  public static final int FRAME_X = 600;

  // 窗口初始化坐标位置 结束


  /**
   * 美每秒显示帧率
   */
  public static final long FPS = 1000 / 30;

  /**
   * 游戏速度，水管和背景的游戏速度
   */
  public static final int GAME_SPEED = 4;

  /**
   * 小鸟资源图片
   */
  // 小鸟图片
  public static final String[][] BIRDS_IMG_PATH = {
    {"resources/img/0.png", "resources/img/1.png", "resources/img/2.png", "resources/img/3.png",
      "resources/img/4.png", "resources/img/5.png", "resources/img/6.png", "resources/img/7.png"},
    {"resources/img/up.png", "resources/img/up.png", "resources/img/up.png", "resources/img/up.png",
      "resources/img/up.png", "resources/img/up.png", "resources/img/up.png",
      "resources/img/up.png"},
    {"resources/img/down_0.png", "resources/img/down_1.png", "resources/img/down_2.png",
      "resources/img/down_3.png", "resources/img/down_4.png", "resources/img/down_5.png",
      "resources/img/down_6.png", "resources/img/down_7.png"},
    {"resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png",
      "resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png",
      "resources/img/dead.png", "resources/img/dead.png",}};

  /**
   * 游戏欢迎页面标题
   */
  public static final String TITLE_IMG_PATH = "resources/img/title.png";
  /**
   * 游戏欢迎页开始提示
   */
  public static final String NOTICE_IMG_PATH = "resources/img/start.png";

  /**
   * notice circle 周期计数
   */
  public static final int NOTICE_CIRCLE = 30;

  public static final int NOTICE_CIRCLE_CLEAR_COUNT = 2;

  /**
   * again notice circle 周期计数
   */
  public static final int AGAIN_NOTICE_CIRCLE = 30;

  public static final int AGAIN_NOTICE_CIRCLE_CLEAR_COUNT = 2;

  /**
   * 游戏结束提示图片
   */
  public static final String GAME_OVER_IMG_PATH = "resources/img/over.png";
  /**
   * 游戏分牌
   */
  public static final String SCORE_IMG_PATH = "resources/img/score.png";
  /**
   * 再来一次
   */
  public static final String AGAIN_IMG_PATH = "resources/img/again.png";


  public static final Font CURRENT_SCORE_FONT = new Font("华文琥珀", Font.BOLD, 32);
  public static final Font SCORE_FONT = new Font("华文琥珀", Font.BOLD, 24);

  /**
   * 标题栏高度
   */
  public static final int TOP_BAR_HEIGHT = 20;

  /**
   * 地面高度
   */
  public static final int GROUND_HEIGHT = 35;

  /**
   * 图片类型
   */
  public static final String[] CLOUD_IMG_PATH = new String[]{"resources/img/cloud_0.png",
    "resources/img/cloud_1.png"};
  /**
   * 云朵生成的概率，单位为百分比
   */
  public static final double CLOUD_BORN_PERCENT = 0.06;
  /**
   * 云朵图片的个数
   */
  public static final int CLOUD_IMAGE_COUNT = 2;
  /**
   * 云朵的最大数量
   */
  public static final int MAX_CLOUD_COUNT = 7;

  /**
   * 水管图片
   */
  public static final String[] PIPE_IMG_PATH = {"resources/img/pipe.png",
    "resources/img/pipe_top.png",
    "resources/img/pipe_bottom.png"};

  /**
   * flay 音乐文件路径
   */
  public static final String FLY_MUSIC_PATH = "resources/wav/fly.wav";
  /**
   * crash 音乐文件路径
   */
  public static final String CRASH_MUSIC_PATH = "resources/wav/crash.wav";

  /**
   * store 音乐文件路径
   */
  public static final String STORE_MUSIC_PATH = "resources/wav/score.wav";
  /**
   * 最佳分数存储文件
   */
  public static final String SCORE_FILE_PATH = "resources/score";
}
