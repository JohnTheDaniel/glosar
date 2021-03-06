package com.johndaniel.glosar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class EditActivity extends SherlockActivity {
	EditText nameField;
	String trainingId;
	String trainingName;
	int counter;
	LayoutParams editTextWeightParams;
	int wrapperCounter;
	int pixels;
	RelativeLayout relativeLayout;
	public static final String PREF_MISC = "StoreSettings";
	public static final String PREF_FILES = "FileStorage";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new);
		
		//Pairing constant views id's to variables.
		nameField = (EditText) findViewById(R.id.addNewEditText);
		nameField.clearFocus();
		nameField.setSelected(false);
		relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
		final LinearLayout addWordButton = (LinearLayout) findViewById(R.id.bottomButton);
		
		//Setting up the name in the nameField
		trainingId = getIntent().getExtras().getString("TRAINING");
		SharedPreferences prefFiles = getSharedPreferences(StartPoint.PREF_FILES, 0);
		trainingName = prefFiles.getString(trainingId, "");
		nameField.setText(trainingName);
		
		//Calculate width
		int dps = 48; //Value, in this case height, described in density pixels
		//Calculate the density pixels height in normal pixels.
		final float scale = getBaseContext().getResources().getDisplayMetrics().density;
		pixels = (int) (dps * scale + 0.5f);
		//The layoutparams for the editTexts. This will give the EditTexts an height of pixels (calculated dps), the weight 1.
		//The weight will cause the EditTexts to share the amount of space inside the container. 
		editTextWeightParams = new LinearLayout.LayoutParams(0, pixels, 1.0f);
		
		counter = 2;
		wrapperCounter = 1;
		
		SharedPreferences prefWords = getSharedPreferences(trainingName, 0);
		String rawWords = prefWords.getAll().toString().replace("{", "").replace("}", "");
		//Toast.makeText(getApplicationContext(), rawWords, Toast.LENGTH_LONG).show();
		final String[] wordsAndTranslations = rawWords.split(", ");
		
		//Add the previous words
		for (int i = 0; i < wordsAndTranslations.length; i++){
			String[] group = wordsAndTranslations[i].split("=");
			String word;
			String translation;
			if(group[0] != null){
				word = group[0];
			} else {
				word = "";
			}
			
			if(group[1] != null){
				translation = group[1];
			} else {
				translation = "";
			}
			addWord(i, word, translation, true);
		}
		
		//Check for addWordButton press
		addWordButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*int idPlus;
				if (wordsAndTranslations.length > 0){
					idPlus = 1;
				} else {
					idPlus = 0;
				}*/
				addWord(1, "", "", false);
			}
		});
		
		/*for (int i = 0; i < wordsAndTranslations.length; i++){
			//Place a container per word and translation and put the words into it. 
			String[] group = wordsAndTranslations[i].split("=");
			String word = group[0];
			String translation = group[1];
			
			LinearLayout wordWrapper = new LinearLayout(EditActivity.this);
			wordWrapper.setBackgroundColor(0xFFFFFFFF);
			wordWrapper.setOrientation(LinearLayout.HORIZONTAL);
			
			EditText editTextWord = new EditText(EditActivity.this);
			EditText editTextTranslation = new EditText(EditActivity.this);
			editTextWord.setLayoutParams(editTextWeightParams);
			editTextTranslation.setLayoutParams(editTextWeightParams);
			editTextWord.setText(word);
			editTextTranslation.setText(translation);
			
			wordWrapper.addView(editTextWord);
			wordWrapper.addView(editTextTranslation);
			RelativeLayout.LayoutParams wrapperParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, pixels);
			wrapperParams.addRule(RelativeLayout.BELOW, wrapperCounter);
			
			wordWrapper.setLayoutParams(wrapperParams);
			if(i != 0){
				wrapperCounter++;
				counter++;
			}
			editTextWord.setId(counter);
			wordWrapper.setId(wrapperCounter);
			counter++;
			editTextTranslation.setId(counter);
			
			//Add the wordwrapper. 
			((RelativeLayout) relativeLayout).addView(wordWrapper);
			
		}*/
		
	}
	private void addWord(int loopCount, String word, String translation, boolean fromInitLoop){
		LinearLayout wordWrapper = new LinearLayout(EditActivity.this);
		wordWrapper.setBackgroundColor(0xFFFFFFFF);
		wordWrapper.setOrientation(LinearLayout.HORIZONTAL);
		
		EditText editTextWord = new EditText(EditActivity.this);
		EditText editTextTranslation = new EditText(EditActivity.this);
		editTextWord.setLayoutParams(editTextWeightParams);
		editTextTranslation.setLayoutParams(editTextWeightParams);
		editTextWord.setText(word);
		editTextTranslation.setText(translation);
		
		wordWrapper.addView(editTextWord);
		wordWrapper.addView(editTextTranslation);
		RelativeLayout.LayoutParams wrapperParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, pixels);
		wrapperParams.addRule(RelativeLayout.BELOW, wrapperCounter);
		
		wordWrapper.setLayoutParams(wrapperParams);
		if(loopCount != 0){
			wrapperCounter++;
			counter++;
		}
		editTextWord.setId(counter);
		wordWrapper.setId(wrapperCounter);
		counter++;
		editTextTranslation.setId(counter);
		
		//Add the wordwrapper. 
		((RelativeLayout) relativeLayout).addView(wordWrapper);
		
		//Animate in the wordWraper
		Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_right_in);
		animation.setStartOffset(0);
		wordWrapper.startAnimation(animation);
		
		//Set singleLine and focus order
		editTextWord.setSingleLine();
		editTextTranslation.setSingleLine();
		if(!fromInitLoop){
			editTextWord.setFocusableInTouchMode(true);
			editTextWord.requestFocus();
			//Request keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(editTextWord, InputMethodManager.SHOW_IMPLICIT);
		} else {
			editTextTranslation.setFocusableInTouchMode(true);
			editTextTranslation.requestFocus();
			//Request keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(editTextTranslation, InputMethodManager.SHOW_IMPLICIT);
		}
		//Set focus order. 
		editTextWord.setNextFocusDownId(editTextTranslation.getId());
		editTextTranslation.setNextFocusDownId(editTextTranslation.getId() + 1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.add_file, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.addFileSaveButton: 
			boolean allClear = doCheck();
			if(allClear){
				doReplacement();
				Intent intent = new Intent(this, StartPoint.class);
				startActivity(intent);
				finish();	
			}
			return true;	
		default: 
			return super.onOptionsItemSelected(item); 
		}
	}
	
	private boolean doCheck() {
		// TODO Auto-generated method stub
		GlosarFileHandler glosFH = new GlosarFileHandler(getApplicationContext(), this, this);
		boolean allClear = glosFH.wordCheck(nameField.getText().toString(), counter);
		return allClear;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(this, StartPoint.class);
		startActivity(intent);
	}
	private void doReplacement() {
		// TODO Auto-generated method stub
		GlosarFileHandler glosFH = new GlosarFileHandler(getApplicationContext(), this, this);
		//boolean allClear = glosFH.wordCheck(nameField.getText().toString(), counter);
		//if (allClear){
			glosFH.deleteOperation(trainingId, PREF_FILES);
			glosFH.saveOperation(PREF_FILES, PREF_MISC, trainingName, counter);
		//}
	}
	

}
