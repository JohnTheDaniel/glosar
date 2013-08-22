package com.johndaniel.glosar;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
		RelativeLayout relativeContainer = (RelativeLayout) v.findViewById(R.id.translate1_container);
		
		int bgColor = colorChooser(getArguments().getInt("COLOR"));
		relativeContainer.setBackgroundColor(bgColor);
		
		TextView txtView = (TextView) v.findViewById(R.id.text1);
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
				return res.getColor(R.color.blue);
			case 1:
				return res.getColor(R.color.red);
			case 2: 
				return res.getColor(R.color.purple);
			case 3:
				return res.getColor(R.color.yellow);
			case 4:
				return res.getColor(R.color.green);
			default: 
				return res.getColor(R.color.blue);
			
		}
	}
	
	
}
