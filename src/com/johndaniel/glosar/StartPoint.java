package com.johndaniel.glosar;

import java.io.File;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;


public class StartPoint extends SherlockFragmentActivity implements ListOfFilesFragment.OnTrainingSelectedListener {
	public static final String PREF_MISC = "StoreSettings";
	public static final String PREF_FILES = "FileStorage";
	boolean showingOverview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){ //We had a reboot. We need to replace, not add, the fragments
			setContentView(R.layout.activity_start_point);
			ActionBar actionBar = getSupportActionBar();
			boolean isTablet = getResources().getBoolean(R.bool.isTablet);
			if(isTablet){ //We are on a TABLET, folks!!
				FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
				
				fm.replace(R.id.start_point_container, new ListOfFilesFragment())
				.replace(R.id.list_of_files_container, new IconAndTextFragment())
				.commit();
				actionBar.setTitle("Glosar");
			} else { 
				FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
				fm.replace(R.id.start_point_container, new ListOfFilesFragment()).commit();
				actionBar.setTitle("Gamla švningar");
			}
		} else if (savedInstanceState == null) {
			setContentView(R.layout.activity_start_point);
			ActionBar actionBar = getSupportActionBar();
			boolean isTablet = getResources().getBoolean(R.bool.isTablet);
			if(isTablet){ //We are on a TABLET, folks!!
				FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
				
				fm.add(R.id.start_point_container, new ListOfFilesFragment())
				.add(R.id.list_of_files_container, new IconAndTextFragment())
				.commit();
				actionBar.setTitle("Glosar");
			} else { 
				FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
				fm.add(R.id.start_point_container, new ListOfFilesFragment()).commit();
				actionBar.setTitle("Gamla švningar");
			}
		}
		showingOverview = false;
	}
	

	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		if(isTablet){ //We are on a TABLET, folks!!
			FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
			
			fm.replace(R.id.start_point_container, new ListOfFilesFragment())
			.replace(R.id.list_of_files_container, new IconAndTextFragment())
			.commit();
		} else { 
			FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
			fm.replace(R.id.start_point_container, new ListOfFilesFragment()).commit();
		}
		showingOverview = false;
	} */


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.start_point, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		case R.id.addNewFileButton: 
			Intent addIntent = new Intent(this, addFile.class);
			startActivity(addIntent);
			return true;
		case R.id.action_settings:
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
			return true;
		default: return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void showTraining(String chosenTraining) {
		// TODO Auto-generated method stub
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		
		OverviewFragment overviewFragment = new OverviewFragment();
		Bundle args = new Bundle();
		args.putString("TRAINING", chosenTraining);
		overviewFragment.setArguments(args);
		
		FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
		
		if (isTablet){ //On tablet: should replace the fragment at the right side
			fm.replace(R.id.list_of_files_container, overviewFragment);
			
		} else { 
			//.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
			fm.replace(R.id.start_point_container, overviewFragment);
			
			ActionBar actionBar = getSupportActionBar();
			//actionBar.setTitle(chosenTraining);
		}
		fm.addToBackStack(null)
		.commit();
		showingOverview = true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if (!isTablet && showingOverview){
				showingOverview = false;
				
				ActionBar actionBar = getSupportActionBar();
				actionBar.setTitle("Gamla švningar");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putBoolean("Reboot", true);
	}
	
	public void setActionBarTitle(String title) {
	    getSupportActionBar().setTitle(title);
	}
}
