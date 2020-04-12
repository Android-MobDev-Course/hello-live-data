package com.mobdev.hellolivedata.data;

import java.util.List;

/**
 * Created by Marco Picone picone.m@gmail.com on 12,April,2020
 * Mobile System Development - University Course
 */
public interface LogDataListener {
    void onListChanged(List<LogDescriptor> logDescriptor);
}
