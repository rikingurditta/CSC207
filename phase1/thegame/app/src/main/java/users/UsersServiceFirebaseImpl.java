package users;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/** Firebase implementation of users service interface - a singleton */
public class UsersServiceFirebaseImpl implements IUsersService {

  /** Singleton instance */
  private static UsersServiceFirebaseImpl instance;

  /** Mutable LiveData object for making user data observable */
  private final MutableLiveData<IUser> userLiveData;

  /** Reference to FirebaseAuth.Instance */
  private FirebaseAuth firebaseAuth;

  /** Reference to AuthUI.Instance */
  private AuthUI firebaseAuthUI;

  /** A list of the used log in providers */
  private List<AuthUI.IdpConfig> providers =
      Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
          new AuthUI.IdpConfig.GoogleBuilder().build());

  /** Creates a new instance UsersServiceFirebaseImpl */
  private UsersServiceFirebaseImpl() {
    firebaseAuth = FirebaseAuth.getInstance();
    firebaseAuthUI = AuthUI.getInstance();

    userLiveData = new MutableLiveData<>();

    // Listen in to all changes in current user Auth status
    firebaseAuth.addAuthStateListener(
        new FirebaseAuth.AuthStateListener() {
          @Override
          public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            updateUserOnAuthChange();
          }
        });
  }

  /**
   * Returns the singleton instance of this class, instantiates if needed
   *
   * @return The instance of this class
   */
  public static UsersServiceFirebaseImpl getInstance() {
    if (instance == null) {
      instance = new UsersServiceFirebaseImpl();
    }
    return instance;
  }

  /**
   * Creates the Firebase UI SignIn Intent - opens the UI built-in auth window
   *
   * @return Firebase Sign In Intent
   */
  @Override
  public Intent signIn() {
    // Return the intent of the Sign in activity
    return firebaseAuthUI.createSignInIntentBuilder().setAvailableProviders(providers).build();
  }

  /** Sign the current user out */
  @Override
  public void signOut() {
    firebaseAuth.signOut();
  }

  /**
   * Deletes the current user
   *
   * @param listener Method to perform OnComplete
   * @throws NoUserException If no user is signed in
   */
  @Override
  public void delete(OnCompleteListener<Void> listener) throws NoUserException {
    if (!isUserConnected()) {
      throw new NoUserException("No IUser connected");
    }

    FirebaseUser user = firebaseAuth.getCurrentUser();

    //noinspection ConstantConditions Null check in method call above
    user.delete();
  }

  /**
   * Deletes the current user
   *
   * @throws NoUserException If no user is signed in
   */
  @Override
  public void delete() throws NoUserException {
    if (!isUserConnected()) {
      throw new NoUserException("No IUser connected");
    }

    FirebaseUser user = firebaseAuth.getCurrentUser();

    //noinspection ConstantConditions Null check in method call above
    user.delete();
  }

  /**
   * Checks if there currently is a logged in user
   *
   * @return True if a user is signed in, false otherwise
   */
  @Override
  public boolean isUserConnected() {
    return firebaseAuth.getCurrentUser() != null;
  }

  /**
   * Gets the LiveData observable with the currently logged in user
   *
   * @return LiveData encapsulation of current user data
   */
  @Override
  public LiveData<IUser> getUser() {
    return userLiveData;
  }

  /** Set the value of the user LiveData to alert observers to change */
  private void updateUserOnAuthChange() {
    userLiveData.setValue(new UserFirebaseImp());
  }
}
