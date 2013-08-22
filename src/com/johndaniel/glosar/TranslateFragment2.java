package com.johndaniel.glosar;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TranslateFragment2 extends Fragment {

	private  String text;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		text = getArguments().getString(TranslateHolder.FRAGMENT_TRANSLATION);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_translate2,	container, false);
		RelativeLayout relativeContainer = (RelativeLayout) v.findViewById(R.id.translate2_container);
		
		int bgColor = colorChooser(getArguments().getInt("COLOR"));
		relativeContainer.setBackgroundColor(bgColor);
		
		TextView txtView = (TextView) v.findViewById(R.id.text2);
		if (text != null){
			txtView.setText(text);
		}
		return v;
	}
	
	private int colorChooser(int colorChooser){
		/* 
		 * 1 = blue
		 * 2 = red
		 * 3 = purple
		 * 4 = yellow
		 * 5 = green
		 */
		Resources res = getResources();
		switch (colorChooser){
			case 0: 
				return res.getColor(R.color.blue_dark);
			case 1:
				return res.getColor(R.color.red_dark);
			case 2: 
				return res.getColor(R.color.purple_dark);
			case 3:
				return res.getColor(R.color.yellow_dark);
			case 4:
				return res.getColor(R.color.green_dark);
			default: 
				return res.getColor(R.color.blue_dark);
			
		}
	}
	
}
