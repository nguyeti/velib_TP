package com.example.tpvelib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceStation extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

}
