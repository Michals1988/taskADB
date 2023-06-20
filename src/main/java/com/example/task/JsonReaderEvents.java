package com.example.task;

import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class JsonReaderEvents {


    public long dataStartTimeChange(String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        ZonedDateTime originalDateStartTime = ZonedDateTime.parse(startTime, formatter);


        LocalDate currentDate = LocalDate.now();

        LocalDate newDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth());

        ZonedDateTime newDateTime = ZonedDateTime.of(newDate, originalDateStartTime.toLocalTime(), originalDateStartTime.getZone());

        String newDateTimeString = formatter.format(newDateTime);
        //System.out.println("Nowa data: " + newDateTimeString);

        return newDateTime.toInstant().toEpochMilli();
    }

    public long dataEndTimeChange(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        ZonedDateTime originalDateStartTime = ZonedDateTime.parse(startTime, formatter);
        ZonedDateTime originalDateEndTime = ZonedDateTime.parse(endTime, formatter);

        LocalDate currentDate = LocalDate.now();
        LocalDate newDate;

        if (originalDateStartTime.getDayOfMonth() != originalDateEndTime.getDayOfMonth()) {
            newDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth() + 1);
        } else {
            newDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth());
        }

        ZonedDateTime newDateTime = ZonedDateTime.of(newDate, originalDateEndTime.toLocalTime(), originalDateEndTime.getZone());

        return newDateTime.toInstant().toEpochMilli();
    }


    public ArrayList<TvEvents> read(String jsonPath, int channelIdFromController) {
        JSONParser parser = new JSONParser();
        String path = jsonPath + "/events.json";
        ArrayList<TvEvents> tvEventsArrayList = new ArrayList<>();


        try {


            // Wczytaj plik JSON
            Object obj = parser.parse(new FileReader(path));

            // Konwertuj obiekt na obiekt JSONObject
            JSONObject jsonObject = (JSONObject) obj;

            // Pobierz tablicę "data"
            JSONArray dataArray = (JSONArray) jsonObject.get("data");


            // Pobierz pierwszy obiekt z tablicy "data"
            for (Object data : dataArray) {
                JSONObject dataObject = (JSONObject) data;

                // Odczytaj wartości z obiektu
                String id = (String) dataObject.get("id");
                String channelId = (String) dataObject.get("channelId");
                String name = (String) dataObject.get("name");
                String startTime = (String) dataObject.get("startTime");
                String endTime = (String) dataObject.get("endTime");

                String url = null;

                try {
                    JSONArray picturesArray = (JSONArray) dataObject.get("pictures");
                    if (picturesArray != null) {
                        JSONObject logoObject = (JSONObject) picturesArray.get(0);
                        url = (String) logoObject.get("url");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Long StartTime = dataStartTimeChange(startTime);
                long EndTime = dataEndTimeChange(startTime, endTime);

                if (Integer.parseInt(channelId) == channelIdFromController) {
                    TvEvents tvEvent = new TvEvents();
                    tvEvent.setEventId(id);
                    tvEvent.setImageEvent(url);
                    tvEvent.setChannelIdEvents(Integer.parseInt(channelId));
                    tvEvent.setStartTime(StartTime);
                    tvEvent.setEndTime(EndTime);
                    tvEvent.setProgramName(name);


                    tvEventsArrayList.add(tvEvent);

//                    //Wyświetl odczytane wartości
//                    System.out.println("id: " + id);
//                    System.out.println("channelID: " + channelId);
//                    System.out.println("name: " + name);
//                    System.out.println("url: " + url);
//                    System.out.println("startTime: " + startTime);
//                    System.out.println("endTime: " + endTime);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tvEventsArrayList;
    }

    public ArrayList<TvEvents> sortEventsByStartTime(ArrayList<TvEvents> oneChannelEventList) {
        Collections.sort(oneChannelEventList, Comparator.comparingLong(TvEvents::getStartTime));
        return oneChannelEventList;
    }


    public TvEvents findCurrentEvent(ArrayList<TvEvents> eventList) {
        TvEvents tvEvent = new TvEvents();

        for (TvEvents tvEventsFromArray : eventList) {
            Date currentTime = new Date(System.currentTimeMillis());
            long currentTimeMillis = currentTime.getTime();

            if ((tvEventsFromArray.getStartTime() < currentTimeMillis) && (tvEventsFromArray.getEndTime() > currentTimeMillis)) {
                tvEvent = tvEventsFromArray;
            }
        }

        return tvEvent;
    }
}
