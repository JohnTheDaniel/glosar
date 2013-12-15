package com.johndaniel.glosar;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class SpellFragment extends Fragment{
	SpelledRightListener mSpellListener;
	public interface SpelledRightListener {
		public void spelledRight();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View thisView = inflater.inflate(R.layout.fragment_spell, container, false);
		
		//[0] = word
		//[1] = translation
		final String wordAndTrans = getArguments().getString(SpellActivity.TRANSLATION_AND_WORD_KEY);
		final int editTextId = getArguments().getInt(SpellActivity.EDITTEXT_ID_KEY);
		
		TextView wordView = (TextView) thisView.findViewById(R.id.fragment_spell_word);
		final EditText translationView = (EditText) thisView.findViewById(R.id.fragment_spell_translation);
		translationView.setId(editTextId);
		

		final InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
		translationView.requestFocus();
		translationView.setNextFocusDownId(translationView.getId() + 1);
		imm.showSoftInput(translationView, 0);
		
		String[] textDataArray = wordAndTrans.split("="); 
		//0 is word, 1 is translation
		String word = textDataArray[0];
		final String translation = textDataArray[1];
		
		wordView.setText(word + "(" + translation + ")");
		
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
				if(currentText.equals(translation)){
					translationView.clearFocus();
					mSpellListener.spelledRight();
				}
			}
		});
		
		return thisView;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mSpellListener = (SpelledRightListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

}
