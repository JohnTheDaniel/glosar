package com.johndaniel.glosar;

import java.io.File;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class OverviewFragment extends SherlockFragment {
	public static final String PREF_MISC = "StoreSettings";
	public static final String PREF_FILES = "FileStorage";
	public static final String TRANSLATIONS = "com.johndaniel.glosar.TRANSLATIONS";
	public static final String NUM_TRANS = "com.johndaniel.glosar.NUM_TRANS";
	String training;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.train, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.trainDeleteBtn: 
			//Do delete operation over
			//deleteThis(training);
			//Show dialog
			confirmDeleteDialog();
			return true;
		default: return super.onOptionsItemSelected(item);
		}
	}
	
	public void confirmDeleteDialog(){
		new AlertDialog.Builder(getActivity())
	    .setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_entry))
	    .setPositiveButton(getResources().getString(R.string.yes) , new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        	deleteThis(training);
	        }
	     })
	    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View thisView = inflater.inflate(R.layout.activity_train, container, false);

		training = getArguments().getString("TRAINING");
		
		SharedPreferences settings = getActivity().getSharedPreferences(PREF_FILES, 0); 
		String header = settings.getString(training, getString(R.string.not_found));
		
		// Set title bar if not tablet
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		if (!isTablet){
		    ((StartPoint) getActivity())
            .setActionBarTitle(header);
		}
	    
		TextView textView = (TextView) thisView.findViewById(R.id.trainHeader);
		SharedPreferences thisFile = getActivity().getSharedPreferences(header, 0);
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
		Button btn = (Button) thisView.findViewById(R.id.startTrainingBtn);
		btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (getActivity().getBaseContext(), TranslateActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt(NUM_TRANS, numberOfTranslations);
				bundle.putStringArray(TRANSLATIONS, translations);
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
			
		});
		return thisView;
	}
	
	private void deleteThis(String currentTraining){	
		//This code works
		//Remove contents
		SharedPreferences filePrefs = getActivity().getSharedPreferences(PREF_FILES, 0);
		
		String thisPrefsName = filePrefs.getString(currentTraining, "ghshf3gtsh78isfdhsiv").toString();
		SharedPreferences thisPrefs = getActivity().getSharedPreferences(thisPrefsName, 0);
		SharedPreferences.Editor thisPrefsEditor = thisPrefs.edit();
		thisPrefsEditor.clear().commit();
		
		
		//Remove file
		File file1 = new File(getActivity().getApplicationContext().getFilesDir().getParentFile().getPath() + "/shared_prefs/" + thisPrefsName + ".xml");
		file1.delete();
		
		File file2 = new File(getActivity().getApplicationContext().getFilesDir().getParentFile().getPath() + "/shared_prefs/" + thisPrefsName + ".bak");
		if(file2.exists()){
			file2.delete();
		}
		
		//Remove reference
		SharedPreferences.Editor filePrefsEditor = filePrefs.edit();
		filePrefsEditor.putString(currentTraining, "").commit();
		
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		if (!isTablet){
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.start_point_container, new ListOfFilesFragment()).commit();
			// Set title bar
		    ((StartPoint) getActivity())
		            .setActionBarTitle(getString(R.string.old_exercises));
		    ((StartPoint) getActivity()).removeSetHomeAsUp();
		    
		    
		} else if (isTablet){
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.list_of_files_container, new IconAndTextFragment())
			.commit();
			
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.start_point_container, new ListOfFilesFragment()).commit();
			
			Context context = getActivity();
			CharSequence toastText = "I did the replacement, sir!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, toastText, duration);
			toast.show();
		}
	}
}

