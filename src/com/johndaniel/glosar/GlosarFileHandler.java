package com.johndaniel.glosar;

import java.io.File;

import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;


public class GlosarFileHandler {
	Context context;
	Context appContext;
	Activity activity;
	
	public GlosarFileHandler(Context inContext, Context inAppContext, Activity inActivity){
		this.context = inContext;
		this.activity = inActivity;
		this.appContext = inAppContext;
	}
	public void saveOperation(String PREF_FILES, String PREF_MISC, String trainingName, int counter) {
		//first check. Is there any any name?
			//Get the amount of files.
			SharedPreferences filePrefsMisc = activity.getSharedPreferences(PREF_MISC, 0);
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
			SharedPreferences filePrefsFiles = activity.getSharedPreferences(PREF_FILES, 0);
			SharedPreferences.Editor filesEditorFiles = filePrefsFiles.edit();
			filesEditorFiles.putString(thisFileReferense, trainingName).commit();

			//Save the stuff witten into this app
			String thisPrefName = trainingName;
			SharedPreferences thisFilePrefs = activity.getSharedPreferences(thisPrefName, 0);
			
			//Here goes the stuff from the edittexts.'
			if(counter > 1){ //To avoid null
				Toast saveErr = Toast.makeText(appContext, "For-loop kšrs", Toast.LENGTH_LONG);
				saveErr.show();
				for(int i = 2; i < counter; i = i + 2){
						EditText word1 = (EditText) activity.findViewById(i);
						EditText word2 = (EditText) activity.findViewById(i + 1);
						String word1Text = word1.getText().toString();
						String word2Text = word2.getText().toString();
						
						if(!(word1Text.equals("") && word2Text.equals(""))){
							thisFilePrefs.edit().putString(word1Text, word2Text).commit();
						}
				}
			}
			
			
			Toast confirmSaveToast = Toast.makeText(appContext, context.getString(R.string.saving___), Toast.LENGTH_LONG);
			confirmSaveToast.show();
			if (oldFilesNumber == 0){
				/* If the the oldFileNumber is 0, then it means that the tutorial was
				 * active. We now need to restart StartPoint.java activity to get rid of the
				 * tutorial. I do it using an intent and then check if an 
				 * intent was received in StartPoint.java.
				 * 
				 *	I'm sure there's a better way to do it. Please make pull request if you have any ideas. */
				
				
				//For development. Deleted on release.
				Toast.makeText(appContext, "Skickar intentmeddelande", Toast.LENGTH_LONG).show();
			}
				/*
				//Making and starting the intent. 
				Intent intent = new Intent(this, StartPoint.class);
				intent.putExtra(REBOOT_MESSAGE, true);
				startActivity(intent);

				}
				//Just save it.
				//ThisFileReference is equal to the filesNumber+1
				//ThisFileReference references to the name of is training in sharedPreferences
				filesEditorFiles.putString(thisFileReferense, trainingName.getText().toString());
				filesEditorFiles.commit();
				finish();
				
				Intent intent = new Intent(this, StartPoint.class);
				startActivity(intent);
				*/
				
	}
	
	public void replaceOperation(String trainingId, String PREF_FILES, String PREF_MISC, String trainingName, int counter){
		/* 
		 * First delete the training. Then rewrite
		 */
		deleteOperation(trainingId, PREF_FILES);
		saveOperation(PREF_FILES, PREF_MISC, trainingName, counter);
	}
	public void deleteOperation(String trainingId, String PREF_FILES){	
		//This code works
		//Remove contents
		SharedPreferences filePrefs = activity.getSharedPreferences(PREF_FILES, 0);
		
		String thisPrefsName = filePrefs.getString(trainingId, "ghshf3gtsh78isfdhsiv").toString();
		SharedPreferences thisPrefs = activity.getSharedPreferences(thisPrefsName, 0);
		SharedPreferences.Editor thisPrefsEditor = thisPrefs.edit();
		thisPrefsEditor.clear().commit();
		
		
		//Remove file
		File file1 = new File(activity.getApplicationContext().getFilesDir().getParentFile().getPath() + "/shared_prefs/" + thisPrefsName + ".xml");
		file1.delete();
		
		File file2 = new File(activity.getApplicationContext().getFilesDir().getParentFile().getPath() + "/shared_prefs/" + thisPrefsName + ".bak");
		if(file2.exists()){
			file2.delete();
		}
		
		//Remove reference
		SharedPreferences.Editor filePrefsEditor = filePrefs.edit();
		filePrefsEditor.putString(trainingId, "").commit();
		
		/*boolean isTablet = appContext.getResources().getBoolean(R.bool.isTablet);
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
		}*/
	}
	public boolean wordCheck(String nameField, int counter){
		boolean allClear = true;
		/*
		 * There are two things we need to check before saving the training
		 * a) Are there any words that are equal to each other?
		 *  - The word (left side on the screen) are used as keys in SharedPreferences
		 *    If they are equal to each other, it will cause an overwrite.
		 * 
		 * b) Check if the exercise got a name (currently plased in saveOperation()) 
		 * 
		 * c) Check if there are any empty words/translations?
		 */
		
		//b)
		if (nameField.equals("")){
			//nameField is empty.
			Toast saveErr = Toast.makeText(appContext, context.getResources().getString(R.string.name_the_exercise).toString(), Toast.LENGTH_LONG);
			saveErr.show();
			return false;
		}
		
		//c)
		//Why counter-1? That is the amount of words. 
		//Why is i = 2? The first word has always the id 2.
		for (int i = 2; i <= counter-1; i = i+2){
			EditText word = (EditText) activity.findViewById(i);
			EditText translation = (EditText) activity.findViewById(i + 1);
			if (!(word.getText().toString().equals("") && translation.getText().toString().equals(""))){
				if ((word.getText().toString().equals("")) || (translation.getText().toString().equals(""))){
					Toast.makeText(appContext, context.getResources().getString(R.string.there_is_an_empty_word_or_translation_please_fix_it), Toast.LENGTH_LONG).show();
					return false;
				}
			}
		}
		
		//a)
		//Print in all the words into an array
		String[] words = new String[(counter-1)/2]; 
		int wordsIndexCounter = 0;
		//Why counter-1? That is the amount of words. 
		//Why is i = 2? The first word has always the id 2.
		for (int i = 2; i <= counter-1; i = i + 2){
			 EditText word = (EditText) activity.findViewById(i);		 
			 words[wordsIndexCounter] = word.getText().toString();
			 wordsIndexCounter++;
		}
		for (int i = 0; i < words.length; i++){
			String wordA = words[i];
			if(!(wordA.equals(""))){
				for (int a = 0; a < words.length; a++){
					if (a != i){
						String wordB = words[a];
						if (wordA.equals(words[a])){
							//We got a match!
							Toast.makeText(appContext, context.getResources().getString(R.string.two_words_are_equal_to_each_other), Toast.LENGTH_SHORT).show();
							return false;
						}
					}
				}
			}
		}
		return allClear;
	}
}
