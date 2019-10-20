package users;

/** An exception for no current user connected */
public class NoUserException extends Exception {
  public NoUserException(String errorMessage) {
    super(errorMessage);
  }
}
