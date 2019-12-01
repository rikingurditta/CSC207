package com.group0565.users;

/** An exception for no current user connected */
class NoUserException extends Exception {
  /**
   * Create a new instance of NoUserException
   *
   * @param errorMessage The error message for the exception
   */
  NoUserException(String errorMessage) {
    super(errorMessage);
  }
}
