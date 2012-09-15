package com.pennapps.vnd.ffling;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends Service {
	
    private final IBinder mBinder = new LocalBinder();
	private Context myContext;
    private NotificationManager mNM;
    private LocationManager myLocation;
    private LocationListener locationListener = new MyLocationListener();

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = 512;
    
    @Override
    public void onCreate() {
    	myContext = this;
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        myLocation = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = myLocation.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            enableLocationSettings();
        }
        
        myLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 0, locationListener);
        showNotification();     
    }
    
    private void enableLocationSettings() {

    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        BackgroundService getService() {
            return BackgroundService.this;
        }
    }
    
	private final class MyLocationListener implements LocationListener {
		
		public void onLocationChanged(Location locFromGps) {
			Toast.makeText(myContext, locFromGps.toString(), Toast.LENGTH_SHORT).show();
		}
		
		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	}
    


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.connected);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher, text, System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.connected),text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
}