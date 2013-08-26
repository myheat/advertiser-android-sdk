package com.gocpa.gocpasdksample;

import com.gocpa.android.sdk.GocpaTracker;
import com.gocpa.android.sdk.GocpaUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button buttonTest1 = (Button)findViewById(R.id.button1);  
		buttonTest1.setOnClickListener(onClickListener);  
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private OnClickListener onClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			GocpaTracker.getInstance(MainActivity.this).reportDevice();
			//Toast.makeText(MainActivity.this, GocpaTracker.getInstance(MainActivity.this).reportDevice(), Toast.LENGTH_SHORT).show();
		}
	};

}
