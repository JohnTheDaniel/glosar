package com.johndaniel.glosar;

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


public class StartPoint extends SherlockFragmentActivity implements ListOfFilesFragment.OnTrainingSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		if(isTablet){ //We are on a TABLET, folks!!
			FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
			
			fm.add(R.id.start_point_container, new ListOfFilesFragment())
			.add(R.id.list_of_files_container, new IconAndTextFragment())
			.commit();
		} else { 
			FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
			fm.add(R.id.start_point_container, new ListOfFilesFragment()).commit();
		}
	}

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
		if (isTablet){ //On tablet: should replace the fragment at the right side
			
			
		} else { 
			OverviewFragment overviewFragment = new OverviewFragment();
			Bundle args = new Bundle();
			args.putString("TRAINING", chosenTraining);
			overviewFragment.setArguments(args);
			
			FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
			fm
			//.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
			.replace(R.id.start_point_container, overviewFragment)
			.addToBackStack(null)
			.commit();
		}
	}
	

}
