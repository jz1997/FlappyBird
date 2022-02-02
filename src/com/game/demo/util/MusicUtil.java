package com.game.demo.util;

import com.game.demo.Constant;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * MusicUtil: 音乐工具类
 *
 * @author Jiang
 * @since 2022/2/2 9:02 PM
 */
public class MusicUtil {

  public static void playFly() {
    play(Constant.FLY_MUSIC_PATH);
  }

  public static void playCrash() {
    play(Constant.CRASH_MUSIC_PATH);
  }

  public static void playStore() {
    play(Constant.STORE_MUSIC_PATH);
  }

  public static void play(String path) {
    File file = new File(path);
    try {
      AudioStream audioStream = new AudioStream(new FileInputStream(file));
      AudioPlayer.player.start(audioStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
