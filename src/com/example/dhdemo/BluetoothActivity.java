/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dhdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class BluetoothActivity extends Activity {
	
    // 调试用
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    public static String EXTRA_DEVICE_ADDRESS = "设备地址";
    public static String EXTRA_DEVICE = "设备";
    
    public static String BT_MAC_ADDRESS = "bt_mac_address";
    public static String BT_DEV_NAME = "bt_dev_name";
    
    //保存配对设备的地址、名字
    private String pairedDevName = "";
    private String pairedDevAddress = "";
    
    //settings
    private SharedPreferences settings;

    //蓝牙设备，适配器
    private BluetoothDevice mBtDevice;
    private BluetoothAdapter mBtAdapter;
    
    //界面
    private Button scanButton;
    private ButtonClickListener btnClickListener = new ButtonClickListener();
    
    //蓝牙设备列表
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  //设置窗口显示模式为窗口方式
        setContentView(R.layout.activity_bluetooth);

        // 设定默认返回值为取消
        setResult(Activity.RESULT_CANCELED);

        // 设定扫描按键响应
        scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(btnClickListener);
        
        // 初使化设备存储数组
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bt_device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bt_device_name);
        
        settings = PreferenceManager.getDefaultSharedPreferences(this.getApplication());
        if(settings.contains(BT_MAC_ADDRESS))
        {
        	pairedDevName = settings.getString(BT_DEV_NAME, "");
        	pairedDevAddress = settings.getString(BT_MAC_ADDRESS, "");
        	
        	mPairedDevicesArrayAdapter.add(pairedDevName + "\n" + pairedDevAddress);
        }

        // 设置已配队设备列表
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // 设置新查找设备列表
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // 注册BluetoothAdapter action 接收器
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onDestroy() 
    {
        super.onDestroy();

        // 关闭服务查找
        if (mBtAdapter != null) 
        {
            mBtAdapter.cancelDiscovery();
        }

        // 注销action接收器
        this.unregisterReceiver(mReceiver);
    }
    
    public void OnCancel(View v)
    {
    	finish();
    }
    
	class ButtonClickListener implements OnClickListener
	{
		@Override
		public void onClick(View view)
		{
			switch(view.getId())
			{
			case R.id.button_scan:
				doDiscovery();
                view.setVisibility(View.GONE);
				break;
			case R.id.button_cancel:
				break;
			
			}
		}
	}
	
    /**
     * 开始服务和设备查找
     */
    private void doDiscovery()
    {
        if (D) Log.d(TAG, "doDiscovery()");

        // 在窗口显示查找中信息
        setProgressBarIndeterminateVisibility(true);
        setTitle("查找设备中...");

        // 显示其它设备（未配对设备）列表
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // 关闭再进行的服务查找
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        //并重新开始
        mBtAdapter.startDiscovery();
    }

    // 选择设备响应函数 
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
    	
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) 
        {
            // 准备连接设备，关闭服务查找
            mBtAdapter.cancelDiscovery();

            // 得到mac地址
            String info = ((TextView) v).getText().toString();
            int index = info.indexOf("\n");
            String address = info.substring(index + 1);
            String name = info.substring(0, index);
            
            mPairedDevicesArrayAdapter.add(name + "\n" + address);
            mNewDevicesArrayAdapter.remove(name + "\n" + address);
            
            //存储mac地址
            SharedPreferences.Editor editor = settings.edit();

    		editor.putString(BT_MAC_ADDRESS, address);
    		editor.putString(BT_DEV_NAME, name);
    		
    		editor.commit();
/*
            // 设置返回数据
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            intent.putExtra(EXTRA_DEVICE, mBtDevice);

            // 设置返回值并结束程序
            setResult(Activity.RESULT_OK, intent);
            
            finish();
*/
        }
    };

    // 查找到设备和搜索完成action监听器
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
    	
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            // 查找到设备action
            if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                // 得到蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBtDevice = device;
                // 如果是已配对的则略过，已得到显示，其余的在添加到列表中进行显示
                String name = device.getName();
                String address = device.getAddress();
                
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) 
                {
                	if(!pairedDevAddress.equals(address))
                	{
                		mNewDevicesArrayAdapter.add(name + "\n" + address);
                	}
                }
                else
                {  //添加到已配对设备列表
                	if(!pairedDevAddress.equals(address))
                	{
                		mPairedDevicesArrayAdapter.add(name + "\n" + address);
                	}
                }
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) 
            {
                setProgressBarIndeterminateVisibility(false);
                setTitle("选择要连接的设备");
                
                if (mNewDevicesArrayAdapter.getCount() == 0) 
                {
                    String noDevices = "没有找到新设备";
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };


}
