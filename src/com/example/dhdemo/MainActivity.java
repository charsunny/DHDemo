package com.example.dhdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private Button okButton;
	private Button cancelButton;
	private ButtonClickListener btnClickListener = new ButtonClickListener();
	
	private EditText usernameEditText;
	private EditText passwordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		okButton = (Button)findViewById(R.id.okButton);
		okButton.setOnClickListener(btnClickListener);
		
		cancelButton = (Button)findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(btnClickListener);
		
		usernameEditText = (EditText)findViewById(R.id.usernameEditText);
		passwordEditText = (EditText)findViewById(R.id.passwordEditText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean authentication(String username, String password)
	{
		if(username.equals("lideheng") && password.equals("123456"))
		{
			return true;
		}
		
		return true;
	}
	
	class ButtonClickListener implements OnClickListener
	{
		@Override
		public void onClick(View view)
		{
			switch(view.getId())
			{
			case R.id.okButton:
				//身份验证
				if(authentication(usernameEditText.getText().toString(), passwordEditText.getText().toString()))
				{
					Intent rootIntent = new Intent(MainActivity.this, RootActivity.class);
					rootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(rootIntent);
				}
				else
				{
					
				}
				break;
			case R.id.cancelButton:
				break;
			
			}
		}
	}

}
