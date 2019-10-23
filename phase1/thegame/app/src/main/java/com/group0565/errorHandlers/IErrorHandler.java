package com.group0565.errorHandlers;

/**
 * General non-platform specific error handler interface
 *
 * @param <T> The Throwable type to be handled by the class
 */
public interface IErrorHandler<T extends Throwable> {

  /**
   * Crash the app on severe error
   *
   * @param ex The throwable object to handle
   * @throws T A throwable object
   */
  void Crash(T ex) throws T;

  /**
   * Alert the user to the error, log the error, but keep running
   *
   * @param ex The throwable object to handle
   * @param message The message to the user
   * @param displayer A UI object capable of displaying the error
   */
  void Alert(IErrorDisplayer displayer, T ex, String message);

  /**
   * Log the error but keep running
   *
   * @param ex The throwable object to handle
   */
  void Ignore(T ex);
}
