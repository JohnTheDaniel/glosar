package com.erlaa.glosor;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class addFile extends Activity {
	int counter;
	int text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new);
		
		//Create custom actionbar
		final ActionBar ab = getActionBar();
	    ab.setDisplayShowHomeEnabled(false);
	    ab.setDisplayShowTitleEnabled(false);     
	    final LayoutInflater inflater = (LayoutInflater)getSystemService("layout_inflater");
	    View view = inflater.inflate(R.layout.action_bar_edit_mode_add_file,null); 
	    
	    //Pairing id's
	    final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
	    final ScrollView containerScrollView = (ScrollView) findViewById(R.id.container);
	    final LinearLayout addWordButton = (LinearLayout) findViewById(R.id.bottomButton);
	    final EditText nameField = (EditText) findViewById(R.id.addNewEditText);
	    
	    ab.setCustomView(view);
	    ab.setDisplayShowCustomEnabled(true);
	    View custom; 
	    LayoutInflater inflated = (LayoutInflater)   getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	    custom = inflater.inflate(R.layout.action_bar_edit_mode_add_file, null);
	    final LinearLayout saveButton = (LinearLayout) custom.findViewById(R.id.addNewSaveButton);
	    final LinearLayout cancelButton = (LinearLayout) custom.findViewById(R.id.addNewCancelButton);
	    
	    
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
					TextView word = new TextView(addFile.this);
					text++;
					word.setText("Test" + text);
					RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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
					//Test
			}	
		});	
	}
}
