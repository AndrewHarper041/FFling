package com.pennapps.vnd.ffling;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapDemo extends MapActivity {
    
    private MapView mapView;
    private List<Overlay> mapOverlays;
    private static final int latitudeE6 = 37985339;
    private static final int longitudeE6 = 23716735;
    private int userLat = 37985339;
    private int userLong = 23716735;
    private MapController mapController;
    private ArrayList<Location> nearby;
    
    private class LocationUpdateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("msg received!");
			Log.i("DbAuthLog", "Receiving intents!", null);
			ArrayList <String> strs = getIntent().getStringArrayListExtra("stock_list");
			for(String str: strs){
				StringTokenizer st = new StringTokenizer(str, ", ");
				double TempLat = Double.parseDouble(st.nextToken())*14;
				double TempLong = Double.parseDouble(st.nextToken())*14;
				Location tempLoc = new Location("");
				tempLoc.setLatitude(TempLat);
				tempLoc.setLongitude(TempLong);
				nearby.add(tempLoc);
			}
		}
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        //initializes map
        mapView = (MapView) findViewById(R.id.map_view);       
        mapView.setBuiltInZoomControls(true);
        
        //sets center
       // MapController myMapController = mapView.getController();
        //myMapController.setCenter(new GeoPoint(userLat, userLong));
        
        nearby = new ArrayList<Location>();
        
        //creates the overlay
        mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        CustomItemizedOverlay itemizedOverlay = 
             new CustomItemizedOverlay(drawable, this){
        	@Override
			protected boolean onTap(int index){
        		processTap(index);
        		return true;
        	}
        };
        //will be implemented in another method ie. run
        GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);	//to be gotten from filename
        //going to have to loop these
        NotePreview note1 = new NotePreview(latitudeE6, longitudeE6, "It Works!", "hello!");
        NotePreview note2 = new NotePreview(latitudeE6+400, longitudeE6+400, "But will it blend?", "goodbye!");
        itemizedOverlay.addOverlay(note1);
        itemizedOverlay.addOverlay(note2);
        mapOverlays.add(itemizedOverlay);
        
        mapController = mapView.getController();
        
        mapController.animateTo(point);		//moves map center to point
        mapController.setZoom(20);
    }
        
    public void processTap(int index){
    	CustomItemizedOverlay itemOverlay = (CustomItemizedOverlay) mapOverlays.get(0);	//only object is the ItemizedOverlay
    	OverlayItem item = itemOverlay.getItem(index);
    	if(item != null){
    		System.out.println(item.getTitle() + " was tapped!");
    	}
    	else{
    		System.out.println("NULL");
    	}
        mapController.animateTo(item.getPoint());

        //makes alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(item.getTitle());
        builder.setMessage(item.getSnippet());
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
    
    public void updateList(){
    	
    }
    
    public void getInfo(){
    	
    }
    public void updateMap(String [] planes){
    	//check list to see if any were removed.
    	
    }
    
    public void goToMenu(View view){
    	Intent intent = new Intent();
    	setResult(RESULT_OK, intent);
    	finish();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
