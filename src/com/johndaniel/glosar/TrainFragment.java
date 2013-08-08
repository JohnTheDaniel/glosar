package com.johndaniel.glosar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;

public class TrainFragment extends Fragment {
	public static final String PREF_MISC = "StoreSettings";
	public static final String PREF_FILES = "FileStorage";
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View thisView = inflater.inflate(R.layout.activity_train, container);
		
		
		
		
		/*Intent intent = getIntent();
		String training = intent.getStringExtra(StartPoint.EXTRA_POSITION);*/
		
		SharedPreferences settings = this.getActivity().getSharedPreferences(PREF_FILES, 0); 
		//String header = settings.getString(training, "Not Found");
		
		// this.getActivity().getSupportActionBar().setTitle(header);
		TextView textView = (TextView) thisView.findViewById(R.id.trainHeader);
		//SharedPreferences thisFile = this.getActivity().getSharedPreferences(header, 0);
		//String allFromFile = thisFile.getAll().toString();
		//textView.setText(allFromFile);
		
		//Set up the button
		Button startTrainingBtn = (Button) thisView.findViewById(R.id.startTrainingBtn);
		startTrainingBtn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return thisView;	
	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View thisView = inflater.inflate(R.layout.activity_train, container);
		return thisView;
	}*/
	
	
}

