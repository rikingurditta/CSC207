package com.group0565.errorHandlers;

/** An interface for objects capable of displaying a message to the UI */
public interface IErrorDisplayer {
  /**
   * Display a message to the UI with the given text
   *
   * @param message The text to be displayed
   */
  void DisplayMessage(String message);
}
