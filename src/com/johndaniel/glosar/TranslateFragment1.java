package com.johndaniel.glosar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TranslateFragment1 extends Fragment {
	private  String text;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		text = getArguments().getString(TranslateHolder.FRAGMENT_WORD);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_translate1,	container, false);
		
		TextView txtView = (TextView) v.findViewById(R.id.text1);
		if (text != null){
			txtView.setText(text);
		}
		return v;
	}
	
	
}
