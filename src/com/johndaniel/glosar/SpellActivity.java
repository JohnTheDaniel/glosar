package com.johndaniel.glosar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.Window;
import com.johndaniel.glosar.SpellFragment.SpelledRightListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

public class SpellActivity extends SherlockFragmentActivity implements SpelledRightListener{
	int NUM_TRANS;
	boolean reverseTraining;
	String[] translations;
	public static String TRANSLATION_AND_WORD_KEY = "com.johndaniel.glosar.translation_and_word_key";
	public static String EDITTEXT_ID_KEY = "com.johndaniel.glosar.edittext_id_key";
	int currentFragmentShowing = 0;
	SpellFragment[] fragments = new SpellFragment[0];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_spell);
		Intent intent = getIntent();
		translations = intent.getStringArrayExtra(OverviewFragment.TRANSLATIONS);
		reverseTraining = intent.getBooleanExtra(OverviewFragment.REVERSE_TRANSLATION, false);
		NUM_TRANS = intent.getIntExtra(OverviewFragment.NUM_TRANS, 1);
		
		for(int i = 0; i < translations.length; i++){
			fragments = expandFragmentArray(fragments);
			fragments[i] = new SpellFragment();
			Bundle args = new Bundle();
			String translationAndWord;
			translationAndWord = translations[i];
			args.putString(TRANSLATION_AND_WORD_KEY, translationAndWord);
			args.putInt(EDITTEXT_ID_KEY, i);
			fragments[i].setArguments(args);
		}
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
			.add(R.id.spell_activity_fragment_container, fragments[0])
			.commit();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.spell, menu);
		return true;
	}

	@Override
	public void spelledRight() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Spelled right", Toast.LENGTH_SHORT).show();
		switchToNextFragment();
	}

	private void switchToNextFragment() {
		// TODO Auto-generated method stub
		if(currentFragmentShowing <= translations.length){
			currentFragmentShowing++;
		}
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
			.replace(R.id.spell_activity_fragment_container, fragments[currentFragmentShowing])
			.commit();
	}
	public SpellFragment[] expandFragmentArray(SpellFragment[] oldArray) {
		SpellFragment[] newArray = new SpellFragment[oldArray.length + 1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		return newArray;
	}

}
