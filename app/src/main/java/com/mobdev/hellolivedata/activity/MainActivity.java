package com.mobdev.hellolivedata.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mobdev.hellolivedata.R;
import com.mobdev.hellolivedata.data.LogDescriptorManager;
import com.mobdev.hellolivedata.data.LogListLiveData;

import java.util.Locale;
import java.util.Random;

/**
 * Created by Marco Picone picone.m@gmail.com on 12,April,2020
 * Mobile System Development - University Course
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView myTextView = null;

    private TextView logListTextView = null;

    private Button myButton = null;

    private Button addLogButton = null;

    private Random random = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setupLogViewModel();

    }

    private void init(){

        //Retrieve UI Components
        logListTextView = (TextView)findViewById(R.id.logListTextView);
        addLogButton = (Button) findViewById(R.id.addLogButton);

        //Initialize Random object
        this.random = new Random();

        addLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add random Log to the LogDescriptorManager
                LogDescriptorManager.getInstance().addLog(LogDescriptorManager.createRandomLogDescriptor());
            }
        });
    }

    private void setupLogViewModel(){
        LogListLiveData.getInstance().observe(this, updatedLogList -> {
            Log.d(TAG, "LogList Updated ! New List Size: " + updatedLogList.size());
            String newTextViewValue = String.format(Locale.ITALY,"List Sie: %d", updatedLogList.size());
            logListTextView.setText(newTextViewValue);
        });
    }
}
