package com.example.bthome2;


import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    enum UI{
        CONNECT,
        DEVICE_LIST,
        CONNECTING,
        SWITCHES
    }

    private UI ui;

    private final int REQUEST_ENABLE_BT = 5;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevicesSet;
    private ArrayList<BluetoothDevice> btDevices = new ArrayList<>();

    private BTDeviceListAdapter btDeviceListAdapter = null;


    private View layoutConnect;
    private ImageButton buttonConnect;

    private View layoutBtDeviceList;
    private ListView listViewDevices;


    private View layoutProgress;
    private ProgressBar progressBar;
    private TextView textViewProgress;

    private View layoutSwitches;
    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private ToggleButton toggleButton3;
    private ToggleButton toggleButton4;



















    /*********************************************************************************************/
    /****************************        DEFAULT METHODS START          **************************/
    /*********************************************************************************************/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUiComponents();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initialUiConnect();


        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);*/
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    setBluetoothDeviceList();
                }
                else if(resultCode == RESULT_CANCELED){
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.mainLayout), "Bluetooth is not enabled!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onBackPressed() {
        if(ui == UI.CONNECT) {
            super.onBackPressed();
        }
        else if(ui == UI.DEVICE_LIST){
            changeUiToConnectFromDeviceList();
        }
        else if(ui == UI.CONNECTING){
            changeUiToConnectFromConnecting();
        }
        else if(ui == UI.SWITCHES){
            disconnectAndChangeUiToFromSwitchesToConnect();
        }

    }


    /*********************************************************************************************/
    /****************************          DEFAULT METHODS END          **************************/
    /*********************************************************************************************/






















    /*********************************************************************************************/
    /****************************          UI COMPONENTS START          **************************/
    /*********************************************************************************************/


    private void initUiComponents(){

        layoutConnect = findViewById(R.id.layoutConnect);
        buttonConnect = findViewById(R.id.imageButtonConnectDevice);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickConnect();
            }
        });


        layoutBtDeviceList = findViewById(R.id.layoutDeviceList);
        listViewDevices = findViewById(R.id.listViewDeviceList);


        layoutProgress = findViewById(R.id.layoutProgress);
        progressBar = findViewById(R.id.progressBar);
        textViewProgress = findViewById(R.id.textViewProgress);


        layoutSwitches = findViewById(R.id.layoutSwitch);
        toggleButton1 = findViewById(R.id.toggleButton1);
        toggleButton2 = findViewById(R.id.toggleButton2);
        toggleButton3 = findViewById(R.id.toggleButton3);
        toggleButton4 = findViewById(R.id.toggleButton4);

        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton1.getText().equals("OFF")){
                   BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                       @Override
                       public void onResult(boolean sent) {

                       }
                   };
                    SendThread s =new SendThread(bluetoothSendResult,"OFF1\r\n");
                    s.start();
                }
                else if(toggleButton1.getText().equals("ON")){
                    BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                        @Override
                        public void onResult(boolean sent) {

                        }
                    };
                    SendThread s =new SendThread(bluetoothSendResult,"ON1\r\n");
                    s.start();
                }
            }
        });


        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton2.getText().equals("OFF")){
                    BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                        @Override
                        public void onResult(boolean sent) {

                        }
                    };
                    SendThread s =new SendThread(bluetoothSendResult,"OFF2\r\n");
                    s.start();
                }
                else if(toggleButton2.getText().equals("ON")){
                    BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                        @Override
                        public void onResult(boolean sent) {

                        }
                    };
                    SendThread s =new SendThread(bluetoothSendResult,"ON2\r\n");
                    s.start();
                }
            }
        });



        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton3.getText().equals("OFF")){
                    BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                        @Override
                        public void onResult(boolean sent) {

                        }
                    };
                    SendThread s =new SendThread(bluetoothSendResult,"OFF3\r\n");
                    s.start();
                }
                else if(toggleButton3.getText().equals("ON")){
                    BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                        @Override
                        public void onResult(boolean sent) {

                        }
                    };
                    SendThread s =new SendThread(bluetoothSendResult,"ON3\r\n");
                    s.start();
                }
            }
        });


        toggleButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton4.getText().equals("OFF")){
                    BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                        @Override
                        public void onResult(boolean sent) {

                        }
                    };
                    SendThread s =new SendThread(bluetoothSendResult,"OFF4\r\n");
                    s.start();
                }
                else if(toggleButton4.getText().equals("ON")){
                    BluetoothSendResult bluetoothSendResult = new BluetoothSendResult() {
                        @Override
                        public void onResult(boolean sent) {

                        }
                    };
                    SendThread s =new SendThread(bluetoothSendResult,"ON4\r\n");
                    s.start();
                }
            }
        });



        //remove all layout
        layoutConnect.setVisibility(View.GONE);
        layoutBtDeviceList.setVisibility(View.GONE);
        layoutProgress.setVisibility(View.GONE);
        layoutSwitches.setVisibility(View.GONE);
    }



    /*********************************************************************************************/
    /****************************           UI COMPONENTS END           **************************/
    /*********************************************************************************************/






























































    /*********************************************************************************************/
    /****************************          ANIMATIONS START             **************************/
    /*********************************************************************************************/




    private void initialUiConnect(){
        layoutConnect.animate()
                .alpha(01.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        layoutConnect.setVisibility(View.VISIBLE);
                    }
                });
        ui = UI.CONNECT;
    }




    private void onClickConnectAnimation(){
        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.down_to_up_out);
        layoutConnect.startAnimation(animationOut);
        animationOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutConnect.setVisibility(View.GONE);
                layoutProgress.setVisibility(View.GONE);
                layoutSwitches.setVisibility(View.GONE);
                layoutBtDeviceList.setVisibility(View.VISIBLE);
                setBluetoothDeviceList();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ui = UI.DEVICE_LIST;
    }


    private void changeUiToConnectFromDeviceList(){

        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.right_to_left_out);
        layoutBtDeviceList.startAnimation(animationOut);
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutBtDeviceList.setVisibility(View.GONE);


                Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.up_to_down_in);
                layoutConnect.startAnimation(animationIn);
                layoutConnect.setVisibility(View.VISIBLE);
                animationIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ui = UI.CONNECT;
    }


    boolean cancelConnection = false;
    private void changeUiToConnectFromConnecting(){
        cancelConnection = true;
        if(connectThread != null) {
            if (connectThread.isAlive()) {
                Log.d(TAG, "connectThread.isAlive()[changeUiToConnectFromConnecting]");
                connectThread.cancel();
                new Thread() {
                    @Override
                    public void run() {
                        while (connectThread.isAlive()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
                Log.d(TAG, "not connectThread.isAlive()[changeUiToConnectFromConnecting]");
            }
        }

        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.down_to_up_out);
        layoutProgress.startAnimation(animationOut);
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutProgress.setVisibility(View.GONE);



                Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.left_to_right_in);
                layoutConnect.startAnimation(animationIn);
                animationIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutConnect.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ui = UI.CONNECT;
    }



    private void changeUiConnectingToSwitches(){

        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.down_to_up_out);
        layoutProgress.startAnimation(animationOut);
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutProgress.setVisibility(View.GONE);



                Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.down_to_up_in);
                layoutSwitches.startAnimation(animationIn);
                layoutSwitches.setVisibility(View.VISIBLE);
                animationIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ui = UI.SWITCHES;

    }



    private void disconnectAndChangeUiToFromSwitchesToConnect(){
        if(connectThread != null) {
            if (connectThread.isAlive()) {
                Log.d(TAG, "connectThread.isAlive()[disconnectAndChangeUiToFromSwitchesToConnect]");
                connectThread.cancel();
                new Thread() {
                    @Override
                    public void run() {
                        while (connectThread.isAlive()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
                Log.d(TAG, "not connectThread.isAlive()[disconnectAndChangeUiToFromSwitchesToConnect]");
            }
        }

        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.up_to_down_out);
        layoutSwitches.startAnimation(animationOut);
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutSwitches.setVisibility(View.GONE);



                Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.up_to_down_in);
                layoutConnect.startAnimation(animationIn);
                layoutConnect.setVisibility(View.VISIBLE);
                animationIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ui = UI.CONNECT;


    }


    /*********************************************************************************************/
    /****************************          ANIMATIONS END             **************************/
    /*********************************************************************************************/








































    /*********************************************************************************************/
    /****************************         USER ACTION START             **************************/
    /*********************************************************************************************/


    private void onClickConnect(){
        cancelConnection = false;
        if (bluetoothAdapter == null) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.mainLayout), "Bluetooth not exist!", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else{
            onClickConnectAnimation();
        }
    }



    private void setBluetoothDeviceList(){
        pairedDevicesSet = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice bluetoothDevice :pairedDevicesSet){
            boolean exist = false;
            for(BluetoothDevice bluetoothDevice2 :btDevices){
                if(bluetoothDevice.getAddress().equals(bluetoothDevice2.getAddress())){
                    exist = true;
                }
            }
            if(!exist){
                btDevices.add(bluetoothDevice);
            }
        }

        btDeviceListAdapter = new BTDeviceListAdapter(this,btDevices,onClickDevice);
        listViewDevices.setAdapter(btDeviceListAdapter);

    }



    private BTDeviceListClickListener onClickDevice = new BTDeviceListClickListener() {
        @Override
        public void onClick(BluetoothDevice bluetoothDevice) {
            //Toast.makeText(getApplicationContext(),""+bluetoothDevice.getName(),Toast.LENGTH_LONG).show();
            setUiProgressAndConnecting(bluetoothDevice);
        }
    };



    /*********************************************************************************************/
    /****************************           USER ACTION END             **************************/
    /*********************************************************************************************/






































    /*********************************************************************************************/
    /****************************           BLUETOOTH START             **************************/
    /*********************************************************************************************/

    interface BluetoothSendResult{
        void onResult(boolean sent);
    }


    private ConnectThread connectThread = null;
    private BluetoothSocket bluetoothSocket = null;




    //connection after selection a device

    private void setUiProgressAndConnecting(final BluetoothDevice bluetoothDevice){
        textViewProgress.setText("Connecting");


        // closing previous connection
        if(connectThread != null){
            if(connectThread.isAlive()) {
                Log.d(TAG, "connectThread.isAlive()[setUiProgressAndConnecting]");
                connectThread.cancel();

                new Thread() {
                    @Override
                    public void run() {
                        while (connectThread.isAlive()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
                Log.d(TAG, "not connectThread.isAlive()[setUiProgressAndConnecting]");
            }
            else{
                Log.d(TAG, "connectThread is not isAlive()[setUiProgressAndConnecting]");
            }
        }


        Log.d(TAG,"create new ConnectThread(bluetoothDevice)");
        connectThread = new ConnectThread(bluetoothDevice);


        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.right_to_left_out);
        layoutBtDeviceList.startAnimation(animationOut);

        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutBtDeviceList.setVisibility(View.GONE);
                Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.up_to_down_in);
                layoutProgress.startAnimation(animationIn);
                layoutProgress.setVisibility(View.VISIBLE);

                animationIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        connectThread.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ui = UI.CONNECTING;
    }







    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.mainLayout), "Fail to open connection.", Snackbar.LENGTH_LONG);
                snackbar.show();
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.mainLayout), "Fail to close connection.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            if(cancelConnection)
            {
                cancel();
                return;
            }
            manageMyConnectedSocket(mmSocket);
            while(bluetoothSocket.isConnected() ){
                try {
                    Thread.sleep(100);
                    if(bluetoothAdapter.getProfileConnectionState(BluetoothAdapter.STATE_CONNECTED) == BluetoothAdapter.STATE_CONNECTED ){
                        Log.e(TAG, "bluetoothAdapter STATE_CONNECTED");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e(TAG, "Socket Closed");
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.mainLayout), "Bluetooth disconnected.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.mainLayout), "Fail to close connection.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }






    public void manageMyConnectedSocket(BluetoothSocket mmSocket){
        changeUiConnectingToSwitches();
        bluetoothSocket = mmSocket;

    }





    private class  SendThread extends Thread{
        private BluetoothSendResult bluetoothSendResult;
        private String text;

        public SendThread(BluetoothSendResult bluetoothSendResult,String text) {
            this.bluetoothSendResult = bluetoothSendResult;
            this.text = text;
        }


        @Override
        public void run(){
            if(bluetoothSocket.isConnected()){
                try {
                    OutputStream outputStream = bluetoothSocket.getOutputStream();
                    outputStream.write((text+'\0').getBytes());
                    bluetoothSendResult.onResult(true);
                } catch (IOException e) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.mainLayout), "Fail to send command.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    e.printStackTrace();
                    bluetoothSendResult.onResult(false);
                }
            }
        }



    }





    /*********************************************************************************************/
    /****************************           BLUETOOTH END              **************************/
    /*********************************************************************************************/































































}
