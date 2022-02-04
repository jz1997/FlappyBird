package com.game.demo;

import com.game.demo.util.GameUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * PipeFactory:
 *
 * @author Jiang
 * @since 2022/2/4 11:18 PM
 */
public class PipeFactory {

  private static final List<Supplier<AbstractPipe>> PIPE_SUPPLIERS = new ArrayList<>();

  private static final int PIPE_START_X = Constant.FRAME_WIDTH + Constant.PIPES_BETWEEN_WIDTH;

  static {
    PIPE_SUPPLIERS.add(() -> NormalPipe.createRandomPipe(PIPE_START_X));
    PIPE_SUPPLIERS.add(() -> MovingPipe.createRandomPipe(PIPE_START_X));
  }

  public static AbstractPipe createRandomPipe() {
    int index = GameUtil.getRandomNumber(0, PIPE_SUPPLIERS.size());
    return PIPE_SUPPLIERS.get(index).get();
  }
}
