package com.pennapps.vnd.ffling;

import java.util.ArrayList;

import com.google.android.maps.MapView;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MailboxActivity extends ListActivity{
	ArrayList<Message> inbox;
	ListView listView;
	@Override
	
    public void onCreate(Bundle savedInstanceState) {
		TextView selected;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailbox);        
        inbox = new ArrayList<Message>();
        inbox.add(new Message("first", "hello world", "Java", "12:12:12", "1"));
        inbox.add(new Message("second", "hello world", "Java", "12:12:12", "1"));
        inbox.add(new Message("third", "hello world", "Java", "12:12:12", "1"));
        inbox.add(new Message("fourth", "hello world", "Java", "12:12:12", "1"));
        setListAdapter(new ArrayAdapter<Message>(this, android.R.layout.simple_expandable_list_item_1, inbox));
        selected = (TextView)findViewById(R.id.textViewMail);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		//open up reader and send over inbox.get(position);
		System.out.println(inbox.get(position) + "\n was clicked");
	}
	
	public void goToMenu(View view){
    	Intent intent = new Intent();
    	setResult(RESULT_OK, intent);
    	finish();
    }
	public void goToReader(View view){
		
	}
}
