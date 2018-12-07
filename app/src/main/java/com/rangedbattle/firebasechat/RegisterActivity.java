package com.rangedbattle.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/** Allows the user to register a new user with the firebase database.
 *
 */
public class RegisterActivity extends AppCompatActivity {

    // Constants
    static final String CHAT_PREFS = "ChatPrefs";
    static final String DISPLAY_NAME_KEY = "username";

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView emailView;
    private AutoCompleteTextView usernameView;
    private EditText passwordView;
    private EditText confirmPasswordView;

    // Firebase instance variables
    private FirebaseAuth auth;


    /** Creates the page for the user to register.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rangedbattle.firebasechat.R.layout.activity_register);

        emailView = (AutoCompleteTextView) findViewById(com.rangedbattle.firebasechat.R.id.register_email);
        passwordView = (EditText) findViewById(com.rangedbattle.firebasechat.R.id.register_password);
        confirmPasswordView = (EditText) findViewById(com.rangedbattle.firebasechat.R.id.register_confirm_password);
        usernameView = (AutoCompleteTextView) findViewById(com.rangedbattle.firebasechat.R.id.register_username);

        // Keyboard sign in action
        confirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == com.rangedbattle.firebasechat.R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        auth = FirebaseAuth.getInstance();


    }

    /** Calls the private registration method to attempt to register a user.
     * @param v - View for the signup page
     */
    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    /**Registers a new user if the email and password are correct. This is private and called by the class.
     *
     */
    private void attemptRegistration() {

        // Reset errors displayed in the form.
        emailView.setError(null);
        passwordView.setError(null);
        usernameView.setError(null);

        // Store values at the time of the login attempt.
        String userName = usernameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String confirmPassword = confirmPasswordView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password,confirmPassword)) {
            passwordView.setError(getString(com.rangedbattle.firebasechat.R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(com.rangedbattle.firebasechat.R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(com.rangedbattle.firebasechat.R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)){
            usernameView.setError(getString(com.rangedbattle.firebasechat.R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        } else if (!isUserNameValid(userName)){
            usernameView.setError(getString(com.rangedbattle.firebasechat.R.string.error_invalid_username));
            focusView = usernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();

        }
    }

    /**Verifies the username only contains characters A-Z, upper or lowercase, and has at least 6 characters.
     * @param userName
     * @return
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) boolean isUserNameValid(String userName){
        if(userName.length() <= 5){
            return false;           //Return false if the userName is too short
        }

        if(userName.matches("^[A-Za-z]+$")){
            return true;            //Return true if username meets requirements
        }

        return false;                //Return false if userName fails conditions

    }

    /** Verifies the email contains one and only one @ symbol.
     * @param email - String for the user's email address
     * @return true - passes validation tests
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) boolean isEmailValid(String email) {
        boolean atSign = false;
        boolean extension = false;
        email = email.trim();
        int length = email.length();

        // a@b.ext is smallest possible, test length
        if(length < 7) return false;

        // check if there is one, and only one @ sign
        for(int i=0;i<email.length();i++) {
            if (email.charAt(i) == '@') {
                if (atSign) return false; //@ sign found
                else atSign = true;
            }
        }

        String extensions[] = {".com",".net",".edu",".org"};
        String possibleExt = email.substring(length-4,length).toLowerCase();

        for(int e = 0; e < 4; e++) {
            if (extensions[e].equals(possibleExt)) {
                extension = true;
            }
        }

        if(extension && atSign) return true;

        return false;
    }

    /** Checks that the password contains requisite security requirements.
     * @param password - String for the users password
     * @param confirmPassword - String to verify the user entered the same password
     * @return - True or False for the validity of the password
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)  boolean isPasswordValid(String password, String confirmPassword) {
        //TODO: Add own logic to check for a valid password

        return confirmPassword.equals(password) && password.length() > 4 && (password.contains("!")||password.contains("@")||
                password.contains("#")||password.contains("$")||password.contains("%")||password.contains("^")||password.contains("&")
                ||password.contains("*")||password.contains("(")||password.contains(")"));
    }

    /**Creates a user in firebase with given data.
     *
     */
    // TODO: Create a Firebase user
    private void createFirebaseUser() {

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();



        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Chat", "createUser onComplete: " + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.d("Chat", "user creation failed");
                    showErrorDialog("Registration attempt failed");
                } else {
                    saveDisplayName();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }


    /**Saves the user display name in firebase.
     *
     */
    private void saveDisplayName() {

        FirebaseUser user = auth.getCurrentUser();
        String displayName = usernameView.getText().toString();

        if (user !=null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Battle Chat", "User name updated.");
                            }
                        }
                    });

        }

    }

    /**Error dialog box
     * @param message - Registration error message
     */
    // TODO: Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }




}
