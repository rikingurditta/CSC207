package users;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/** Firebase implementation of IUser interface */
public class UserFirebaseImp implements IUser {
  /** The Firebase user object */
  private FirebaseUser user;

  /** Creates a new UserFirebaseImp */
  UserFirebaseImp() {
    user = FirebaseAuth.getInstance().getCurrentUser();
  }

  /**
   * Returns the display name of the current user
   *
   * @return IUser's display name
   */
  @Override
  public String getDisplayName() {
    return user.getDisplayName();
  }

  /**
   * Returns the email of the current user
   *
   * @return IUser's email
   */
  @Override
  public String getEmail() {
    return user.getEmail();
  }

  /**
   * Returns the uid of the current user
   *
   * @return IUser's uid
   */
  @Override
  public String getUid() {
    return user.getUid();
  }

  /**
   * Checks whether a user is connected
   *
   * @return True if a user is connected, false otherwise
   */
  @Override
  public boolean isConnected() {
    return user != null;
  }
}
