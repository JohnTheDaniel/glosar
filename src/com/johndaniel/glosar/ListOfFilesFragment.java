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

public class ListOfFilesFragment extends SherlockFragment {
	public static final String EXTRA_POSITION = "com.johndaniel.glosar.POSITION";
	ListView fileListView;
	String[] positionReferer = new String[0];
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
		//NŠsta steg: Skapa intent och skicka šver info om vilken fil som har blivit klickad i listan.
		
		//ActionBar ab = getSupportActionBar();
		//ab.setIcon(getResources().getDrawable(R.drawable.launcher_white));
		return thisView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		//All of this code can now be placed in onCreateView. 
		
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

			if (training != ""){
					//Make array bigger first
					fileListValues = expandStringArray(fileListValues);
					positionReferer = expandStringArray(positionReferer);
					positionReferer[positionReferer.length - 1] = i + 1 + "";
					SharedPreferences settingsPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
					boolean keepCountSetting = settingsPrefs.getBoolean("keep_count", false);
					if (keepCountSetting){
						fileListValues[fileListValues.length - 1] = positionReferer[positionReferer.length - 1] + ". " + training;
					} else {
						fileListValues[fileListValues.length - 1] = training;
					}
				
			}
		}
		//Arrays.sort(fileListValues, Collections.reverseOrder());	
		fileListValues = reverseArray(fileListValues);
		positionReferer = reverseArray(positionReferer);


		//Vid fšrsta startup kommer anvŠndaren inte
		//ha nŒgra gloslistor. VŠlkomsmeddelande.
		/* At first startup, the user will not have anything saved.
		 * This makes a welcome message. */
		if (needTutorialCheck() == true){
			TextView tv = (TextView) thisView.findViewById(R.id.prompt);
			tv.setText(getString(R.string.start_point_no_files_message)/*"\nInga filer lagrade.\n\nTryck pŒ plusknappen fšr att lŠgga till en trŠning."*/);
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
					theListener.showTraining(positionReferer[position]);
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
	public String[] expandStringArray(String[] oldArray) {
		String[] newArray = new String[oldArray.length + 1];
		
		System.out.print(newArray);
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		return newArray;
	}
	
	public static String[] reverseArray(String[] data) {
	    int left = 0;
	    int right = data.length - 1;

	    while( left < right ) {
	        // swap the values at the left and right indices
	        String temp = data[left];
	        data[left] = data[right];
	        data[right] = temp;

	        // move the left and right index pointers in toward the center
	        left++;
	        right--;
	    }
	    return data;
	}
}

