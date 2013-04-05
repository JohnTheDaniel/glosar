package com.erlaa.glosor;



import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class StartPoint extends SherlockActivity {
	
	ListView fileListView;
	public static final String PREFS_NAME = "StoreSettings";
	public static final String PREFS_FILE_NUMBER = "numberOfFIles";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		EditText toAdd, word2;
		//get the list of files
		String files;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		files = settings.getAll().toString();
		
		//hantera file, dela och ta bort skit
		files.replace("{", "").replace("}", "");
		files.replace("=", ", ");
		
		String[] fileListValues = files.replace("{", "").replace("}", "").split(", ");
		
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
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.start_point, menu);
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		//Måste ladda om listan när man kommer tillbaka till appen
		//efter att man har skrivit in sina glosor. 
		super.onResume();
		//get the list of files
		String files;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		files = settings.getAll().toString();
		
		//hantera file, dela och ta bort skit
		files.replace("{", "").replace("}", "");
		files.replace("=", ", ");
		String[] fileListValues = files.replace("{", "").replace("}", "").split(", ");
		
		//Vid första startup kommer användaren inte
		//ha några gloslistor. Välkomsmeddelande.
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
	}
}

