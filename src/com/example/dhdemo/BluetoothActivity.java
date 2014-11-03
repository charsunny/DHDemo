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
	
    // ������
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    public static String EXTRA_DEVICE_ADDRESS = "�豸��ַ";
    public static String EXTRA_DEVICE = "�豸";
    
    public static String BT_MAC_ADDRESS = "bt_mac_address";
    public static String BT_DEV_NAME = "bt_dev_name";
    
    //��������豸�ĵ�ַ������
    private String pairedDevName = "";
    private String pairedDevAddress = "";
    
    //settings
    private SharedPreferences settings;

    //�����豸��������
    private BluetoothDevice mBtDevice;
    private BluetoothAdapter mBtAdapter;
    
    //����
    private Button scanButton;
    private ButtonClickListener btnClickListener = new ButtonClickListener();
    
    //�����豸�б�
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  //���ô�����ʾģʽΪ���ڷ�ʽ
        setContentView(R.layout.activity_bluetooth);

        // �趨Ĭ�Ϸ���ֵΪȡ��
        setResult(Activity.RESULT_CANCELED);

        // �趨ɨ�谴����Ӧ
        scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(btnClickListener);
        
        // ��ʹ���豸�洢����
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bt_device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bt_device_name);
        
        settings = PreferenceManager.getDefaultSharedPreferences(this.getApplication());
        if(settings.contains(BT_MAC_ADDRESS))
        {
        	pairedDevName = settings.getString(BT_DEV_NAME, "");
        	pairedDevAddress = settings.getString(BT_MAC_ADDRESS, "");
        	
        	mPairedDevicesArrayAdapter.add(pairedDevName + "\n" + pairedDevAddress);
        }

        // ����������豸�б�
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // �����²����豸�б�
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // ע��BluetoothAdapter action ������
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

        // �رշ������
        if (mBtAdapter != null) 
        {
            mBtAdapter.cancelDiscovery();
        }

        // ע��action������
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
     * ��ʼ������豸����
     */
    private void doDiscovery()
    {
        if (D) Log.d(TAG, "doDiscovery()");

        // �ڴ�����ʾ��������Ϣ
        setProgressBarIndeterminateVisibility(true);
        setTitle("�����豸��...");

        // ��ʾ�����豸��δ����豸���б�
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // �ر��ٽ��еķ������
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        //�����¿�ʼ
        mBtAdapter.startDiscovery();
    }

    // ѡ���豸��Ӧ���� 
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
    	
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) 
        {
            // ׼�������豸���رշ������
            mBtAdapter.cancelDiscovery();

            // �õ�mac��ַ
            String info = ((TextView) v).getText().toString();
            int index = info.indexOf("\n");
            String address = info.substring(index + 1);
            String name = info.substring(0, index);
            
            mPairedDevicesArrayAdapter.add(name + "\n" + address);
            mNewDevicesArrayAdapter.remove(name + "\n" + address);
            
            //�洢mac��ַ
            SharedPreferences.Editor editor = settings.edit();

    		editor.putString(BT_MAC_ADDRESS, address);
    		editor.putString(BT_DEV_NAME, name);
    		
    		editor.commit();
/*
            // ���÷�������
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            intent.putExtra(EXTRA_DEVICE, mBtDevice);

            // ���÷���ֵ����������
            setResult(Activity.RESULT_OK, intent);
            
            finish();
*/
        }
    };

    // ���ҵ��豸���������action������
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
    	
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            // ���ҵ��豸action
            if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                // �õ������豸
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBtDevice = device;
                // ���������Ե����Թ����ѵõ���ʾ�����������ӵ��б��н�����ʾ
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
                {  //��ӵ�������豸�б�
                	if(!pairedDevAddress.equals(address))
                	{
                		mPairedDevicesArrayAdapter.add(name + "\n" + address);
                	}
                }
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) 
            {
                setProgressBarIndeterminateVisibility(false);
                setTitle("ѡ��Ҫ���ӵ��豸");
                
                if (mNewDevicesArrayAdapter.getCount() == 0) 
                {
                    String noDevices = "û���ҵ����豸";
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };


}
