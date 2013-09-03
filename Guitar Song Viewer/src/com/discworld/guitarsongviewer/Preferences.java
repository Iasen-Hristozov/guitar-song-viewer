package com.discworld.guitarsongviewer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity
{
	public static final String PREF_SHOW_CHORDS = "PREF_SHOW_CHORDS";
	public static final String PREF_TEXT_SIZE = "PREF_TEXT_SIZE";
	
	SharedPreferences prefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
	}
}
