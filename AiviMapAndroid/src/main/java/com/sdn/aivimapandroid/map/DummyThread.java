package com.sdn.aivimapandroid.map;

public class DummyThread implements Runnable {
    private volatile int value;

    @Override
    public void run() {
        value = 2;
    }

    public int getValue() {
        return value;
    }
}