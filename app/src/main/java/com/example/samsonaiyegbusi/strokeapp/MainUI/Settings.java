package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.example.samsonaiyegbusi.strokeapp.R;

public class Settings extends AppCompatActivity {


    static int fontsize;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.settings_main);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new settingsFrag()).commit();

    }

    public static class settingsFrag extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);

            ListPreference lists = (ListPreference) findPreference("font_preference");

            lists.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    System.out.println("Hello there!");
                    String font = (String) newValue;
                    fontsize=Integer.parseInt(font);

                    //System.out.println(fontsize);
                    CustomTextView.setTextSizes(fontsize);

                    return true;
                }
            });

        }
    }


}
