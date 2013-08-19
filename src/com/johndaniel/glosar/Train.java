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

public class Train extends SherlockActivity {
	public static final String PREF_MISC = "StoreSettings";
	public static final String PREF_FILES = "FileStorage";
	public static final String TRANSLATIONS = "com.johndaniel.glosar.TRANSLATIONS";
	public static final String NUM_TRANS = "com.johndaniel.glosar.NUM_TRANS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Print out an overview of the words the training holds.
		Intent intent = getIntent();
		String training = intent.getStringExtra(StartPoint.EXTRA_POSITION);
		
		SharedPreferences settings = getSharedPreferences(PREF_FILES, 0); 
		String header = settings.getString(training, "Not Found");
		
		getSupportActionBar().setTitle(header);
		TextView textView = (TextView) findViewById(R.id.trainHeader);
		SharedPreferences thisFile = getSharedPreferences(header, 0);
		String allFromFile = thisFile.getAll().toString();
		textView.setText(allFromFile);
		
		
		/*
		 * The TranslateActivity need to know:
		 * a) How many translations, which will be the same amount TranslateHolders
		 *    and pages in the ViewPager
		 * 
		 * b) The translations the TranslateHolders will translate between.
		 */
		//Getting the amount of translations
		final int numberOfTranslations = allFromFile.split(", ").length;
		//Getting all the translations
		final String translations[] = allFromFile.replace("{", "").replace("}", "").split(", ");
		
		//Button click
		Button btn = (Button) findViewById(R.id.startTrainingBtn);
		btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (Train.this, TranslateActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt(NUM_TRANS, numberOfTranslations);
				bundle.putStringArray(TRANSLATIONS, translations);
				intent.putExtras(bundle);
				startActivity(intent);
				
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
