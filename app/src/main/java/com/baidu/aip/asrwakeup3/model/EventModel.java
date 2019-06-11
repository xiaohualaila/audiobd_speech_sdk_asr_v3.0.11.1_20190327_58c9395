package com.baidu.aip.asrwakeup3.model;


import com.baidu.aip.asrwakeup3.event.IBus;

public class EventModel implements IBus.IEvent{

    public String value;


    public EventModel(String value) {

        this.value = value;
    }

    @Override
    public int getTag() {
        return 10;
    }
}
