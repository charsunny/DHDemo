package com.example.dhdemo;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class HistoryDataActivity extends Activity {
	
	//显示模式
	private static final int NORMAL_MODE = 1025;
	private static final int EDIT_MODE = 1026;
	
	private int displayMode = NORMAL_MODE;

	//上下文activity
	Activity mActivity;

	//normal模式
	private ListView normalModeList;
	private Button editButton;
	
	private NormalModeAdapter normalModeAdapter;
	
	//edit模式
	private ListView editModeList;
	private Button okButton;
	private Button sendButton;
	private Button deleteButton;
	
	private EditModeAdapter editModeAdapter;
	
	
	//spinner
	private Spinner spinner;
    private ArrayAdapter<String> adapter;
    
	private static final String[] siteItems = 
		{"站点ID: all",
		 "站点ID: 大屯站",
		 "站点ID: 小屯站",
		 "站点ID: 000003",
		 "站点ID: 000004"
		};
	
	private static final String[] siteIDValues = 
		{"0",
		 "大屯站",
		 "小屯站",
		 "000003",
		 "000004"
		};
	
	private String selectSiteID;

	// 数据类
	class SiteData 
	{
		SiteData(String siteID, 
				 String detectionTime, 
				 String valumetricWater, 
				 String weightWater,
				 String lon,
				 String lat,
				 String soilType,
				 String measureDepth) 
		{
			this.siteID = siteID;
			this.detectionTime = detectionTime;
			this.valumetricWater = valumetricWater;
			this.weightWater = weightWater;
			this.lon = lon;
			this.lat = lat;
			this.soilType = soilType;
			this.measureDepth = measureDepth;
		}

		String siteID;
		String detectionTime;
		String valumetricWater;
		String weightWater;
		String lon;
		String lat;
		String soilType;
		String measureDepth;
		
		@Override
		public String toString()
		{
			return siteID 
					+ "(" + detectionTime + ")"
					+ "(" + valumetricWater + ")"
					+ "(" + weightWater + ")"
					+ "(" + lon + ")"
					+ "(" + lat + ")"
					+ "(" + soilType + ")"
					+ "(" + measureDepth + ")";
		}
	}

	// init data
	SiteData[] siteDataCollection = new SiteData[] {
			
			new SiteData("大屯站", "2014-10-2 3:45", "17", "12.5", "106.2096", "40.0059", "粘土", "10"),
			new SiteData("大屯站", "2014-10-2 3:45", "17", "12.5", "106.2096", "40.0059", "粘土", "10"),
			new SiteData("小屯站", "2014-10-2 3:45", "17", "12.5", "106.2096", "40.0059", "粘土", "10"),
			new SiteData("000003", "2014-10-2 3:45", "17", "12.5", "106.2096", "40.0059", "粘土", "10"),
			new SiteData("000004", "2014-10-2 3:45", "17", "12.5", "106.2096", "40.0059", "粘土", "10"),
			new SiteData("000005", "2014-10-2 3:45", "17", "12.5", "106.2096", "40.0059", "粘土", "10"),

	};
	
	ArrayList<SiteData> showSiteDataArray = new ArrayList<SiteData>();
	ArrayList<SiteData> allSiteDataArray = new ArrayList<SiteData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mActivity = this;
		
		for(int i = 0; i < 6; i++)
		{
			SiteData data = siteDataCollection[i];
			showSiteDataArray.add(data);
			allSiteDataArray.add(data);
		}
		
		initUI();
	};

	private void initUI() 
	{
		if (displayMode == NORMAL_MODE) 
		{
			toNormalMode();
		} 
		else 
		{
			toEditMode();
		}
	};

	private void toEditMode() 
	{
		displayMode = EDIT_MODE;
		
		this.setContentView(R.layout.multiselection_edit);
		
		//init list
		editModeList = (ListView) findViewById(R.id.editModeList);
		editModeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		editModeAdapter = new EditModeAdapter();
		editModeList.setAdapter(editModeAdapter);
		editModeList.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position, long id) {
				editModeAdapter.toggle(position);
			}
		});
		
		okButton = (Button) findViewById(R.id.okButton);
		okButton.setOnClickListener(new SwitchModeButtonListener());
		
		deleteButton = (Button) findViewById(R.id.editModeDeleteButton);
		deleteButton.setOnClickListener(new DeleteButtonListener());
		
		sendButton = (Button) findViewById(R.id.editModeSendButton);
		sendButton.setOnClickListener(new SendButtonListener());
		
		spinner = (Spinner) findViewById(R.id.siteSpinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, siteItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);
	};

	private void toNormalMode() 
	{
		displayMode = NORMAL_MODE;
		
		this.setContentView(R.layout.multiselection_normal);
		
		normalModeList = (ListView) findViewById(R.id.normalModeList);
		normalModeAdapter = new NormalModeAdapter();
		normalModeList.setAdapter(normalModeAdapter);
		
		editButton = (Button) findViewById(R.id.editButton);
		editButton.setOnClickListener(new SwitchModeButtonListener());
		
		spinner = (Spinner) findViewById(R.id.siteSpinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, siteItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);
	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener
	{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
        {
            //view.setText("你的血型是："+m[arg2]);
        	if(selectSiteID == siteIDValues[arg2])
        	{
        		return;
        	}
        	
        	selectSiteID = siteIDValues[arg2];
        	
        	if(arg2 == 0)
        	{
        		showSiteDataArray.addAll(allSiteDataArray);
        	}
        	else
        	{
	        	ArrayList<SiteData> siteDataArray = new ArrayList<SiteData>();
	        	siteDataArray.addAll(allSiteDataArray);
	        	showSiteDataArray.removeAll(showSiteDataArray);
	        	
	        	for(int i = 0; i < siteDataArray.size(); i++)
	        	{
	        		SiteData data = siteDataArray.get(i);
	        		if(data.siteID == selectSiteID)
	        		{
	        			showSiteDataArray.add(data);
	        		}
	        	}
        	}
        	
        	if (displayMode == NORMAL_MODE)
        	{
        		normalModeAdapter.notifyDataSetChanged();
        	}
        	else
        	{
        		editModeAdapter.notifyDataSetChanged();
        	}
        }
 
        public void onNothingSelected(AdapterView<?> arg0) 
        {
        	
        }
    }

	class SwitchModeButtonListener implements OnClickListener 
	{
		@Override
		public void onClick(View v) 
		{
			if (displayMode == NORMAL_MODE)
			{
				toEditMode();
			}
			else
			{
				toNormalMode();
			}
		}
	}

	class DeleteButtonListener implements OnClickListener 
	{
		@Override
		public void onClick(View v) 
		{
			int[] selectedItemIndexes = editModeAdapter.getSelectedItemIndexes();
			int size = selectedItemIndexes.length;
			
			if (size == 0) 
			{
				Toast.makeText(mActivity, "Nothing to delete", Toast.LENGTH_SHORT).show();
			} 
			else 
			{
				for(int i = 0; i < size; i++)
				{
					showSiteDataArray.remove(selectedItemIndexes[i]);
				}
				
				editModeAdapter.clearSelectedStatus();
				editModeAdapter.notifyDataSetChanged();
			}
		}
	}
	
	class SendButtonListener implements OnClickListener 
	{
		@Override
		public void onClick(View v) 
		{
			int[] selectedItemIndexes = editModeAdapter.getSelectedItemIndexes();
			int size = selectedItemIndexes.length;
			
			if (size == 0) 
			{
				Toast.makeText(mActivity, "Nothing to delete", Toast.LENGTH_SHORT).show();
			} 
			else 
			{
				Intent sendIntent = new Intent(HistoryDataActivity.this, SendActivity.class);
				sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(sendIntent);
			}
		}
	}

	//缓存
	static class ViewHolderNormal 
	{
		TextView siteIDValueTextView;
		TextView detectionTimeValueTextView;
		TextView valumetricWaterValueTextView;
		TextView weightWaterValueTextView;
		TextView lonValueTextView;
		TextView latValueTextView;
		TextView soilTypeValueTextView;
		TextView measureDepthValueTextView;
	}

	class NormalModeAdapter extends BaseAdapter 
	{
		LayoutInflater inflater = null;

		@Override
		public int getCount() 
		{
			return showSiteDataArray.size();
		}

		@Override
		public Object getItem(int arg0) 
		{
			return null;
		}

		@Override
		public long getItemId(int arg0) 
		{
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ViewHolderNormal viewHolder;
			
			if (convertView == null) 
			{
				if (inflater == null) 
				{
					inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
				}
			
				convertView = inflater.inflate(R.layout.item_normal, null);
				
				viewHolder = new ViewHolderNormal();
				viewHolder.siteIDValueTextView = (TextView) convertView.findViewById(R.id.textView_siteIdValue);
				viewHolder.detectionTimeValueTextView = (TextView) convertView.findViewById(R.id.textView_detectionTimeValue);
				viewHolder.valumetricWaterValueTextView = (TextView) convertView.findViewById(R.id.textView_valumetricValue);
				viewHolder.weightWaterValueTextView = (TextView) convertView.findViewById(R.id.textView_weightwaterValue);
				viewHolder.lonValueTextView = (TextView) convertView.findViewById(R.id.textView_lonlatValue);
				viewHolder.soilTypeValueTextView = (TextView) convertView.findViewById(R.id.textView_soilTypeValue);
				viewHolder.measureDepthValueTextView = (TextView) convertView.findViewById(R.id.textView_measureDepthValue);
				
				convertView.setTag(viewHolder);
			} 
			else 
			{
				viewHolder = (ViewHolderNormal) convertView.getTag();
			}
				
			//数据
			SiteData data = showSiteDataArray.get(position);
			viewHolder.siteIDValueTextView.setText(data.siteID);
			viewHolder.detectionTimeValueTextView.setText(data.detectionTime);
			viewHolder.valumetricWaterValueTextView.setText(data.valumetricWater);
			viewHolder.weightWaterValueTextView.setText(data.weightWater);
			
			String lonlatValue = data.lon + "/" + data.lat;
			viewHolder.lonValueTextView.setText(lonlatValue);
			
			viewHolder.soilTypeValueTextView.setText(data.soilType);
			viewHolder.measureDepthValueTextView.setText(data.measureDepth);
			
				
			return convertView;
		}
	}

	static class ViewHolderEdit 
	{
		TextView siteIDValueTextView;
		TextView detectionTimeValueTextView;
		TextView valumetricWaterValueTextView;
		TextView weightWaterValueTextView;
		TextView lonValueTextView;
		TextView latValueTextView;
		TextView soilTypeValueTextView;
		TextView measureDepthValueTextView;
		
		CheckBox checkBox;
	}

	class EditModeAdapter extends BaseAdapter 
	{
		LayoutInflater inflater = null;
		
		boolean[] itemStatus;
		{
			itemStatus = new boolean[siteDataCollection.length];
		}
		
		//ArrayList<boolean> itemStatus = new ArrayList<boolean>();

		@Override
		public int getCount() 
		{
			return showSiteDataArray.size();
		}

		@Override
		public Object getItem(int arg0) 
		{
			return null;
		}

		public void toggle(int position)
		{
			if(itemStatus[position] == true)
			{
				itemStatus[position] = false;
			}
			else
			{
				itemStatus[position] = true;
			}
			
			//date changed and we should refresh the view
			this.notifyDataSetChanged();
		}
		
		@Override
		public long getItemId(int arg0) 
		{
			return 0;
		}

		public int[] getSelectedItemIndexes() 
		{
			if (itemStatus == null || itemStatus.length == 0) 
			{
				return new int[0];
			} 
			else 
			{
				int size = itemStatus.length;
				int counter = 0;
				// TODO how can we skip this iteration?
				for (int i = 0; i < size; i++) 
				{
					if (itemStatus[i] == true)
						++counter;
				}
				
				int[] selectedIndexes = new int[counter];
				int index = 0;
				for (int i = 0; i < size; i++) 
				{
					if (itemStatus[i] == true)
						selectedIndexes[index++] = i;
				}
				
				return selectedIndexes;
			}
		};
		
		public void clearSelectedStatus()
		{
			for(int i = 0; i < siteDataCollection.length; i++)
			{
				itemStatus[i] = false;
			}
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ViewHolderEdit viewHolder;
			if (convertView == null) 
			{
				if (inflater == null) 
				{
					inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
				}
				convertView = inflater.inflate(R.layout.item_edit, null);
				viewHolder = new ViewHolderEdit();
				
				viewHolder.siteIDValueTextView = (TextView) convertView.findViewById(R.id.textView_siteIdValue);
				viewHolder.detectionTimeValueTextView = (TextView) convertView.findViewById(R.id.textView_detectionTimeValue);
				viewHolder.valumetricWaterValueTextView = (TextView) convertView.findViewById(R.id.textView_valumetricValue);
				viewHolder.weightWaterValueTextView = (TextView) convertView.findViewById(R.id.textView_weightwaterValue);
				viewHolder.lonValueTextView = (TextView) convertView.findViewById(R.id.textView_lonlatValue);
				viewHolder.soilTypeValueTextView = (TextView) convertView.findViewById(R.id.textView_soilTypeValue);
				viewHolder.measureDepthValueTextView = (TextView) convertView.findViewById(R.id.textView_measureDepthValue);
				
				viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxEdit);
				viewHolder.checkBox.setVisibility(View.VISIBLE);
				
				convertView.setTag(viewHolder);
			} 
			else 
			{
				viewHolder = (ViewHolderEdit) convertView.getTag();
			}
			
			//数据
			SiteData data = showSiteDataArray.get(position);
			viewHolder.siteIDValueTextView.setText(data.siteID);
			viewHolder.detectionTimeValueTextView.setText(data.detectionTime);
			viewHolder.valumetricWaterValueTextView.setText(data.valumetricWater);
			viewHolder.weightWaterValueTextView.setText(data.weightWater);
			
			String lonlatValue = data.lon + "/" + data.lat;
			viewHolder.lonValueTextView.setText(lonlatValue);
			
			viewHolder.soilTypeValueTextView.setText(data.soilType);
			viewHolder.measureDepthValueTextView.setText(data.measureDepth);
			
			viewHolder.checkBox.setOnCheckedChangeListener(new MyCheckBoxChangedListener(position));
			if (itemStatus[position] == true) 
			{
				viewHolder.checkBox.setChecked(true);
			} 
			else 
			{
				viewHolder.checkBox.setChecked(false);
			}
			
			return convertView;
		}

		class MyCheckBoxChangedListener implements OnCheckedChangeListener 
		{
			int position;

			MyCheckBoxChangedListener(int position) 
			{
				this.position = position;
			}

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				System.out.println("" + position + "Checked?:" + isChecked);
				
				if (isChecked)
				{
					itemStatus[position] = true;
				}
				else
				{
					itemStatus[position] = false;
				}
			}
		}
	}
}