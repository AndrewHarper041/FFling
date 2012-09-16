package com.pennapps.vnd.ffling;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class NotePreview extends OverlayItem {
	String Date, Title;
	
	public NotePreview(int lon, int lat, String Title, String Date){
		super(new GeoPoint (lon, lat), Title, Date);
		this.Date = Date;
		this.Title = Title;
	}
	
	public String getTitle(){
		return Title;
	}
	public String getDate(){
		return Date;
	}
}
