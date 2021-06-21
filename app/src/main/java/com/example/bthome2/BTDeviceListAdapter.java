package com.example.bthome2;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BTDeviceListAdapter extends BaseAdapter {

    private int delay =0;
    private Context context;
    private ArrayList<BluetoothDevice> pairedDevices;
    private BTDeviceListClickListener onClickDevice;
    private LayoutInflater layoutInflater;

    public BTDeviceListAdapter(Context context, ArrayList<BluetoothDevice> pairedDevices, BTDeviceListClickListener onClickDevice) {
        this.context = context;
        this.pairedDevices = pairedDevices;
        this.onClickDevice = onClickDevice;
        layoutInflater = LayoutInflater.from(context);
        delay  = 1000;
    }

    @Override
    public int getCount() {
        return pairedDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return pairedDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.list_device_layout,null);
        TextView tvName = convertView.findViewById(R.id.textViewBTListDeviceName);
        TextView tvMac = convertView.findViewById(R.id.textViewBTListDeviceMac);
        final BluetoothDevice bluetoothDevice = pairedDevices.get(position);
        tvName.setText(bluetoothDevice.getName());
        tvMac.setText(bluetoothDevice.getAddress());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDevice.onClick(bluetoothDevice);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context,  R.anim.right_to_left_in);
        convertView.startAnimation(animation);


        return convertView;
    }


}
