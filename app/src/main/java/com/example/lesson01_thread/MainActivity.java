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

import java.util.Random;

/**
 * Viet chuong trinh thuc hien cac nhiem vu
 * 1. Tao mang a gom 100 phan tu tren thread main
 * 2. Tao 2 thread chan, le dem so so chan va so so le
 */
public class MainActivity extends AppCompatActivity {
    Handler mHandler;
    int a[];
    final int SIZE = 100;
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
                    case "THREAD_CHAN_FINISH":
                        Log.d("So so chan", msg.getData().getInt("SO_SO_CHAN") + "");
                        break;
                    case "THREAD_LE_FINISH":
                        Log.d("So so le", msg.getData().getInt("SO_SO_LE") + "");
                        break;
                }
            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayGeneration(SIZE);
            }
        });
    }

    void arrayGeneration(int size){
        a = new int[size];
        Random random = new Random();
        for(int i=0; i < size; i++){
            a[i] = random.nextInt(100);
        }

        threadChan();
        threadLe();
    }

    void threadChan(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                int count = 0;
                for(int i=0;i < SIZE;i++){
                    if(a[i] % 2 == 0){
                        count++;
                    }
                }

                //Send value to main thread
                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("COMMAND", "THREAD_CHAN_FINISH");
                bundle.putInt("SO_SO_CHAN", count);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        };

        thread.start();
    }

    void threadLe(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                int count = 0;
                for(int i=0;i < SIZE;i++){
                    if(a[i] % 2 == 1){
                        count++;
                    }
                }

                //Send value to main thread
                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("COMMAND", "THREAD_LE_FINISH");
                bundle.putInt("SO_SO_LE", count);
                msg.setData(bundle);

                mHandler.sendMessage(msg);
            }
        };

        thread.start();
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