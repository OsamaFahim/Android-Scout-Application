package com.databits.androidscouting.util;

import android.content.Context;

import androidx.preference.Preference;

import com.preference.PowerPreference;

public class PreferenceManager {
    private static PreferenceManager instance;

    private final com.preference.Preference configPreference;
    private final com.preference.Preference debugPreference;
    private final com.preference.Preference listPreference;
    private final com.preference.Preference matchPreference;
    private final com.preference.Preference pitDataPreference;
    private PreferenceManager() {
        // Initialize each preference only once
        //PowerPreference.init(context);
        configPreference = PowerPreference.getFileByName("Config");
        debugPreference = PowerPreference.getFileByName("Debug");
        listPreference = PowerPreference.getFileByName("List");
        matchPreference = PowerPreference.getFileByName("Match");
        pitDataPreference = PowerPreference.getFileByName("PitData");
    }

    public static synchronized PreferenceManager getInstance() {
        if (instance == null) {
            instance = new PreferenceManager();
        }
        return instance;
    }

    public com.preference.Preference getConfigPreference() {
        return configPreference;
    }

    public com.preference.Preference getDebugPreference() {
        return debugPreference;
    }

    public com.preference.Preference getListPreference() {
        return listPreference;
    }

    public com.preference.Preference getMatchPreference() {
        return matchPreference;
    }
    public com.preference.Preference getPitDataPreference() {return pitDataPreference;}
}

