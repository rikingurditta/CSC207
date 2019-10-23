package com.group0565.users;

/** Interface of a IUser component */
public interface IUser {
  /**
   * Returns the display name of the current user
   *
   * @return IUser's display name
   */
  String getDisplayName();

  /**
   * Returns the email of the current user
   *
   * @return IUser's email
   */
  String getEmail();

  /**
   * Returns the uid of the current user
   *
   * @return IUser's uid
   */
  String getUid();

  /**
   * Checks whether a user is connected
   *
   * @return True if a user is connected, false otherwise
   */
  boolean isConnected();
}
