package com.example.dhdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class DetectionQueryDataActivity extends Activity {
	
	private Button realDataButton;
	private Button historyDataButton;
	private Button siteImageButton;
	private ButtonClickListener btnClickListener = new ButtonClickListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detectionquerydata);
		
		realDataButton = (Button)findViewById(R.id.Button_real_data);
		realDataButton.setOnClickListener(btnClickListener);
		
		historyDataButton = (Button)findViewById(R.id.Button_history_data);
		historyDataButton.setOnClickListener(btnClickListener);
		
		siteImageButton = (Button)findViewById(R.id.Button_site_image);
		siteImageButton.setOnClickListener(btnClickListener);
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
			case R.id.Button_real_data:
				Intent realDataIntent = new Intent(DetectionQueryDataActivity.this, RealTimeDataActivity.class);
				realDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(realDataIntent);
				break;
			case R.id.Button_history_data:
				Intent historyDataIntent = new Intent(DetectionQueryDataActivity.this, HistoryDataActivity.class);
				historyDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(historyDataIntent);
				break;
			case R.id.Button_site_image:
				Toast.makeText(DetectionQueryDataActivity.this, getString(R.string.developing_text), Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

}
