package com.johndaniel.glosar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class addFile extends SherlockActivity {
	int counter; 
	int text;
	Activity activityRaw;
	EditText nameField; //The name of the training. Needs to be placed here to be accessed by all methods.
	public static final String PREF_MISC = "StoreSettings";
	public static final String PREF_FILES = "FileStorage";
	SharedPreferences thisFilePrefs;
	public final static String REBOOT_MESSAGE = "com.erlaa.glosor.REBOOT_MESSAGE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new);
		
		
		activityRaw = this;
		//Getting number of trainings
		//Used to name file, and also decide if whe need the tutorial on StartPoint.java
		SharedPreferences filePrefs = getSharedPreferences(PREF_MISC, 0);
		int filesNumber = filePrefs.getInt("numberOfFiles", 0);

		
		//Write toast containing the amount of files.
		//This is for development only, it will get deleted on release. 
		Context context = getApplicationContext();
		CharSequence toastText = filesNumber + " filer";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, toastText, duration);
		toast.show();

		//Pairing id's to variables.
		final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
		final ScrollView containerScrollView = (ScrollView) findViewById(R.id.container);
		final LinearLayout addWordButton = (LinearLayout) findViewById(R.id.bottomButton);
		nameField = (EditText) findViewById(R.id.addNewEditText);
		
		
		
		//Place the initial EditTexts
		
		//Calculate width
		int dps = 48; //Value, in this case height, described in density pixels
		//Calculate the density pixels height in normal pixels.
		final float scale = getBaseContext().getResources().getDisplayMetrics().density;
		final int pixels = (int) (dps * scale + 0.5f);
		
		final LayoutParams weightParams = new LinearLayout.LayoutParams(0, pixels, 1.0f);
		
		counter = 2;
		//add first word.
		/*TextView startWords = new TextView(this);
		startWords.setText("Word number 1");
		startWords.setId(-counter);
		startWords.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		relativeLayout.addView(startWords);*/
		
		//HERE STARTS THE NEW STUFF
		//The two words paired to each other:
		EditText initword1 = new EditText(addFile.this); 
		EditText initword2 = new EditText(addFile.this);
		
		//The container of the two EditTexts
		LinearLayout initWordWrapper = new LinearLayout(addFile.this);
		initWordWrapper.setBackgroundColor(0xFFFFFFFF);
		initWordWrapper.setOrientation(LinearLayout.HORIZONTAL);

		/*int dps = 48; //Value, in this case height, described in density pixels
		//Calculate the density pixels height in normal pixels.
		final float scale = getBaseContext().getResources().getDisplayMetrics().density;
		int pixels = (int) (dps * scale + 0.5f);*/
		
		//The two EditTexts must have same width, therefore they have both the weight of 1 (1.0f)
		/*LayoutParams weightParams = new LinearLayout.LayoutParams(0, pixels, 1.0f);*/
		initword1.setLayoutParams(weightParams);
		initword2.setLayoutParams(weightParams);
		
		
		//add the EditTexts to the container
		initWordWrapper.addView(initword1);
		initWordWrapper.addView(initword2);
		initword1.setHint("word");
		initword2.setHint("translation");
		
		//The newly added wordWrapper must always be placed below the already placed wordWrapper
		//In this case, there are no wordwrappers.
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, pixels);
		layoutParams.addRule(RelativeLayout.BELOW, -counter+1);

		initWordWrapper.setLayoutParams(layoutParams);
		
		//Setting up the id's. 
		/*The idea is to make it possible to get the Strings from id's using a 
		 * for loop and then printing them to this sharedPreference file. 
		 * 
		 * String from id1 = String from id2
		 * String from id3 = String from id4
		 * etc.*/
		
		//This part could become problematic. They both have the same id. 
		//Will go with it for now.
		
		initword1.setId(counter);
		initWordWrapper.setId(-counter);
		counter++;
		initword2.setId(counter);
				
		//Scroll down to bottom after added the new wordWrapper.
		((RelativeLayout) relativeLayout).addView(initWordWrapper);
		
		
		//Scroll down and animate
		//Scroll down to bottom after added the new wordWrapper.
		containerScrollView.post(new Runnable() {            
			@Override
			public void run() {
				//containerScrollView.fullScroll(View.FOCUS_DOWN);              
			}
		});
		
		//animate in the wordWrapper
		Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_right_in);
		animation.setStartOffset(0);
		initWordWrapper.startAnimation(animation);
		
		//Make the editTexts single line
		initword1.setSingleLine();
		initword2.setSingleLine();
		
		//nameField.requestFocus();
		initword1.requestFocus();
		initword1.setNextFocusDownId(initword2.getId());
		
		//User clicked addWord button
		addWordButton.setOnClickListener(new OnClickListener() { //ButtonClick, add new wordset

			@Override
			public void onClick(View v) {
				//The two words paired to each other:
				EditText word1 = new EditText(addFile.this); 
				EditText word2 = new EditText(addFile.this);
				
				//The container of the two EditTexts
				LinearLayout wordWrapper = new LinearLayout(addFile.this);
				wordWrapper.setBackgroundColor(0xFFFFFFFF);
				wordWrapper.setOrientation(LinearLayout.HORIZONTAL);

				/*int dps = 48; //Value, in this case height, described in density pixels
				//Calculate the density pixels height in normal pixels.
				final float scale = getBaseContext().getResources().getDisplayMetrics().density;
				int pixels = (int) (dps * scale + 0.5f);*/
				
				//The two EditTexts must have same width, therefore they have both the weight of 1 (1.0f)
				/*LayoutParams weightParams = new LinearLayout.LayoutParams(0, pixels, 1.0f);*/
				word1.setLayoutParams(weightParams);
				word2.setLayoutParams(weightParams);
				
				/*//Set next key on keyboard
				word1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				word2.setImeOptions(EditorInfo.IME_ACTION_NEXT);*/
				
				//add the EditTexts to the container
				wordWrapper.addView(word1);
				wordWrapper.addView(word2);
				
				
				//The newly added wordWrapper must always be placed below the already placed wordWrapper
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, pixels);
				layoutParams.addRule(RelativeLayout.BELOW, -counter+1);
				
				
				wordWrapper.setLayoutParams(layoutParams);
				
				//Setting up the id's. 
				/*The idea is to make it possible to get the Strings from id's using a 
				 * for loop and then printing them to this sharedPreference file. 
				 * 
				 * String from id1 = String from id2
				 * String from id3 = String from id4
				 * etc.*/
				
				//This part could become problematic. They both have the same id. 
				//Will go with it for now.
				
				counter++;
				word1.setId(counter);
				wordWrapper.setId(-counter);
				counter++;
				word2.setId(counter);
				
			
				//Scroll down to bottom after added the new wordWrapper.
				((RelativeLayout) relativeLayout).addView(wordWrapper);
				containerScrollView.post(new Runnable() {            
					@Override
					public void run() {
						//containerScrollView.fullScroll(View.FOCUS_DOWN);              
					}
				});
				
				//animate in the wordWrapper
				Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_right_in);
				animation.setStartOffset(0);
				wordWrapper.startAnimation(animation);
				
				word1.setSingleLine();
				word2.setSingleLine();
				
				word1.setFocusableInTouchMode(true);
				word1.requestFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(word1, InputMethodManager.SHOW_IMPLICIT);
				word1.setNextFocusDownId(word2.getId());
				/*word2 = (EditText)word1.focusSearch(View.FOCUS_RIGHT);
				word2.requestFocus();*/
			}	
		});			
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.add_file, menu);
		return true;	
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*int id = item.getItemId();
		if (id == R.id.addFileSaveButton){
			Context context = getApplicationContext();
			CharSequence text = "Sparar...";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}*/
		switch (item.getItemId()) {
		case R.id.addFileSaveButton:
			saveOperation(activityRaw);
			return true;
		case R.id.addFileCancelButton:
			Intent intent = new Intent(this, StartPoint.class);
			startActivity(intent);
			finish();
			return true;
		default: return super.onOptionsItemSelected(item);
		}
	}
	public void saveOperation(Activity activity) {
		//first check. Is there any any name?
		if (nameField.getText().toString().equals("")){
			//nameField is empty.
			Toast saveErr = Toast.makeText(getApplicationContext(), getResources().getString(R.string.name_the_exercise).toString(), Toast.LENGTH_LONG);
			saveErr.show();
		}
		else {
			//Get the amount of files.
			SharedPreferences filePrefsMisc = getSharedPreferences(PREF_MISC, 0);
			SharedPreferences.Editor filesEditorMisc = filePrefsMisc.edit();
			int filesNumber = filePrefsMisc.getInt("numberOfFiles", 0);
			//Get the amount of files again to see if it was the first the saveOperation() was executed. 
			//Needed to determinate if the tutorial is still necessary. 
			int oldFilesNumber = filesNumber;
			filesNumber++;


			//Writing to StoreSettings - write the new total amount of files
			filesEditorMisc.putInt("numberOfFiles", filesNumber);
			filesEditorMisc.commit();
			String thisFileReferense = "" + filesNumber;


			//Get PREF_FILES
			SharedPreferences filePrefsFiles = getSharedPreferences(PREF_FILES, 0);
			SharedPreferences.Editor filesEditorFiles = filePrefsFiles.edit();


			//Save the stuff witten into this app
			String thisPrefName = nameField.getText().toString();
			thisFilePrefs = getSharedPreferences(thisPrefName, 0);
			
			//Here goes the stuff from the edittexts.'
			//This is just some development test
			//thisFilePrefs.edit().putString("Hello", "Hej").commit();
			if(counter > 1){ //To avoid null
				Toast saveErr = Toast.makeText(getApplicationContext(), "For-loop kšrs", Toast.LENGTH_LONG);
				saveErr.show();
				for(int i = 2; i < counter; i = i + 2){
					
					//Currently gives a bug because the first id points to a textview. This gets fixed when the first id gets EditTexts
					EditText word1 = (EditText) activity.findViewById(i); //Does'nt work becouse the linearlayout has the same id
					EditText word2 = (EditText) activity.findViewById(i + 1);
					String word1Text = word1.getText().toString();
					String word2Text = word2.getText().toString();
					
					
					thisFilePrefs.edit().putString(word1Text, word2Text).commit();
				}
			}
			
			
			Toast confirmSaveToast = Toast.makeText(getApplicationContext(), getString(R.string.saving___), Toast.LENGTH_LONG);
			confirmSaveToast.show();
			if (oldFilesNumber == 0){
				/* If the the oldFileNumber is 0, then it means that the tutorial was
				 * active. We now need to restart StartPoint.java activity to get rid of the
				 * tutorial. I do it using an intent and then check if an 
				 * intent was received in StartPoint.java.
				 * 
				 *	I'm sure there's a better way to do it. Please make pull request if you have any ideas. */
				
				
				//For development. Deleted on release.
				Toast.makeText(getApplicationContext(), "Skickar intentmeddelande", Toast.LENGTH_LONG).show();
				
				//Making and starting the intent. 
				Intent intent = new Intent(this, StartPoint.class);
				intent.putExtra(REBOOT_MESSAGE, true);
				startActivity(intent);

				}
				//Just save it.
				//ThisFileReference is equal to the filesNumber+1
				//ThisFileReference references to the name of is training in sharedPreferences
				filesEditorFiles.putString(thisFileReferense, nameField.getText().toString());
				filesEditorFiles.commit();
				finish();
			}
		}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, StartPoint.class);
		startActivity(intent);
	}
	
	}
	/*	public boolean needTutorialCheck() {
		//check if we got some words
				final SharedPreferences filesPrefs = getSharedPreferences(PREF_MISC, 0);
				int trueCheck = filesPrefs.getInt("numberOfFiles", 0);
				if (trueCheck == 0){
					return true; 
				} else {
					return false;
				}		
			}*/
