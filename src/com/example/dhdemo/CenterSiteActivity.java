package com.example.dhdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CenterSiteActivity extends Activity {
	
	private Button addButton;
	private Button editButton;

	private ButtonClickListener btnClickListener = new ButtonClickListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_centersite);
		
		addButton = (Button)findViewById(R.id.Button_add);
		addButton.setOnClickListener(btnClickListener);
		
		editButton = (Button)findViewById(R.id.Button_edit);
		editButton.setOnClickListener(btnClickListener);
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
			case R.id.Button_add:
				break;
			case R.id.Button_edit:
				break;
			}
		}
	}

}
