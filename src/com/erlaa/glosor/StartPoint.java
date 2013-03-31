package com.erlaa.glosor;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class StartPoint extends Activity {
	
	ListView fileListView;
	public static final String PREFS_NAME = "FileList";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		EditText toAdd, word2;
		//get the list of files
		String file;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		file = settings.getAll().toString();
		
		//hantera file, dela och ta bort skit
		file.replace("{", "").replace("}", "");
		file.replace("=", ", ");
		
		//String[] fileListValues = file.split(", ");
		String[] fileListValues = new String[] {
				/*"Fil1", "Fil2"*/
		};
		
		//Vid första startup kommer användaren inte ha några gloslistor. Välkomsmeddelande
		if (fileListValues.length == 0){
			TextView tv = (TextView) findViewById(R.id.prompt);
			tv.setText("\nInga filer lagrade.\n\nTryck på plusknappen för att lägga till en träning.");
			LinearLayout layout = (LinearLayout) findViewById(R.id.container);
			layout.setBackgroundResource(R.drawable.gray_line_bg);
		}
		else {
		fileListView = (ListView) findViewById(R.id.fileListView);
		ArrayAdapter<String> fileAdapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_list_item_1, android.R.id.text1, fileListValues);
		fileListView.setAdapter(fileAdapter);
		}
		
		//Nästa steg: Skapa intent och skicka över info om vilken fil som har blivit klickad i listan.
		
		 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_point, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addNewFileButton:
		Intent intent = new Intent (this, addFile.class);
		startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
