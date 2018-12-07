package com.rangedbattle.firebasechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/** Main chat screen for that displays all messages sent. Allows user to send a new message.
 *
 */
public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String displayName;
    private ListView chatListView;
    private EditText inputText;
    private ImageButton sendButton;
    private DatabaseReference databaseReference;
    private ChatListAdapter adapter;

    /** Creates the chat activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rangedbattle.firebasechat.R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        // Link the Views in the layout to the Java code
        inputText = (EditText) findViewById(com.rangedbattle.firebasechat.R.id.messageInput);
        sendButton = (ImageButton) findViewById(com.rangedbattle.firebasechat.R.id.sendButton);
        chatListView = (ListView) findViewById(com.rangedbattle.firebasechat.R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                sendMessage();
                return true;
            }
        });

        // TODO: Add an OnClickListener to the sendButton to send a message
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    /** Gets the users name from the database
     *
     */
    // TODO: Retrieve the display name from the Shared Preferences
    private void setupDisplayName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        displayName = user.getDisplayName();
    }

    /** Sends the message and username to the database
     *
     */
    private void sendMessage() {

        Log.d("Chat", "I sent something");
        // TODO: Grab the text the user typed in and push the message to Firebase
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            InstantMessage chat = new InstantMessage(input, displayName);
            databaseReference.child("messages").push().setValue(chat);
            inputText.setText("");
        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
    @Override
    public void onStart() {
        super.onStart();
        adapter = new ChatListAdapter(this, databaseReference, displayName);
        chatListView.setAdapter(adapter);
    }


    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
        adapter.cleanup();

    }

}
