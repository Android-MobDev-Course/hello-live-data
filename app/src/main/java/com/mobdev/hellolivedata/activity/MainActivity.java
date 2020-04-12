package com.mobdev.hellolivedata.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobdev.hellolivedata.viewmodel.MyViewModel;
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

    private MyViewModel myViewModel = null;

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

        setupRandomNumberViewModel();

        setupLogViewModel();

    }

    private void init(){

        //Retrieve UI Components
        myTextView = (TextView)findViewById(R.id.myTextView);
        logListTextView = (TextView)findViewById(R.id.logListTextView);
        myButton = (Button) findViewById(R.id.myButton);
        addLogButton = (Button) findViewById(R.id.addLogButton);

        //Initialize Random object
        this.random = new Random();

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTextViewValue = String.format(Locale.ITALY,"Value: %d", random.nextInt(2000));
                myViewModel.getCurrentValue().setValue(newTextViewValue);
            }
        });

        addLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add random Log to the LogDescriptorManager
                LogDescriptorManager.getInstance().addLog(LogDescriptorManager.createRandomLogDescriptor());
            }
        });
    }

    private void setupRandomNumberViewModel(){

        // Get the ViewModel.
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {

                Log.d(TAG, "Observer Data has Changed ! New Value: " + newName);

                // Update the UI, in this case, a TextView.
                myTextView.setText(newName);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        myViewModel.getCurrentValue().observe(this, nameObserver);

    }

    private void setupLogViewModel(){
        LogListLiveData.getInstance().observe(this, updatedLogList -> {
            Log.d(TAG, "LogList Updated ! New List Size: " + updatedLogList.size());
            String newTextViewValue = String.format(Locale.ITALY,"List Sie: %d", updatedLogList.size());
            logListTextView.setText(newTextViewValue);
        });
    }
}
