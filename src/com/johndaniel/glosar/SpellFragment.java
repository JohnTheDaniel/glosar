package com.johndaniel.glosar;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class SpellFragment extends SherlockFragment{
	SpelledRightListener mSpellListener;
	View thisView;
	String translation;
	String word;
	EditText translationView;
	public interface SpelledRightListener {
		public void spelledRight();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		thisView = inflater.inflate(R.layout.fragment_spell, container, false);
		
		//[0] = word
		//[1] = translation
		setHasOptionsMenu(true);
		final String wordAndTrans = getArguments().getString(SpellActivity.TRANSLATION_AND_WORD_KEY);
		final int editTextId = getArguments().getInt(SpellActivity.EDITTEXT_ID_KEY);
		final int colorChooser = getArguments().getInt(SpellActivity.COLOR_CHOOSER_KEY);
		final boolean reverseTraining = getArguments().getBoolean(OverviewFragment.REVERSE_TRANSLATION);
		
		RelativeLayout layoutContainer = (RelativeLayout) thisView.findViewById(R.id.fragment_spell_container);
		layoutContainer.setBackgroundColor(ColorChooser.colorChooser(colorChooser, getActivity()));
		
		TextView wordView = (TextView) thisView.findViewById(R.id.fragment_spell_word);
		translationView = (EditText) thisView.findViewById(R.id.fragment_spell_translation);
		translationView.setId(editTextId);


		final InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
		translationView.requestFocus();
		translationView.setNextFocusDownId(translationView.getId() + 1);
		imm.showSoftInput(translationView, 0);
		
		String[] textDataArray = wordAndTrans.split("="); 
		//0 is word, 1 is translation
		if(textDataArray[0] != null){
			if(!reverseTraining){
				word = textDataArray[0];
			} else {
				translation = textDataArray[0];
			}
		} else {
			word = "";
		}
		
		if(textDataArray[1] != null){
			if(!reverseTraining) {
				translation = textDataArray[1];
			} else {
				word = textDataArray[1];	
			}
		} else {
			translation = "";
		}
		
		wordView.setText(word/* + "(" + translation + ")"*/);
		
		translationView.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				String currentText = translationView.getText().toString();
				if(currentText.toLowerCase().replace(" ", "").equals(translation.toLowerCase().replace(" ", ""))){
					translationView.clearFocus();
					mSpellListener.spelledRight();
					
					backgroundResponse(true, thisView);
				}
			}
		});
		
		return thisView;
	}
	private void backgroundResponse(boolean spelledRight, View layout){
		RelativeLayout container = (RelativeLayout) layout.findViewById(R.id.fragment_spell_container);
		if(spelledRight){
			container.setBackgroundColor(getResources().getColor(R.color.green));
		} else {
			container.setBackgroundColor(getResources().getColor(R.color.red_dark));
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mSpellListener = (SpelledRightListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SpelledRightListener");
        }
    }
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.spell, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.spell_fragment_hint:
			translationView.setText("");
			translationView.setHint(translation);
			translationView.setHintTextColor(0x9FFFFFFF);
			return true;
		
		default: return super.onOptionsItemSelected(item);
		}
	}
	
}
