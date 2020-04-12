package com.mobdev.hellolivedata.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Singleton to manage Log (Memory) Storage
 */
public class LogDescriptorManager {

	private static final String TAG = "LogDescriptorManager";

	private List<LogDescriptor> logList = null;

	private List<LogDataListener> listenerList = null;

	/*
	 * The instance is static so it is shared among all instances of the class. It is also private
	 * so it is accessible only within the class.
	 */
	private static LogDescriptorManager instance = null;

	/*
	 * The constructor is private so it is accessible only within the class.
	 */
	private LogDescriptorManager(){
		this.logList = new ArrayList<>();
		this.listenerList = new ArrayList<>();
	}

	public static LogDescriptorManager getInstance(){
		/*
		 * The constructor is called only if the static instance is null, so only the first time 
		 * that the getInstance() method is invoked.
		 * All the other times the same instance object is returned.
		 */
		if(instance == null){
			instance = new LogDescriptorManager();
		}
		return instance;
	}

	public void addLog(LogDescriptor log){
		this.logList.add(log);
		notifyListUpdated();
	}

	public void removeLog(LogDescriptor log){
		this.logList.remove(log);
		notifyListUpdated();

	}

	public List<LogDescriptor> getLogList(){
		return this.logList;
	}

	private void notifyListUpdated() {
		for(int i=0; i<this.listenerList.size(); i++){
			if(this.listenerList.get(i) != null)
				this.listenerList.get(i).onListChanged(this.logList);
		}
	}

	public void requestLogUpdates(LogDataListener listener) {
		if(listener != null) {
			this.listenerList.add(listener);

			//Immediatelly notify the new Listener with the existing List
			listener.onListChanged(this.logList);

			Log.d(TAG, "New Log Listener Added !");
		}
		else
			Log.e(TAG, "Error adding Log Listener ! listener = null !");
	}

	public void removeLogUpdates(LogDataListener listener) {

		if(listener != null) {
			this.listenerList.add(listener);
			Log.d(TAG, "Log Listener Removed !");
		}
		else
			Log.e(TAG, "Error removing Log Listener ! listener = null !");
	}

	public static LogDescriptor createRandomLogDescriptor() {
		Random random = new Random();

		double x0 = 44.766992;
		double y0 = 10.310035;
		int radius = 1000;

		// Convert radius from meters to degrees
		double radiusInDegrees = radius / 111000f;

		double u = random.nextDouble();
		double v = random.nextDouble();
		double w = radiusInDegrees * Math.sqrt(u);
		double t = 2 * Math.PI * v;
		double x = w * Math.cos(t);
		double y = w * Math.sin(t);

		// Adjust the x-coordinate for the shrinking of the east-west distances
		double new_x = x / Math.cos(y0);

		double foundLongitude = new_x + x0;
		double foundLatitude = y + y0;

		int number = random.nextInt((1000) + 1);

		return new LogDescriptor(foundLatitude, foundLongitude, "RANDOM_LOG", ""+ (double) number);
	}
}

