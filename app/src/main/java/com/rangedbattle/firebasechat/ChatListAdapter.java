package com.rangedbattle.firebasechat;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    private Activity activity;
    private DatabaseReference databaseReference;
    private String displayName;
    private ArrayList<DataSnapshot> snapshotList;

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            snapshotList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {

        this.activity = activity;
        displayName = name;
        databaseReference = ref.child("messages");
        databaseReference.addChildEventListener(listener);

        snapshotList = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return snapshotList.size();
    }

    @Override
    public InstantMessage getItem(int position) {

        DataSnapshot snapshot = snapshotList.get(position);
        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(com.rangedbattle.firebasechat.R.layout.chat_msg_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.authorName = (TextView) convertView.findViewById(com.rangedbattle.firebasechat.R.id.author);
            holder.body = (TextView) convertView.findViewById(com.rangedbattle.firebasechat.R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);

        }

        final InstantMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        boolean isMe = message.getAuthor().equals(displayName);
        setChatRowAppearance(isMe, holder);

        String author = message.getAuthor();
        holder.authorName.setText(author);

        String msg = message.getMessage();
        holder.body.setText(msg);


        return convertView;
    }

    private void setChatRowAppearance(boolean isItMe, ViewHolder holder) {

        if (isItMe) {


            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.BLACK);


            holder.body.setBackgroundResource(com.rangedbattle.firebasechat.R.drawable.bubble2);
        } else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLACK);
            holder.body.setBackgroundResource(com.rangedbattle.firebasechat.R.drawable.bubble1);
        }

        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);

    }


    void cleanup() {

        databaseReference.removeEventListener(listener);
    }


}
