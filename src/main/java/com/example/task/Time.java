package com.example.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    private Date date;

    public String currentTime() {

        Date actualTime = new Date();
        String patternDate = "HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);

        return dateFormat.format(actualTime);
    }

}
