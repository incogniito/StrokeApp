package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public static class settingsFrag extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
            ListPreference lists = (ListPreference) findPreference("font_preference");

            if(lists.getValue() == null) {
                    lists.setValue("16");
                System.out.println("WERE HERE");

            }

                lists.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

                public boolean onPreferenceChange(Preference preference, Object newValue) {
                   // System.out.println("Hello there!");
                    String font = (String) newValue;
                    fontsize = Integer.parseInt(font);

                    //System.out.println(fontsize);
                    CustomTextView.setTextSizes(fontsize);

                    return true;
                }
            });


            final CheckBoxPreference bold;
            bold = (CheckBoxPreference) findPreference("font_bold");
           //bold.setChecked(false);
            bold.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(!bold.isChecked())
                    {
                        CustomTextView.makeBold();
                    }
                    else
                    {
                        CustomTextView.removeBold();

                    }
                    return true;

                }
            });

            final CheckBoxPreference scale;
            scale = (CheckBoxPreference) findPreference("image_scale");
            //bold.setChecked(false);
            scale.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(!scale.isChecked())
                    {
                        int newh = 2;
                        int neww = 2;
                        CustomImageView.setSizes(newh, neww);
                    }
                    else
                    {
                        int newh = 2;
                        int neww = 2;
                        CustomImageView.setSizes(newh, neww);
                    }

                    return true;

                }
            });
        }

    }
}
