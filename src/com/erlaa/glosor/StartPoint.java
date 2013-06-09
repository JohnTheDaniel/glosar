package com.erlaa.glosor;



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
	public static final String PREFS_NAME = "StoreSettings";
	public static final String PREFS_FILE_NUMBER = "numberOfFIles";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		
		/*//get the list of files
		String files;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		files = settings.getAll().toString();
		
		//hantera file, dela och ta bort skit
		files.replace("{", "").replace("}", "");
		files.replace("=", ", ");
		
		String[] fileListValues = files.replace("{", "").replace("}", "").split(", ");
		
		//Vid första startup kommer användaren inte ha några gloslistor. Välkomsmeddelande
		boolean needTutorialCheck = needTutorialCheck();
		if (needTutorialCheck == true){
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
		*/
		
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
		
		//Testar starta om efter jag har fått intent
		Intent intent = getIntent();
		String rebootMessage = intent.getStringExtra(addFile.REBOOT_MESSAGE);
		
		if (rebootMessage != null){
			//do reboot
			
			finish();
			startActivity(intent);
			
			//DET FUNKA!!!
		}
		
		//Lite test toast
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		////TEST

		
		//Måste ladda om listan när man kommer tillbaka till appen
		//efter att man har skrivit in sina glosor. 
		super.onResume();
		//get the list of files
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String files = settings.getAll().toString();
		
		//hantera file, dela och ta bort skit
		files.replace("{", "").replace("}", "");
		files.replace("=", ", ");
		String[] fileListValues = files.replace("{", "").replace("}", "").split(", ");
		
		//Vid första startup kommer användaren inte
		//ha några gloslistor. Välkomsmeddelande.
		if (needTutorialCheck() == true){
			TextView tv = (TextView) findViewById(R.id.prompt);
			tv.setText("\nInga filer lagrade.\n\nTryck på plusknappen för att lägga till en träning.");
			LinearLayout layout = (LinearLayout) findViewById(R.id.container);
			layout.setBackgroundResource(R.drawable.gray_line_bg);
			
			Toast toast = Toast.makeText(context, "needTutorialCheck() == true", duration);
			toast.show();
		}
		else {
			
			
			//Den här koden körs, men utav någon anledning ger den inget resultat om 
			// if ovan har redan körts en gång. 
			
			
			fileListView = (ListView) findViewById(R.id.fileListView);
			ArrayAdapter<String> fileAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, android.R.id.text1, fileListValues);
			fileListView.setAdapter(fileAdapter);
			
			Toast toast = Toast.makeText(context, "needTutorialCheck() == false", duration);
			toast.show();
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

