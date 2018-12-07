package com.rangedbattle.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/** Creates the main login screen for the app.
 *
 */
public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private FirebaseAuth mAuth;
    // UI references.
    private AutoCompleteTextView emailView;
    private EditText passwordView;

    /** Creates the activity to login. This is the entry page for the app.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rangedbattle.firebasechat.R.layout.activity_login);

        emailView = (AutoCompleteTextView) findViewById(com.rangedbattle.firebasechat.R.id.login_email);
        passwordView = (EditText) findViewById(com.rangedbattle.firebasechat.R.id.login_password);

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == com.rangedbattle.firebasechat.R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

    }

    /** User login.
     * @param v
     */
    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();

    }

    /** Calls the registration page for a new user.
     * @param v - The register view
     */
    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.rangedbattle.firebasechat.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    /** Attempts to login. If the login is successful the MainChatActivity loads. If not an error message
     * is displayed.
     *
     */
    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (email.isEmpty())
        if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Chat", "signInWithEmail() onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d("Chat", "Problem signing in: " + task.getException());
                    showErrorDialog("There was a problem signing in");
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainChatActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        });


    }

    // TODO: Show error on screen with an alert dialog
    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}