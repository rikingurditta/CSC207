package com.group0565.errorHandlers;

import android.util.Log;

/** Exception IErrorHandler */
public class ExceptionErrorHandler implements IErrorHandler<Exception> {

  /**
   * Crash the app on severe error
   *
   * @param ex The throwable object to handle
   * @throws Exception A throwable object
   */
  public void Crash(Exception ex) throws Exception {
    throw ex;
  }

  /**
   * Alert the user to the error using a Toast, log the error, but keep running
   *
   * @param ex The throwable object to handle
   * @param message The message to the user
   * @param displayer A UI object capable of displaying the error
   */
  public void Alert(IErrorDisplayer displayer, Exception ex, String message) {
    displayer.DisplayMessage(message);
    Log.e("EXCEPTION CAUGHT", message, ex);
  }

  /**
   * Log the error but keep running
   *
   * @param ex The throwable object to handle
   */
  public void Ignore(Exception ex) {
    Log.e("EXCEPTION CAUGHT", ex.getMessage(), ex);
  }
}
