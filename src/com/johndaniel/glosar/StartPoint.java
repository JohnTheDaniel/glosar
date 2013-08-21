package com.johndaniel.glosar;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Hej Daniel! V�lkommen tillbaka!
 * Idag ska du skriva om s�ttet filerna skrivs in i listan.
 * Skapa en array med samma l�ngd som number of files, och
 * skriv en for loop som skickar ut en fil per siffra.
 * Om keep count inst�llningen �r checkat s� ska
 * �ven siffran ing� i namnet om printas ut. 
 * 
 * Det m�ste �ven vara ett s�kert system som inte printar ut filer
 * som inte existerar. T.ex fil nummer 3 kanske raderas av anv�ndaren,
 * det ska inte komma upp en tom lista. 3 f�r inte printas ut. 
 * Kan g�ras genom att kolla om filnamnet �r tomt? addFile.java
 * till�ter inte att man missar filnamnet, s� p� det s�ttet kan man 
 * k�nna igen om det �r en fil som inte existerar med en default output 
 * fr�n sharedpreferences. 
 * 
 * Aja... Du kommer alltid p� allt efter ett en stund a frustation. 
 * 
 * Lycka till!
 * 
 * */

public class StartPoint extends SherlockActivity {
	public static final String EXTRA_POSITION = "com.johndaniel.glosar.POSITION";
	ListView fileListView;
	public static final String PREF_MISC = "StoreSettings"; //Saves stuffs like numberOfFiles.
	public static final String PREF_FILES = "FileStorage"; //Saves the names of the files
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		this.setTitle(getResources().getString(R.string.startPointLabel));
		//N�sta steg: Skapa intent och skicka �ver info om vilken fil som har blivit klickad i listan.
		
		ActionBar ab = getSupportActionBar();
		ab.setIcon(getResources().getDrawable(R.drawable.launcher_white));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.start_point, menu);
		return true;		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//Plus-button pressed. Make a new file.
		case R.id.addNewFileButton:
			Intent intent = new Intent (this, addFile.class);
			startActivity(intent);
			return true;
		case R.id.action_settings:
			Intent settingsIntent = new Intent (this, SettingsActivity.class);
			startActivity(settingsIntent);
			return true;
		default: return super.onOptionsItemSelected(item);
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		/*
		 * It turned out, this wasn't needed
		Intent intent = getIntent();
		String rebootMessage = intent.getStringExtra(addFile.REBOOT_MESSAGE);

		if (rebootMessage != null){
			//do reboot

			finish();
			startActivity(intent);

			//DET FUNKA!!!
		}
		 */

		/* Everything was plased in the resumed, because we need to refresh the list after the user comes back from 
		 * addFile.java. When addFile.java is launched, this activity gets paused. 
		 * When the user turns back from addFile.java, the onResume() method is called, and makes a refresh of the 
		 * list of files. */
		super.onResume();
		//Some stuffs for test toast, not present in release.
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;


		//get the list of files
		SharedPreferences settings = getSharedPreferences(PREF_FILES, 0);
		String files = settings.getAll().toString();

		//Handle data from getAll().
		String[] fileListValues = files.replace("{", "").replace("}", "").replace("=", ". ").split(", ");
		//Latest training first
		Arrays.sort(fileListValues, Collections.reverseOrder());
		


		//Vid f�rsta startup kommer anv�ndaren inte
		//ha n�gra gloslistor. V�lkomsmeddelande.
		/* At first startup, the user will not have anything saved.
		 * This makes a welcome message. */
		if (needTutorialCheck() == true){
			TextView tv = (TextView) findViewById(R.id.prompt);
			tv.setText(getString(R.string.guide)/*"\nInga filer lagrade.\n\nTryck p� plusknappen f�r att l�gga till en tr�ning."*/);
			LinearLayout layout = (LinearLayout) findViewById(R.id.container);
			layout.setBackgroundResource(R.drawable.gray_line_bg);

			Toast toast = Toast.makeText(context, "needTutorialCheck() == true", duration);
			toast.show();
		}
		else {			
			//The tutorial is not needed. Loading the ListView of trainings.
			fileListView = (ListView) findViewById(R.id.fileListView);
			ArrayAdapter<String> fileAdapter = new ArrayAdapter<String>(this,
					R.layout.listitem_style, android.R.id.text1, fileListValues);
			fileListView.setAdapter(fileAdapter);
			fileListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent (view.getContext(), Train.class);
					String positionString = "" + position;
					String trainingName = parent.getItemAtPosition(position).toString();
					String[] trainingNameArr = trainingName.split(". ");
					intent.putExtra(EXTRA_POSITION, trainingNameArr[0]);
					startActivity(intent);
				}
				
			});

			
			//Development toast. Deleted at release.
			Toast toast = Toast.makeText(context, "needTutorialCheck() == false", duration);
			toast.show();
		}
	}
	public boolean needTutorialCheck(){
		// Tutorial is needed if the user hasn't made any files.
		final SharedPreferences filesPrefs = getSharedPreferences(PREF_MISC, 0);
		int trueCheck = filesPrefs.getInt("numberOfFiles", 0);
		if (trueCheck == 0){
			return true; 
		} else {
			return false;
		}		
	}
	
}

