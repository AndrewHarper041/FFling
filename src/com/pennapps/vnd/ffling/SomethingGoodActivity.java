package com.pennapps.vnd.ffling;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONObject;

import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.text.format.*;

public class SomethingGoodActivity extends Activity {

	private TextView mySubject;
	private TextView kiss;
	private Button message;
	private Button cancel;
	
	public static final String mAPP_ID = "207372532726150";
	public Facebook mFacebook = new Facebook(mAPP_ID);
	
	Time now = new Time();
	
	private ArrayList<Message> wholeThing = new ArrayList<Message>();
	
	private String filepath;
	public double longitude;
	public double lattitude;
	public String time;
	public String textMessage;
	public String subject;
	public String radius;
	public String path = "/mnt/sdcard/Android/data/com.pennapps.vnd.FFling/";
	
	private class LocationUpdateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("msg received!");
			
			Log.i("DbAuthLog", "Receiving intents!", null);
			filepath = getIntent().getStringExtra("file");
			wholeThing = MyFileReader.getWholeFile(filepath);
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending);
        mySubject = (TextView) findViewById(R.id.subject);
        kiss = (TextView) findViewById(R.id.message);
        message = (Button) findViewById(R.id.sendButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        message.setOnClickListener(theSendListener);
        cancel.setOnClickListener(theCancelListener);
    }
    
	private OnClickListener theSendListener = new OnClickListener() {
	    public void onClick(View v) {
	    	String facebookID;
	    	try {
	    		JSONObject json = Util.parseJson(mFacebook.request("me"));
				facebookID = json.getString("id");
			} catch (Exception e) {
				facebookID = "";
				e.printStackTrace();
			} catch (FacebookError e) {
				facebookID = "";
				e.printStackTrace();
			}
	    	
	    	
	    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
	    	Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    	
	    	longitude = location.getLongitude();
	    	lattitude = location.getLatitude();
	    	
	    	now.setToNow();
	    	
	    	
	    	subject = String.valueOf(mySubject.getText());
	    	textMessage = String.valueOf(kiss.getText());
	    	
	    	radius = "15";
	    	
			FileCreator fc = new FileCreator(path);
			Message m = new Message(facebookID, now.toString(), String.valueOf(lattitude), String.valueOf(longitude), textMessage, subject, radius);
			
			String newPath = fc.createNewPaperAirplane(radius, subject, m);
	    }
	};
	
	private String getMessage(){
		
	}
	
	private OnClickListener theCancelListener = new OnClickListener() {
	    public void onClick(View v) {
	    	finish();
	    }
	};
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.something_good, menu);
        return true;
    }
}
