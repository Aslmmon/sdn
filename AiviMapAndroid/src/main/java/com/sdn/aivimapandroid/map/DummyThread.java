package com.sdn.aivimapandroid.map;

public class DummyThread implements Runnable {
    private volatile int value;
    private Boolean flag = true;

    @Override
    public void run() {
        value = 0;
    }

    public int getValue() {
        return 0;
    }
}