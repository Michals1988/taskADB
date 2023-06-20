package com.example.task;

public class TvEvents {

    private String eventId;
    private String programName;
    private int channelIdEvents;
    private long startTime;
    private long endTime;
    private String imageEvent;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getChannelIdEvents() {
        return channelIdEvents;
    }

    public void setChannelIdEvents(int channelIdEvents) {
        this.channelIdEvents = channelIdEvents;
    }

    public String getImageEvent() {
        return imageEvent;
    }

    public void setImageEvent(String imageEvent) {
        this.imageEvent = imageEvent;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public TvEvents() {
        this.programName = "Brak nazwy programu";
        this.startTime = 0;
        this.endTime = 0;
        this.channelIdEvents = 0;
        this.eventId = "0";
        this.imageEvent="";
    }

    public TvEvents(String eventId, String programName, int channelIdEvents, long startTime, long endTime, String imageEvent) {
        this.eventId = eventId;
        this.programName = programName;
        this.channelIdEvents = channelIdEvents;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageEvent = imageEvent;
    }
}
