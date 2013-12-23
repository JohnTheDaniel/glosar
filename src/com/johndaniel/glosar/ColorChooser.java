package com.johndaniel.glosar;

import android.content.Context;
import android.content.res.Resources;

public class ColorChooser {
	public static int colorChooser(int colorChooser, Context context){
		/* 
		 * 1 = blue
		 * 2 = red
		 * 3 = purple
		 * 4 = yellow
		 * 5 = green
		 */
		Resources res = context.getResources();
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
	public static int darkColorChooser(int colorChooser, Context context){
		/* 
		 * 1 = blue
		 * 2 = red
		 * 3 = purple
		 * 4 = yellow
		 * 5 = green
		 */
		Resources res = context.getResources();
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
