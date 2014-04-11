package com.example.lokiwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainScreenActivity extends Activity {

	TextView timerDisplay;
	TextView justAteButton;
	TextView wentOutsideButton;
	Handler myHandler = new Handler();
	
	long timeUntilDoom;			//value in milliseconds
	long startTime;				//value in milliseconds
	long timeElapsed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		timerDisplay = (TextView)findViewById(R.id.timerValue);
		justAteButton = (TextView)findViewById(R.id.justAteButton);
		wentOutsideButton = (TextView)findViewById(R.id.wentOutsideButton);
		timeUntilDoom = 7201000;		//2 hours (in milliseconds)
		startTime = SystemClock.uptimeMillis();
		
		timerDisplay.setText(fixTimeFormat(timeUntilDoom));
		myHandler.postDelayed(updateTimerMethod, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	
	private String fixTimeFormat(long timeInSeconds){
		int hours;
		int minutes;
		int seconds;
		
		seconds = (int)timeInSeconds/1000;
		minutes = seconds/60;
		hours = minutes/60;
		
		seconds = seconds % 60;
		minutes = minutes % 60;
		
		return hours + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
	}
	
	public void justAteButton(View v){
		Log.d("method reached", "just ate");
		startTime = SystemClock.uptimeMillis() - 5400000;
	}
	
	public void wentOutsideButton(View v){
		Log.d("went outside", "got to method");
		startTime = SystemClock.uptimeMillis();
	}
	
	private Runnable updateTimerMethod = new Runnable() {

		public void run() {
			timeElapsed = (SystemClock.uptimeMillis() - startTime);
			long timeShown = timeUntilDoom - timeElapsed;
			
			if(timeElapsed >= timeUntilDoom){
				timeShown = 0;
				//signal alarm
			}//doom reached
			
			timerDisplay.setText(fixTimeFormat(timeShown));
			myHandler.postDelayed(this, 0);
		}

	};


}
