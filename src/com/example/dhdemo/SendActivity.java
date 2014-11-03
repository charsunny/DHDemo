package com.example.dhdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SendActivity extends Activity {
	
	private Button sendButton;
	private ButtonClickListener btnClickListener = new ButtonClickListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		
		sendButton = (Button)findViewById(R.id.sendButton);
		sendButton.setOnClickListener(btnClickListener);
		
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
			case R.id.sendButton:
				break;
			}
		}
	}

}
