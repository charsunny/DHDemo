package com.example.dhdemo;

import com.example.dhdemo.R;
import com.lock.core.AppLock;
import com.lock.core.AppLockActivity;
import com.lock.core.LockManager;
import com.lock.core.PageListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class RootActivity extends Activity {
	
	private static PageListener pageListener;
	
	private Button detectionQueryButton;
	private Button settingButton;
	private Button phoneBookButton;
	private ButtonClickListener btnClickListener = new ButtonClickListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);
		
		detectionQueryButton = (Button)findViewById(R.id.Button_data_detection_query);
		detectionQueryButton.setOnClickListener(btnClickListener);
		
		settingButton = (Button)findViewById(R.id.Button_paraments_setting);
		settingButton.setOnClickListener(btnClickListener);
		
		phoneBookButton = (Button)findViewById(R.id.Button_phone_book);
		phoneBookButton.setOnClickListener(btnClickListener);
		
		LockManager.getInstance().enableAppLock(this.getApplication());
		pageListener = (PageListener)LockManager.getInstance().getAppLock();
		
		if (pageListener != null) 
		{
			pageListener.onActivityCreated(this);
		}
	}

	@Override
	protected void onStart() 
	{
		super.onStart();

		if (pageListener != null) 
		{
			pageListener.onActivityStarted(this);
		}
	}

	@Override
	protected void onResume() 
	{
		super.onResume();

		if (pageListener != null) 
		{
			pageListener.onActivityResumed(this);
		}
	}

	@Override
	protected void onPause() 
	{
		super.onPause();

		if (pageListener != null) 
		{
			pageListener.onActivityPaused(this);
		}
	}

	@Override
	protected void onStop() 
	{
		super.onStop();

		if (pageListener != null) 
		{
			pageListener.onActivityStopped(this);
		}
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();

		if (pageListener != null) 
		{
			pageListener.onActivityDestroyed(this);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);

		if (pageListener != null) 
		{
			pageListener.onActivitySaveInstanceState(this);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) 
		{
		case AppLock.DISABLE_PASSLOCK:
			break;
		case AppLock.ENABLE_PASSLOCK:
		case AppLock.CHANGE_PASSWORD:
			if (resultCode == RESULT_OK) 
			{
				Toast.makeText(this, getString(R.string.setup_passcode), Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class ButtonClickListener implements OnClickListener
	{
		@Override
		public void onClick(View view)
		{
			switch(view.getId())
			{
			case R.id.Button_data_detection_query:
				Intent detectionQueryIntent = new Intent(RootActivity.this, DetectionQueryDataActivity.class);
				detectionQueryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(detectionQueryIntent);
				break;
			case R.id.Button_paraments_setting:
				Intent settingIntent = new Intent(RootActivity.this, SettingActivity.class);
				settingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(settingIntent);
				break;
			case R.id.Button_phone_book:
				Toast.makeText(RootActivity.this, getString(R.string.developing_text), Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

}
