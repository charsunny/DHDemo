package com.example.dhdemo;

import com.lock.core.AppLock;
import com.lock.core.AppLockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference;
import android.preference.ListPreference;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;

public class SettingActivity extends PreferenceActivity implements OnPreferenceClickListener, 
OnPreferenceChangeListener{
	
	ListPreference soilTypeListPref;
	EditTextPreference devIDEditPref;
	
	EditTextPreference sensorDepthEditPref;
	EditTextPreference soilsBulkEditPref;
	EditTextPreference fieldWaterEditPref;
	
	EditTextPreference lonEditPref;
	EditTextPreference latEditPref;
	
	EditTextPreference AEditPref;
	EditTextPreference BEditPref;
	EditTextPreference CEditPref;
	EditTextPreference DEditPref;
	
	CheckBoxPreference PSCheckBoxPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_setting);
		addPreferencesFromResource(R.xml.setting_preference);
		
		//取得属于整个应用程序的SharedPreferences  
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getApplication());
/*		
		soilTypeListPref = (ListPreference)findPreference(getResources().getString(R.string.soil_type_key));     
		soilTypeListPref.setOnPreferenceChangeListener(this);  
		soilTypeListPref.setOnPreferenceClickListener(this);
*/		    
        String devID = settings.getString(getResources().getString(R.string.device_id_key), "0");
		devIDEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.device_id_key));    
		devIDEditPref.setSummary(devID);
		devIDEditPref.setOnPreferenceChangeListener(this);  
		devIDEditPref.setOnPreferenceClickListener(this); 
/*		
		String sensorDepth = settings.getString(getResources().getString(R.string.sensor_depth_key), "0");
		sensorDepthEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.sensor_depth_key)); 
		sensorDepthEditPref.setSummary(sensorDepth);
		sensorDepthEditPref.setOnPreferenceChangeListener(this);  
		sensorDepthEditPref.setOnPreferenceClickListener(this);
*/		
		String soilsBulk = settings.getString(getResources().getString(R.string.soils_bulk_key), "0");
		soilsBulkEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.soils_bulk_key));     
		soilsBulkEditPref.setSummary(soilsBulk);
		soilsBulkEditPref.setOnPreferenceChangeListener(this);  
		soilsBulkEditPref.setOnPreferenceClickListener(this);
		
		String fieldWater = settings.getString(getResources().getString(R.string.field_water_key), "0");
		fieldWaterEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.field_water_key));     
		fieldWaterEditPref.setSummary(fieldWater);
		fieldWaterEditPref.setOnPreferenceChangeListener(this);  
		fieldWaterEditPref.setOnPreferenceClickListener(this);
		
		String lon = settings.getString(getResources().getString(R.string.lon_key), "0");
		lonEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.lon_key)); 
		lonEditPref.setSummary(lon);
		lonEditPref.setOnPreferenceChangeListener(this);  
		lonEditPref.setOnPreferenceClickListener(this);
		
		String lat = settings.getString(getResources().getString(R.string.lat_key), "0");
		latEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.lat_key)); 
		latEditPref.setSummary(lat);
		latEditPref.setOnPreferenceChangeListener(this);  
		latEditPref.setOnPreferenceClickListener(this);
		
		String A = settings.getString(getResources().getString(R.string.A_key), "0");
		AEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.A_key)); 
		AEditPref.setSummary(A);
		AEditPref.setOnPreferenceChangeListener(this);  
		AEditPref.setOnPreferenceClickListener(this);
		
		String B = settings.getString(getResources().getString(R.string.B_key), "0");
		BEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.B_key));  
		BEditPref.setSummary(B);
		BEditPref.setOnPreferenceChangeListener(this);  
		BEditPref.setOnPreferenceClickListener(this);
		
		String C = settings.getString(getResources().getString(R.string.C_key), "0");
		CEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.C_key));  
		CEditPref.setSummary(C);
		CEditPref.setOnPreferenceChangeListener(this);  
		CEditPref.setOnPreferenceClickListener(this);
		
		String D = settings.getString(getResources().getString(R.string.D_key), "0");
		DEditPref = (EditTextPreference)findPreference(getResources().getString(R.string.D_key));     
		DEditPref.setSummary(D);
		DEditPref.setOnPreferenceChangeListener(this);  
		DEditPref.setOnPreferenceClickListener(this);
		
		boolean password = settings.getBoolean(getResources().getString(R.string.enable_password_key), false);
		boolean enablePS = false;
		if (settings.contains("passcode")) 
		{
			enablePS = true;
		}
		PSCheckBoxPref = (CheckBoxPreference)findPreference(getResources().getString(R.string.enable_password_key));     
		//PSCheckBoxPref.setSummary(enablePS);
		PSCheckBoxPref.setChecked(enablePS);
		PSCheckBoxPref.setOnPreferenceChangeListener(this);  
		PSCheckBoxPref.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_setting, menu);
		return true;
	}
	
	@Override  
    public boolean onPreferenceChange(Preference preference, Object newValue) {  

		String key = preference.getKey();
         
        if(getResources().getString(R.string.soil_type_key).equals(key))  
        {  
        	int index = Integer.parseInt(newValue.toString()) - 1;
    		String soilTypeValue = getResources().getStringArray(R.array.soil_type)[index];
    		preference.setSummary(soilTypeValue);
        }   
        else if(getResources().getString(R.string.device_id_key).equals(key))  
        {
        	int value = Integer.parseInt(newValue.toString());
        	if(value < 0)
        	{
        		return false;
        	}
    		preference.setSummary(newValue.toString());
        } 
        else if(getResources().getString(R.string.sensor_depth_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.soils_bulk_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.field_water_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.lon_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.lat_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.A_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.B_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.C_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.D_key).equals(key))  
        {  
    		preference.setSummary(newValue.toString());
        }
        else if(getResources().getString(R.string.enable_password_key).equals(key))  
        {  
        	Intent intent = new Intent(SettingActivity.this, AppLockActivity.class);
        	boolean check = newValue.toString().equals("true");
			if(check)
			{
				intent.putExtra(AppLock.TYPE, AppLock.ENABLE_PASSLOCK);
			}
			else
			{
				intent.putExtra(AppLock.TYPE, AppLock.DISABLE_PASSLOCK);
			}
			//intent.putExtra(ACTIVESTATE_KEY, isFirstActive());
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			startActivity(intent);
        }
        else  
        {  
            //如果返回false表示不允许被改变  
            return false;  
        }  
        //返回true表示允许改变  
        return true;  
    }  
	
    @Override  
    public boolean onPreferenceClick(Preference preference) {  

        //判断是哪个Preference被点击了  
        if(preference.getKey().equals(R.string.soil_type_key))  
        {  

        }   
        else  
        {  
            return false;  
        }  
        
        return true;  
    }  

}
