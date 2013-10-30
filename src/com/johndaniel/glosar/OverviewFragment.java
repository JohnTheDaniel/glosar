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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
	public static final String REVERSE_TRANSLATION = "com.johndaniel.glosar.REVERSE_TRAINING";
	String training;
	RelativeLayout wordsContainer;
	int counter = 4;
	CheckBox reverseTrainingBox;
	boolean reverseTraining = false;
	
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
			//Confirm delete
			//Show dialog
			confirmDeleteDialog();
			return true;
		case R.id.overviewEditBtn:
			Intent intent = new Intent(getActivity(), EditActivity.class);
			Bundle bndl = new Bundle();
			bndl.putString("TRAINING", training);
			intent.putExtras(bndl);
			startActivity(intent);
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
	    
		//TextView textView = (TextView) thisView.findViewById(R.id.trainHeader);
		SharedPreferences thisFile = getActivity().getSharedPreferences(header, 0);
		String allFromFile = thisFile.getAll().toString();
		//textView.setText(allFromFile);
		
		//Getting the amount of translations
		final int numberOfTranslations = allFromFile.split(", ").length;
		
		
		String words[] = allFromFile.replace("{", "").replace("}", "").split(", ");
		ListView wordsListView = (ListView) thisView.findViewById(R.id.overview_fragment_listview);
		View footerView =  inflater.inflate(R.layout.overview_fragment_listview_footer, null, false);
        wordsListView.addFooterView(footerView);
        wordsListView.setAdapter(new OverviewFragmentListAdapter(getActivity(), words));
		
		
		/*
		 * The TranslateActivity need to know:
		 * a) How many translations, which will be the same amount TranslateHolders
		 *    and pages in the ViewPager
		 * 
		 * b) The translations the TranslateHolders will translate between.
		 */
		//Getting all the translations
		final String translations[] = allFromFile.replace("{", "").replace("}", "").split(", ");
		
		//Button click
		Button btn = (Button) footerView.findViewById(R.id.startTrainingBtn);
		
		//Find the checkboxes, which is placed in the footerView.
		reverseTrainingBox = (CheckBox) footerView.findViewById(R.id.overview_reverse_training_box);
		btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (getActivity().getBaseContext(), TranslateActivity.class);
				Bundle bundle = new Bundle();
				boolean reverseTraining = reverseTrainingBox.isChecked();
				bundle.putBoolean(REVERSE_TRANSLATION, reverseTraining);
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
			
			//Development toast!
			Context context = getActivity();
			CharSequence toastText = "I did the replacement, sir!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, toastText, duration);
			toast.show();
		}
	}
	public void makeWordsViews(String word1, String word2){
		//Calculate width
		int dps = 48; //Value, in this case height, described in density pixels
		//Calculate the density pixels height in normal pixels.
		final float scale = getActivity().getResources().getDisplayMetrics().density;
		final int pixels = (int) (dps * scale + 0.5f);
		//The layoutparams for the editTexts. This will give the EditTexts an height of pixels (calculated dps), the weight 1.
		//The weight will cause the EditTexts to share the amount of space inside the container. 
		final LayoutParams textWeightParams = new LinearLayout.LayoutParams(0, pixels, 1.0f);
		
		
		/* The counter is the key for this operation. It helps 
		 * us to keep track on the id's for the editTexts and the 
		 * wrapper. Each editText has a value given from the counter,
		 * and each wrapper has the -counter id value. */
		
		//Here begins the creation of the editTexts.
		//Create two editTexts, one for the word (key), and one for the translation
		TextView initword1 = new TextView(getActivity()); 
		TextView initword2 = new TextView(getActivity());
		initword1.setText(word1);
		initword2.setText(word2);
		
		//Create the container for the two editTexts
		LinearLayout initWordWrapper = new LinearLayout(getActivity());
		//initWordWrapper.setBackgroundColor(0xFFFFFFFF);
		initWordWrapper.setOrientation(LinearLayout.HORIZONTAL);

		
		//The two EditTexts must have same width, therefore they have both the weight of 1 (1.0f)
		initword1.setLayoutParams(textWeightParams);
		initword2.setLayoutParams(textWeightParams);
		
		
		//add the EditTexts to the container
		initWordWrapper.addView(initword1);
		initWordWrapper.addView(initword2);
		

		//The newly added wordWrapper must always be placed below the already placed wordWrapper
		//In this case, there are no wordwrappers.
		RelativeLayout.LayoutParams wrapperParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, pixels);
		/* The wrapper must always be placed under the previous wrapper. 
		 * The wrapper has the id -counter, counter+1 will give the us the
		 * id of the previously added counter. */
		
		wrapperParams.addRule(RelativeLayout.BELOW, -counter+2);

		initWordWrapper.setLayoutParams(wrapperParams);
		
		//Setting up the id's. 
		/*The idea is to make it possible to get the Strings from id's using a 
		 * for loop and then printing them to this sharedPreference file. 
		 * 
		 * String from id1 = String from id2
		 * String from id3 = String from id4
		 * etc.*/
		counter++;
		initWordWrapper.setId(-counter);
		initword1.setId(counter);
		counter++;
		initword2.setId(counter);
				
		//Scroll down to bottom after added the new wordWrapper.
		((RelativeLayout) wordsContainer).addView(initWordWrapper);
		
		//animate in the wordWrapper
		Animation animation = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.slide_right_in);
		animation.setStartOffset(0);
		//initWordWrapper.startAnimation(animation);
	}
}

