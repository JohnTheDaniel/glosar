package com.johndaniel.glosar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.johndaniel.glosar.SpellFragment.SpelledRightListener;

public class SpellActivity extends SherlockFragmentActivity implements SpelledRightListener{
	int NUM_TRANS;
	boolean reverseTraining;
	String[] translations;
	int colorChooser;
	public static String COLOR_CHOOSER_KEY = "com.johndaniel.glosar.color_chooser_key";
	public static String TRANSLATION_AND_WORD_KEY = "com.johndaniel.glosar.translation_and_word_key";
	public static String EDITTEXT_ID_KEY = "com.johndaniel.glosar.edittext_id_key";
	int currentFragmentShowing = 0;
	ActionBar actionBar;
	SpellFragment[] fragments = new SpellFragment[0];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.translate_activity_ab_bg));
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
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
			args.putInt(COLOR_CHOOSER_KEY, i % 5);
			args.putBoolean(OverviewFragment.REVERSE_TRANSLATION, reverseTraining);
			args.putInt(EDITTEXT_ID_KEY, i);
			fragments[i].setArguments(args);
		}
		FragmentManager fm = getSupportFragmentManager();
		if(savedInstanceState == null){
		fm.beginTransaction()
			.add(R.id.spell_activity_fragment_container, fragments[0])
			.commit();
		} else if (savedInstanceState != null){
			fm.beginTransaction()
			.replace(R.id.spell_activity_fragment_container, fragments[0])
			.commit();
		}
		actionBar.setTitle(getString(R.string.word_big_first) + " " + (currentFragmentShowing + 1) + " " + getString(R.string.of) + " " + translations.length);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.spell, menu);
		return true;
	}*/

	@Override
	public void spelledRight() {
		// TODO Auto-generated method stub
		switchToNextFragment();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			return true;
		
		default: return super.onOptionsItemSelected(item);
		}
	}

	private void switchToNextFragment() {
		// TODO Auto-generated method stub
		if(currentFragmentShowing + 1 < translations.length){			
			FragmentManager fm = getSupportFragmentManager();
			fm.beginTransaction()
				.setCustomAnimations(R.anim.fragment_spell_slide_in_right, R.anim.fragment_spell_slide_out_left)
				.replace(R.id.spell_activity_fragment_container, fragments[currentFragmentShowing + 1])
				.commit();
		actionBar.setTitle(getString(R.string.word_big_first) + " " + (currentFragmentShowing + 2) + " " + getString(R.string.of) + " " + translations.length);
		currentFragmentShowing++;
		} else {
			finish();
		}
	}
	public SpellFragment[] expandFragmentArray(SpellFragment[] oldArray) {
		SpellFragment[] newArray = new SpellFragment[oldArray.length + 1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		return newArray;
	}

}
