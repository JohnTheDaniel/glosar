package com.erlaa.glosor;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class addFile extends SherlockActivity {
	int counter;
	int text;
	EditText nameField;
	public static final String PREFS_FILE_NUMBER = "StoreSettings";
	SharedPreferences thisFilePrefs;
	public final static String REBOOT_MESSAGE = "com.erlaa.glosor.REBOOT_MESSAGE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new);
		
		//Getting number of trainings
		//Jag glömde varför jag gjorde det här....
		SharedPreferences filePrefs = getSharedPreferences("StoreSettings", 0);
		int filesNumber = filePrefs.getInt("numberOfFiles", 0); //Varför blir den här alltid 0?
		Context context = getApplicationContext();
		CharSequence toastText = filesNumber + " filer";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, toastText, duration);
		toast.show();
	    
	    //Pairing id's
	    final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
	    final ScrollView containerScrollView = (ScrollView) findViewById(R.id.container);
	    final LinearLayout addWordButton = (LinearLayout) findViewById(R.id.bottomButton);
	    nameField = (EditText) findViewById(R.id.addNewEditText);
	    
	    
	    
	    //add first glosa
	    TextView startWords = new TextView(this);
	    startWords.setText("Word number 1");
	    counter = 1;
	    startWords.setId(counter);
	    startWords.setLayoutParams(new LayoutParams(
	    		LayoutParams.WRAP_CONTENT,
	    		LayoutParams.WRAP_CONTENT));
	    relativeLayout.addView(startWords);
	    //Button Click, save

	    //ButtonClick, add new glosa
		addWordButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					EditText word = new EditText(addFile.this);
					text++;
					int dps = 48; 
					final float scale = getBaseContext().getResources().getDisplayMetrics().density;
					int pixels = (int) (dps * scale + 0.5f);
					RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, pixels);
					word.setLayoutParams(layoutParams);
					layoutParams.addRule(RelativeLayout.BELOW, counter);
					counter++;
					word.setId(counter);
					((RelativeLayout) relativeLayout).addView(word);
					containerScrollView.post(new Runnable() {            
					    @Override
					    public void run() {
					          	containerScrollView.fullScroll(View.FOCUS_DOWN);              
					    }
					});
					
					//animate in the object
					Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_right_in);
					animation.setStartOffset(0);
					word.startAnimation(animation);
					//Test
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
			saveOperation();
			return true;
		case R.id.addFileCancelButton:
			finish();
			return true;
		default: return super.onOptionsItemSelected(item);
		}
	}
	public void saveOperation() {
		//first check. Is there any any name?
		if (nameField.getText().toString().equals("")){
			//nameField is empty.
			Toast saveErr = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
			saveErr.show();
		}
		else {
			
			SharedPreferences filePrefs = getSharedPreferences("StoreSettings", 0);
			SharedPreferences.Editor filesEditor = filePrefs.edit();
			int filesNumber = filePrefs.getInt("numberOfFiles", 0);
			filesNumber++;
			//Writing to StoreSettings
			filesEditor .putInt("numberOfFiles", filesNumber);
			String thisFileReferense = "" + filesNumber;
			
			
			//Save the stuff witten into this app
			String thisPrefName = nameField.getText().toString();
			thisFilePrefs = getSharedPreferences(thisPrefName, 0);
			
			Toast confirmSaveToast = Toast.makeText(getApplicationContext(), "Sparar...", Toast.LENGTH_LONG);
			confirmSaveToast.show();
			if (needTutorialCheck() == true){
				Toast.makeText(getApplicationContext(), "Skickar intentmeddelande", Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(this, StartPoint.class);
				intent.putExtra(REBOOT_MESSAGE, true);
				startActivity(intent);
				filesEditor.putString(thisFileReferense, nameField.getText().toString());
				filesEditor.commit();
				
				} else {
					filesEditor.putString(thisFileReferense, nameField.getText().toString());
					filesEditor.commit();
			finish();
			}
		}
	}
	public boolean needTutorialCheck() {
		//check if first startup
				final SharedPreferences filesPrefs = getSharedPreferences("StoreSettings", 0);
				int trueCheck = filesPrefs.getInt("numberOfFiles", 0);
				if (trueCheck == 0){
					return true; 
				} else {
					return false;
				}		
			}
	}
