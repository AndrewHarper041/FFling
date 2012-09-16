package com.pennapps.vnd.ffling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class BackgroundService extends Service {

	private final IBinder mBinder = new LocalBinder();
	private Context myContext;
	private NotificationManager mNM;
	private LocationManager myLocation;
	private LocationListener locationListener = new MyLocationListener();
	public DropboxAPI<AndroidAuthSession> mDBApi;
	private Entry secretList;
	private float DROPPOINT_DISTANCE = 10;
	
	// Unique Identification Number for the Notification.
	// We use it on Notification start, and to cancel it.
	private int NOTIFICATION = 512;

	@Override
	public void onCreate() {
		myContext = this;
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		myLocation = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = myLocation.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!gpsEnabled) {
			enableLocationSettings();
		}

		File directories = new File("/mnt/sdcard/Android/data/com.pennapps.vnd.ffling");
		if (!directories.exists()) directories.mkdirs();
		
		myLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 0, locationListener);
	}

	private void enableLocationSettings() {

	}

	/**
	 * Class for clients to access. Because we know this service always runs in
	 * the same process as its clients, we don't need to deal with IPC.
	 */
	public class LocalBinder extends Binder {
		BackgroundService getService() {
			return BackgroundService.this;
		}
	}

	private final class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location locFromGps) {
			//Log.i("DbAuthLog", "Getting updates!", null);
			//sendBroadcast(new Intent("CHANGE"));
			getShitDone();
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
	
	public void getShitDone() {
		try {
			secretList = mDBApi.metadata("/", 0, null, true, null);
			Log.i("DbExampleLog", secretList.contents.toString());
			for (int i = 0; i < secretList.contents.size(); i = i + 1) {
				if (!secretList.contents.get(i).fileName().equalsIgnoreCase("Public") && !secretList.contents.get(i).fileName().equalsIgnoreCase("Photos")) {
					String filename = secretList.contents.get(i).fileName();
					StringTokenizer st = new StringTokenizer(filename, "~");
					double TempLat = Double.parseDouble(st.nextToken());
					double TempLong = Double.parseDouble(st.nextToken());
					Location tempPos = new Location("placeholder");
					tempPos.setLatitude(TempLat);
					tempPos.setLongitude(TempLong);
					if (myLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).distanceTo(tempPos) < DROPPOINT_DISTANCE)
					{
					//if (true) {
						// Get file.
						FileOutputStream outputStream = null;
						try {
							File file = new File("/mnt/sdcard/Android/data/com.pennapps.vnd.ffling/" + secretList.contents.get(i).fileName() + ".txt");
							outputStream = new FileOutputStream(file);
							DropboxFileInfo info = mDBApi.getFile("/" + secretList.contents.get(i).fileName(), null, outputStream, null);
							Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
							mDBApi.delete("/" + secretList.contents.get(i).fileName());
						} catch (DropboxException e) {
							Log.e("DbExampleLog",
									"Something went wrong while downloading.");
						} catch (FileNotFoundException e) {
							Log.e("DbExampleLog", "File not found.");
						} finally {
							if (outputStream != null) {
								try {
									outputStream.close();
								} catch (IOException e) {
								}
							}
						}
					}
				}
			}
		} catch (DropboxException e) {
			Log.e("DbExampleLog",
					"Something went wrong while getting metadata.");
		}
	}
	
	public void postaholic(String filePathToPost) {
		FileInputStream inputStream = null;
		try {
			File file = new File(filePathToPost);
			inputStream = new FileInputStream(file);
			Entry newEntry = mDBApi.putFile("/testing.txt", inputStream,
					file.length(), null, null);
			Log.i("DbExampleLog", "The uploaded file's rev is: " + newEntry.rev);
		} catch (DropboxUnlinkedException e) {
			Log.e("DbExampleLog", "User has unlinked.");
		} catch (DropboxException e) {
			Log.e("DbExampleLog", "Something went wrong while uploading.");
		} catch (FileNotFoundException e) {
			Log.e("DbExampleLog", "File not found.");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Show a notification whenever we decide to.
	 */
	private void showNotification() {
		CharSequence text = getText(R.string.connected);
		Notification notification = new Notification(R.drawable.ic_launcher, text, System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		notification.setLatestEventInfo(this, getText(R.string.connected), text, contentIntent);
		mNM.notify(NOTIFICATION, notification);
	}
	
	public Entry getList(){
		return secretList;
	}
}