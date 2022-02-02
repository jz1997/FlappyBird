package com.game.demo.score;

import com.game.demo.Constant;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ScoreCounter:
 *
 * @author Jiang
 * @since 2022/2/2 10:33 PM
 */
public class ScoreCounter {

  private int score;

  private ScoreCounter() {
  }

  /**
   * 获取分数计数器实例
   *
   * @return /
   */
  public static ScoreCounter getInstance() {
    return ScoreCounterHolder.instance;
  }

  public int loadBestScoreFromFile() {
    InputStream in;
    try (DataInputStream dis = new DataInputStream(
      new FileInputStream(Constant.SCORE_FILE_PATH))) {
      return dis.readInt();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public void saveBestScoreToFile() {
    OutputStream out;
    try {
      try (DataOutputStream dos = new DataOutputStream(
        new FileOutputStream(Constant.SCORE_FILE_PATH))) {
        dos.writeInt(score);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void reset() {
    this.score = 0;
  }

  public void increaseScore() {
    this.plusScore(1);
  }

  public void plusScore(int scoreToAdd) {
    this.score += scoreToAdd;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getBestScore() {
    return loadBestScoreFromFile();
  }

  public boolean isBestScore() {
    return this.score > this.loadBestScoreFromFile();
  }

  public static class ScoreCounterHolder {

    private static final ScoreCounter instance = new ScoreCounter();
  }
}
