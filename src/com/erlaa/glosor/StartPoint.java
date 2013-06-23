package com.erlaa.glosor;

import java.lang.reflect.Array;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StartPoint extends SherlockActivity {
	
	ListView fileListView;
	public static final String PREF_MISC = "StoreSettings"; //Saves stuffs like numberOfFiles.
	public static final String PREF_FILES = "FileStorage"; //Saves the names of the files
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		
		//Nästa steg: Skapa intent och skicka över info om vilken fil som har blivit klickad i listan. 
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
		}
		return super.onOptionsItemSelected(item);
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


		
		//Måste ladda om listan när man kommer tillbaka till appen
		//efter att man har skrivit in sina glosor. 
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
		files.replace("{", "").replace("}", "");
		files.replace("=", ", ");
		String[] fileListValues = files.replace("{", "").replace("}", "").split(", ");
		
		//sort
		
		
		//Vid första startup kommer användaren inte
		//ha några gloslistor. Välkomsmeddelande.
		/* At first startup, the user will not have anything saved.
		 * This makes a welcome message. */
		if (needTutorialCheck() == true){
			TextView tv = (TextView) findViewById(R.id.prompt);
			tv.setText("\nInga filer lagrade.\n\nTryck på plusknappen för att lägga till en träning.");
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

