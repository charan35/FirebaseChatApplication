package com.jayaraj.firebasechatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.text.TextUtils;
import android.widget.Toast;

public class Settings extends AppCompatPreferenceActivity {

    private static final String TAG = Settings.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_notifications_new_message_ringtone)));

            final SwitchPreference onOffNotifications = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.notifications_new_message));

            final SwitchPreference onOffVibration = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.key_vibrate));
            onOffVibration.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(onOffVibration.isChecked()){
                        Toast.makeText(getActivity(),"Vibration turned Off",Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        onOffVibration.setChecked(false);
                        SharedPreferences userDetails1 = preference.getContext().getSharedPreferences("Vibration", MODE_PRIVATE);
                        SharedPreferences.Editor edit = userDetails1.edit();
                        edit.clear();
                        edit.putBoolean("boolean1", false);
                        edit.commit();
                    }else {
                        Toast.makeText(getActivity(),"Vibration turned On",Toast.LENGTH_SHORT).show();

                        // Unchecked the switch programmatically
                        onOffVibration.setChecked(true);
                        SharedPreferences userDetails1 = preference.getContext().getSharedPreferences("Vibration", MODE_PRIVATE);
                        SharedPreferences.Editor edit = userDetails1.edit();
                        edit.clear();
                        edit.putBoolean("boolean1", true);
                        edit.commit();
                    }
                    return false;
                }
            });

            onOffNotifications.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(onOffNotifications.isChecked()){
                        Toast.makeText(getActivity(),"Notifications turned Off",Toast.LENGTH_SHORT).show();
                        // Checked the switch programmatically
                        onOffNotifications.setChecked(false);
                        SharedPreferences userDetails = preference.getContext().getSharedPreferences("Notification", MODE_PRIVATE);
                        SharedPreferences.Editor edit = userDetails.edit();
                        edit.clear();
                        edit.putBoolean("boolean", false);
                        edit.commit();
                    }else {
                        Toast.makeText(getActivity(),"Notifications turned On",Toast.LENGTH_SHORT).show();
                        // Unchecked the switch programmatically
                        onOffNotifications.setChecked(true);
                        SharedPreferences userDetails = preference.getContext().getSharedPreferences("Notification", MODE_PRIVATE);
                        SharedPreferences.Editor edit = userDetails.edit();
                        edit.clear();
                        edit.putBoolean("boolean", true);
                        edit.commit();
                    }
                    return false;
                }
            });
        }
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String  stringValue = newValue.toString();


            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                if (TextUtils.isEmpty(stringValue)) {
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));
                    SharedPreferences userDetails = preference.getContext().getSharedPreferences("test", MODE_PRIVATE);
                    SharedPreferences.Editor edit = userDetails.edit();
                    edit.clear();
                    edit.putString("test1", stringValue);
                    edit.commit();

                    if (ringtone == null) {
                        preference.setSummary(R.string.summary_choose_ringtone);
                    } else {
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("key_gallery_name")) {
                    preference.setSummary(stringValue);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };


    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@androidhive.info"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }


}
