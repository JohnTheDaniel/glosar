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


public class StartPoint extends SherlockFragmentActivity implements ListOfFilesFragment.OnTrainingSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		if(!isTablet){
			FrameLayout container = (FrameLayout) findViewById(R.id.start_point_container);
			FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
			
			fm.add(R.id.start_point_container, new ListOfFilesFragment()).commit();
		} else { //We are on a TABLET, folks!!
			FrameLayout container = (FrameLayout) findViewById(R.id.start_point_container);
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
	public void showTraining(String chosenTraining) {
		// TODO Auto-generated method stub
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		if (!isTablet){
			OverviewFragment overviewFragment = new OverviewFragment();
			Bundle args = new Bundle();
			args.putString("TRAINING", chosenTraining);
			overviewFragment.setArguments(args);
			
			FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
			fm.replace(R.id.start_point_container, overviewFragment);
			
		} else { //On tablet: should replace the fragment at the right side
			
		}
	}

}
