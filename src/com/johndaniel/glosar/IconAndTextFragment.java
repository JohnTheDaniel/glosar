package com.johndaniel.glosar;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class IconAndTextFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_icon_and_text_fragment);
		View thisView = inflater.inflate(R.layout.activity_icon_and_text_fragment, container, false);
		
		return thisView;
		
	}

}
