package com.johndaniel.glosar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class TranslateActivity extends FragmentActivity {
	/*
	 *  For testing. The NUM_PAGES should have the value of 
	 * the amount of translations.
	 * NUM_PAGES decides how pages the ViewPager should hold.
	 */
	public static final String TRANSLATION = "com.johndaniel.glosar.TRASNLATION";
	
	private int NUM_PAGES;
	
	private String[] translations;
	
	private ViewPager pager;
	
	private PagerAdapter pagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translate);
		
		//Pair id and setup
		pager = (ViewPager) findViewById(R.id.translate_pager);
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		
		Intent intent = getIntent();
		NUM_PAGES = intent.getExtras().getInt(Train.NUM_TRANS);
		translations = intent.getExtras().getStringArray(Train.TRANSLATIONS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.translate, menu);
		return true;
	}
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	TranslateHolder tHolder = new TranslateHolder();
        	Bundle args = new Bundle();
        	args.putString(TRANSLATION, translations[position]);
        	tHolder.setArguments(args);
            return tHolder;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
