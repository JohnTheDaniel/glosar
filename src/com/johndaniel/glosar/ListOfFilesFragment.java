package com.johndaniel.glosar;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Hej Daniel! Välkommen tillbaka!
 * Idag ska du skriva om sättet filerna skrivs in i listan.
 * Skapa en array med samma längd som number of files, och
 * skriv en for loop som skickar ut en fil per siffra.
 * Om keep count inställningen är checkat så ska
 * även siffran ingå i namnet om printas ut. 
 * 
 * Det måste även vara ett säkert system som inte printar ut filer
 * som inte existerar. T.ex fil nummer 3 kanske raderas av användaren,
 * det ska inte komma upp en tom lista. 3 får inte printas ut. 
 * Kan göras genom att kolla om filnamnet är tomt? addFile.java
 * tillåter inte att man missar filnamnet, så på det sättet kan man 
 * känna igen om det är en fil som inte existerar med en default output 
 * från sharedpreferences. 
 * 
 * Aja... Du kommer alltid på allt efter ett en stund a frustation. 
 * 
 * Lycka till!
 * 
 * */

public class ListOfFilesFragment extends SherlockFragment {
	public static final String EXTRA_POSITION = "com.johndaniel.glosar.POSITION";
	ListView fileListView;
	public static final String PREF_MISC = "StoreSettings"; //Saves stuffs like numberOfFiles.
	public static final String PREF_FILES = "FileStorage"; //Saves the names of the files
	View thisView;
	OnTrainingSelectedListener theListener;
	
	public interface OnTrainingSelectedListener {
		public void showTraining(String chosenTraining);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu,
			MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.list_files_fragment_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		case R.id.addNewFileButton:
			Intent intent = new Intent(getActivity(), addFile.class);
			startActivity(intent);
			return true;
		default: return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thisView = inflater.inflate(R.layout.fragment_start_point, container, false);
		//this.setTitle(getResources().getString(R.string.startPointLabel));
		//Nästa steg: Skapa intent och skicka över info om vilken fil som har blivit klickad i listan.
		
		//ActionBar ab = getSupportActionBar();
		//ab.setIcon(getResources().getDrawable(R.drawable.launcher_white));
		return thisView;
	}
	/*@Override
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
		
	}*/

	@Override
	public void onResume() {
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
		Context context = getActivity();
		int duration = Toast.LENGTH_LONG;


		//get the list of files
		SharedPreferences filesPrefs = getActivity().getSharedPreferences(PREF_FILES, 0);
		String files = filesPrefs.getAll().toString();

		/*//Handle data from getAll().
		String[] fileListValues = files.replace("{", "").replace("}", "").replace("=", ". ").split(", ");
		//Latest training first
		Arrays.sort(fileListValues, Collections.reverseOrder());*/
		//Arrays.sort(fileListValues);
		int numberOfFiles = getActivity().getSharedPreferences(PREF_MISC, 0).getInt("numberOfFiles", 0);
		
		
		String[] fileListValues = new String[0];
		for (int i = 0; i < numberOfFiles; i++){
			String training = filesPrefs.getString(i + 1 + "", "hello");
			//String training = "hello";
			//fileListValues[i] = i + 1 + ". " + training;
			if (training != ""){
				/*if (fileListValues.length == 1){
					fileListValues[0] = i + 1 + ". " + training;
				} else {*/
					//Make array bigger first
					fileListValues = expandArray(fileListValues);
					SharedPreferences settingsPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
					boolean keepCountSetting = settingsPrefs.getBoolean("keep_count", false);
					if (keepCountSetting){
						fileListValues[fileListValues.length - 1] = i + 1 + ". " + training;
					} else {
						fileListValues[fileListValues.length - 1] = training;
					}
				
			}
		}
		Arrays.sort(fileListValues, Collections.reverseOrder());


		//Vid första startup kommer användaren inte
		//ha några gloslistor. Välkomsmeddelande.
		/* At first startup, the user will not have anything saved.
		 * This makes a welcome message. */
		if (needTutorialCheck() == true){
			TextView tv = (TextView) thisView.findViewById(R.id.prompt);
			tv.setText(getString(R.string.start_point_no_files_message)/*"\nInga filer lagrade.\n\nTryck på plusknappen för att lägga till en träning."*/);
			LinearLayout layout = (LinearLayout) thisView.findViewById(R.id.container);
			layout.setBackgroundResource(R.drawable.gray_line_bg);

			Toast toast = Toast.makeText(context, "needTutorialCheck() == true", duration);
			toast.show();
		}
		else {			
			//The tutorial is not needed. Loading the ListView of trainings.
			fileListView = (ListView) thisView.findViewById(R.id.fileListView);
			ArrayAdapter<String> fileAdapter = new ArrayAdapter<String>(getActivity(),
					R.layout.listitem_style, android.R.id.text1, fileListValues);
			fileListView.setAdapter(fileAdapter);
			fileListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//Intent intent = new Intent (view.getContext(), OverviewFragment.class);
					String positionString = "" + position;
					String trainingName = parent.getItemAtPosition(position).toString();
					String[] trainingNameArr = trainingName.split(". ");
					//intent.putExtra(EXTRA_POSITION, trainingNameArr[0]);
					//startActivity(intent);*/
					theListener.showTraining(trainingNameArr[0]);
				}
				
			});

			
			//Development toast. Deleted at release.
			Toast toast = Toast.makeText(context, "needTutorialCheck() == false", duration);
			toast.show();
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			theListener = (OnTrainingSelectedListener) activity;
		} catch(ClassCastException e) {
			throw new ClassCastException(activity.toString() + "must implement OnArticleSelectedListener");
		}
	}

	public boolean needTutorialCheck(){
		// Tutorial is needed if the user hasn't made any files.
		final SharedPreferences filesPrefs = getActivity().getSharedPreferences(PREF_MISC, 0);
		int trueCheck = filesPrefs.getInt("numberOfFiles", 0);
		if (trueCheck == 0){
			return true; 
		} else {
			return false;
		}		
	}
	public String[] expandArray(String[] oldArray) {
		String[] newArray = new String[oldArray.length + 1];
		
		System.out.print(newArray);
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		return newArray;
	}
	
}

