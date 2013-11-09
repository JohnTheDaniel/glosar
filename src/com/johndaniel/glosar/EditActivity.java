package com.johndaniel.glosar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class EditActivity extends Activity {
	EditText nameField;
	String training;
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
		relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
		final LinearLayout addWordButton = (LinearLayout) findViewById(R.id.bottomButton);
		
		//Setting up the name in the nameField
		training = getIntent().getExtras().getString("TRAINING");
		SharedPreferences prefFiles = getSharedPreferences(StartPoint.PREF_FILES, 0);
		String trainingName = prefFiles.getString(training, "");
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
			String word = group[0];
			String translation = group[1];
			addWord(i, word, translation);
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
				addWord(1, "", "");
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
	private void addWord(int loopCount, String word, String translation){
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

}
