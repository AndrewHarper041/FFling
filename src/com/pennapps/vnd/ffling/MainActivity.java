package com.pennapps.vnd.ffling;
/* Some shit */
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private boolean mIsBound = false;
	private BackgroundService mBoundService;
	private Context mainContext;
	final static private String APP_KEY = "9znvgiquask661b";
	final static private String APP_SECRET = "bnk406c2yk82fc8";
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;
	private DropboxAPI<AndroidAuthSession> mDBApi;
	//private LocationUpdateReceiver locationUpdateReceiver;

	/*
	 * Only here in case we need it later
	  --implement only in the map frame, other frames do not need it.
	  
	private class LocationUpdateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("DbAuthLog", "Receiving intents!", null);
			if (intent.getAction().equals("CHANGE")) {
				//getShitDone();
			}
		}
	}
	
	*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
		mDBApi.getSession().startAuthentication(MainActivity.this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainContext = this;
		//Button testButton = (Button) findViewById(R.id.buttonNew);
		//testButton.setOnClickListener(mCorkyListener);
	}

	private OnClickListener mCorkyListener = new OnClickListener() {
	    public void onClick(View v) {
	      mBoundService.getShitDone();
	    }
	};

	@Override
	protected void onResume() {
		super.onResume();
		
		/*
		
		Same as above
		
		if (locationUpdateReceiver == null) locationUpdateReceiver = new LocationUpdateReceiver();
		IntentFilter intentFilter = new IntentFilter("CHANGE");
		registerReceiver(locationUpdateReceiver, intentFilter);

		*/

		if (mDBApi.getSession().authenticationSuccessful()) {
			try {
				mDBApi.getSession().finishAuthentication();
				AccessTokenPair tokens = mDBApi.getSession().getAccessTokenPair();
				// Provide your own storeKeys to persist the access token pair
				// A typical way to store tokens is using SharedPreferences
				// storeKeys(tokens.key, tokens.secret);
			} catch (IllegalStateException e) {
				Log.i("DbAuthLog", "Error authenticating", e);
			}
		}
		doBindService();
	}

	@Override
	protected void onPause(){
		super.onPause();
		//if (locationUpdateReceiver != null) unregisterReceiver(locationUpdateReceiver);
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mBoundService = ((BackgroundService.LocalBinder) service).getService();
			mBoundService.mDBApi = mDBApi;
			Toast.makeText(mainContext, R.string.connected, Toast.LENGTH_SHORT).show();
		}

		public void onServiceDisconnected(ComponentName className) {
			mBoundService = null;
			Toast.makeText(mainContext, R.string.disconnected, Toast.LENGTH_SHORT).show();
		}
	};

	void doBindService() {
		bindService(new Intent(mainContext, BackgroundService.class), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(mConnection);
			mIsBound = false;
		}
	}

	public void goTo(View view){
		Intent myIntent = new Intent();
		switch (view.getId()){
			case	R.id.buttonMail: myIntent = new Intent(view.getContext(), MailboxActivity.class);
					break;
			case	R.id.buttonMap: myIntent = new Intent(view.getContext(), MapDemo.class);
					break;
			case	R.id.buttonNew: myIntent = new Intent(view.getContext(), SomethingGoodActivity.class);
					//myIntent.putExtra("service", mBoundService);
					break;
		}
		startActivityForResult(myIntent, 0);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		      if (resultCode == 666) {
		        mBoundService.postaholic(data.getStringExtra("filepath"));
		      }
		}
	
}


