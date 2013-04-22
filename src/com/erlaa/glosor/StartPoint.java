package com.erlaa.glosor;



import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
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
		
		//Kollar om det �r f�sta starten
		/*final SharedPreferences filesPrefs = getSharedPreferences("StoreSettings", 0);
		SharedPreferences.Editor filesEditor = filesPrefs.edit();
		boolean firstTime = filesPrefs.getBoolean("firstTime", true);
		if (firstTime == true) { 
		    filesEditor.putBoolean("firstTime", false);
		    filesEditor.putInt("numberOfFiles", 0);
		    filesEditor.commit();
		}*/
		
		
		//get the list of files
		String files;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		files = settings.getAll().toString();
		
		//hantera file, dela och ta bort skit
		files.replace("{", "").replace("}", "");
		files.replace("=", ", ");
		
		String[] fileListValues = files.replace("{", "").replace("}", "").split(", ");
		
		//Vid f�rsta startup kommer anv�ndaren inte ha n�gra gloslistor. V�lkomsmeddelande
		boolean needTutorialCheck = needTutorialCheck();
		if (needTutorialCheck == true){
			TextView tv = (TextView) findViewById(R.id.prompt);
			tv.setText("\nInga filer lagrade.\n\nTryck p� plusknappen f�r att l�gga till en tr�ning.");
			LinearLayout layout = (LinearLayout) findViewById(R.id.container);
			layout.setBackgroundResource(R.drawable.gray_line_bg);
		}
		else {
		fileListView = (ListView) findViewById(R.id.fileListView);
		ArrayAdapter<String> fileAdapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_list_item_1, android.R.id.text1, fileListValues);
		fileListView.setAdapter(fileAdapter);
		}
		
		//N�sta steg: Skapa intent och skicka �ver info om vilken fil som har blivit klickad i listan.
		
		 
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
		
		//M�ste ladda om listan n�r man kommer tillbaka till appen
		//efter att man har skrivit in sina glosor. 
		super.onResume();
		//get the list of files
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String files = settings.getAll().toString();
		
		//hantera file, dela och ta bort skit
		files.replace("{", "").replace("}", "");
		files.replace("=", ", ");
		String[] fileListValues = files.replace("{", "").replace("}", "").split(", ");
		
		//Vid f�rsta startup kommer anv�ndaren inte
		//ha n�gra gloslistor. V�lkomsmeddelande.
		if (fileListValues.length == 0){
			TextView tv = (TextView) findViewById(R.id.prompt);
			tv.setText("\nInga filer lagrade.\n\nTryck p� plusknappen f�r att l�gga till en tr�ning.");
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
	public boolean needTutorialCheck(){
		//check if first startup
		final SharedPreferences filesPrefs = getSharedPreferences("StoreSettings", 0);
		int trueCheck = filesPrefs.getInt("numberOfFiles", 0);
		if (trueCheck == 0){
			return true; 
		} else {
			return false;
		}		
	}
}

