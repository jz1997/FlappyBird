package com.game.demo.exception;

/**
 * ResourceException: 资源加载异常
 *
 * @author Jiang
 * @since 2022/1/30 1:46 PM
 */
public class ResourceLoadException extends RuntimeException {

  public ResourceLoadException() {
  }

  public ResourceLoadException(String message) {
    super(message);
  }

  public ResourceLoadException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceLoadException(Throwable cause) {
    super(cause);
  }

  public ResourceLoadException(String message, Throwable cause, boolean enableSuppression,
    boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
