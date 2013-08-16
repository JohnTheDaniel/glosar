package com.johndaniel.glosar;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import android.annotation.SuppressLint;
import android.app.Activity;


public class TranslateActivity extends FragmentActivity {
	boolean showingBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translate);
		
		if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_activity_card_face, new TranslateFragment1())
                    .commit();
            
            getSupportFragmentManager().beginTransaction()
            	.add(R.id.main_activity_card_back, new TranslateFragment2())
            	.commit();
                    
            showingBack = false;
        }
		
		RelativeLayout container = (RelativeLayout) findViewById(R.id.main_activity_root);
		container.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Snurra
				flip();
				
			}
		});
	}

	private void flip(){
		RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.main_activity_root);
		RelativeLayout cardFace = (RelativeLayout) findViewById(R.id.main_activity_card_face);
		RelativeLayout cardBack = (RelativeLayout) findViewById(R.id.main_activity_card_back);

	    FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

	    if (cardFace.getVisibility() == View.GONE)
	    {
	        flipAnimation.reverse();
	    }
	    rootLayout.startAnimation(flipAnimation);

	}
	
}
