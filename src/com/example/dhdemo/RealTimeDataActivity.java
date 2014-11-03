package com.example.dhdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class RealTimeDataActivity extends Activity {
	
	private Button modifyButton;
	private Button saveButton;
	private Button sendButton;
	private ButtonClickListener btnClickListener = new ButtonClickListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_realtimedata);
		
		modifyButton = (Button)findViewById(R.id.Button_modify);
		modifyButton.setOnClickListener(btnClickListener);
		
		saveButton = (Button)findViewById(R.id.Button_save);
		saveButton.setOnClickListener(btnClickListener);
		
		sendButton = (Button)findViewById(R.id.Button_send);
		sendButton.setOnClickListener(btnClickListener);
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
			case R.id.Button_modify:
				break;
			case R.id.Button_save:
				break;
			case R.id.Button_send:
				Intent sendIntent = new Intent(RealTimeDataActivity.this, SendActivity.class);
				sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(sendIntent);
				break;
			}
		}
	}

}
