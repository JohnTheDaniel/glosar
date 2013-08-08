package com.johndaniel.glosar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;

public class CopyOfTrain extends SherlockActivity {
	public static final String PREF_MISC = "StoreSettings";
	public static final String PREF_FILES = "FileStorage";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		String training = intent.getStringExtra(StartPoint.EXTRA_POSITION);
		
		SharedPreferences settings = getSharedPreferences(PREF_FILES, 0); 
		String header = settings.getString(training, "Not Found");
		
		getSupportActionBar().setTitle(header);
		TextView textView = (TextView) findViewById(R.id.trainHeader);
		SharedPreferences thisFile = getSharedPreferences(header, 0);
		String allFromFile = thisFile.getAll().toString();
		textView.setText(allFromFile);
		
		//Set up the button
		Button startTrainingBtn = (Button) findViewById(R.id.startTrainingBtn);
		startTrainingBtn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.train, menu);
			
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case android.R.id.home:
				Intent intent = new Intent(this, StartPoint.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
            default: return super.onOptionsItemSelected(item);
		}
	}
}
