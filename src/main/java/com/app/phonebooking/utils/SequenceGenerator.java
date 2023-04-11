package com.app.phonebooking.utils;

public class SequenceGenerator {

    private int currentId;

    public SequenceGenerator() {
        currentId = 0;
    }

    public synchronized int getNextId() {
        currentId++;
        return currentId;
    }

}

