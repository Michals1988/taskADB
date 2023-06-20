package com.example.task;

import java.util.ArrayList;
import java.util.Date;

public class TvChannel {
    private int channelId;
    private long channelNumber;
    private String channelName;
    private String channelLogo;


    public int getChannelID() {
        return channelId;
    }

    public void setChannelID(int channelId) {
        this.channelId = channelId;
    }

    public long getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(long channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelLogo() {
        return channelLogo;
    }

    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }


    public TvChannel() {
        this.channelId = 0;
        this.channelNumber = 0;
        this.channelName = "Brak kanału";
        this.channelLogo = "Brak logo";

    }

    public TvChannel(int channelId, int channelNumber, String channelName, String channelLogo) {
        this.channelId = channelId;
        this.channelNumber = channelNumber;
        this.channelName = channelName;
        this.channelLogo = channelLogo;
    }


}