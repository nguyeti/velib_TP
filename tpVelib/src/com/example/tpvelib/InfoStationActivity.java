package com.example.tpvelib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class InfoStationActivity extends Activity {
	private Double longitude, latitude;
	private int available, free;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Intent receivedIntent = getIntent();
		longitude = receivedIntent.getDoubleExtra("longitude", 0);
		latitude = receivedIntent.getDoubleExtra("latitude", 0);
		available = receivedIntent.getIntExtra("available", 0);
		free = receivedIntent.getIntExtra("free", 0);
		
		Toast.makeText(InfoStationActivity.this, "latitude:" + latitude + " , longitude: " + longitude, Toast.LENGTH_LONG).show();
		Toast.makeText(InfoStationActivity.this, "<vélo dispo: " + available + " , emplacement libre: " + free +">", Toast.LENGTH_LONG).show();

		finish();
    }
}
