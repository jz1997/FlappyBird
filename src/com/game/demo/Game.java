package com.game.demo;

import com.game.demo.score.ScoreCounter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * Game: 主窗体
 *
 * @author Jiang
 * @since 2022/1/28 4:14 PM
 */
public class Game extends Frame {

  private static final Logger log = Logger.getLogger("Game");

  /**
   * 游戏状态 GAME_READY 游戏开始 GAME_START 游戏开始 GAME_OVER 游戏结束
   */
  private static int gameState;
  public static final int GAME_READY = 0;
  public static final int GAME_START = 1;
  public static final int GAME_OVER = 2;

  private GameBackground gameBackground;
  private WelcomeAnimation welcomeAnimation;
  private GameOverAnimation gameOverAnimation;
  private GameClouds gameClouds;
  private GamePipes gamePipes;
  private Bird bird;

  private final ScoreCounter scoreCounter = ScoreCounter.getInstance();

  public Game() {
    initFrame();
    setVisible(true);
    initGame();
  }


  /**
   * 初始化窗体
   */
  private void initFrame() {
    this.setSize(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
    this.setResizable(false);
    this.setLocation(Constant.FRAME_X, Constant.FRAME_Y);
    this.addWindowListener();
    this.addKeyListener();
  }

  /**
   * 监听窗体事件
   */
  private void addWindowListener() {
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  /**
   * 监听按钮事件
   */
  private void addKeyListener() {
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (gameState) {
          case GAME_READY:
            if (keyCode == KeyEvent.VK_SPACE) {
              // 空格键开始 小鸟上升，然后手重力影响
              bird.flap();
              bird.fall();
              setGameState(GAME_START);
            }
            break;
          case GAME_START:
            if (keyCode == KeyEvent.VK_SPACE) {
              bird.flap();
              bird.fall();
            }
            break;
          case GAME_OVER:
            if (keyCode == KeyEvent.VK_SPACE) {
              resetGame();
            }
            break;
          default:
            break;
        }

      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
  }

  private void initGame() {
    gameBackground = new GameBackground();
    welcomeAnimation = new WelcomeAnimation();
    gameOverAnimation = new GameOverAnimation();
    gameClouds = new GameClouds();
    gamePipes = new GamePipes();
    bird = new Bird();
    setGameState(Game.GAME_START);

    // 初始化游戏状态
    this.setGameState(Game.GAME_READY);
    new Thread(() -> {
      while (true) {
        this.repaint();
        try {
          Thread.sleep(Constant.FPS);
        } catch (InterruptedException e) {
          log.severe(
            String.format("initGame() called with exception => parameters = %s, exception = {%s}",
              "", e));

        }
      }
    }).start();
  }

  public static void setGameState(int state) {
    Game.gameState = state;
  }


  // 双缓冲
  private static final BufferedImage BUFFERED_IMAGE = new BufferedImage(Constant.FRAME_WIDTH,
    Constant.FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

  @Override
  public void update(Graphics g) {
    Graphics bufferG = BUFFERED_IMAGE.getGraphics();
    this.gameBackground.draw(bufferG, bird);
    this.gameClouds.draw(bufferG, bird);

    if (Game.gameState != GAME_READY) {
      this.gamePipes.draw(bufferG, bird);
    }

    if (Game.gameState == Game.GAME_READY) {
      welcomeAnimation.draw(bufferG);
    } else if (Game.gameState == Game.GAME_OVER) {
      gameOverAnimation.draw(bufferG);
    }

    this.bird.draw(bufferG);

    g.drawImage(BUFFERED_IMAGE, 0, 0, null);
  }

  private void resetGame() {
    setGameState(GAME_READY);
    this.bird.reset();
    this.gamePipes.reset();
    this.scoreCounter.reset();
    this.gameClouds.reset();
  }

}
