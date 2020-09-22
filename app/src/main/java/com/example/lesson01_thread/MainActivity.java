package com.example.lesson01_thread;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Handler mHandler;
    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.getData().getString("COMMAND")){
                    case "MODULE_5":
                        Log.d("Chia het cho 5", msg.getData().getInt("A") + "");
                        break;
                    case "MODULE_3":
                        Log.d("Chia het cho 3", msg.getData().getInt("A") + "");
                        break;
                }
            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tang();
            }
        });
    }

    void tang(){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 10000; i++){
                    a += 1;
                    if(a % 5 == 0){
                        Message msg = mHandler.obtainMessage();
                        Bundle b = new Bundle();
                        b.putString("COMMAND", "MODULE_5");
                        b.putInt("A", a);
                        msg.setData(b);

                        mHandler.sendMessage(msg);
                    }

                    if(a % 3 == 0){
                        Message msg = mHandler.obtainMessage();
                        Bundle b = new Bundle();
                        b.putString("COMMAND", "MODULE_3");
                        b.putInt("A", a);
                        msg.setData(b);

                        mHandler.sendMessage(msg);
                    }
                }


                giam(); //callback
            }
        });
        t.start();
        Log.d("","f");
    }

    void giam(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 10000; i++){
                    a -= 1;
                }
                Log.d("Gia tri a = ", String.valueOf(a));
            }
        });
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}