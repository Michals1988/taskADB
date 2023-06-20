package com.example.task;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;


public class JsonReaderChannel {
    public ArrayList<TvChannel> read(String jsonPath) {
        JSONParser parser = new JSONParser();
        ArrayList<TvChannel> tvChannelList = new ArrayList<>();
        String path = jsonPath + "/channels.json";

        try {
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray dataArray = (JSONArray) jsonObject.get("data");

            for (Object data : dataArray) {
                JSONObject dataObject = (JSONObject) data;
                TvChannel tvChannel = new TvChannel();

                String id = (String) dataObject.get("id");
                String name = (String) dataObject.get("name");
                long number = (long) dataObject.get("number");

                JSONArray logosArray = (JSONArray) dataObject.get("logos");

                String url = null;
                if (logosArray.size() > 0) {
                    JSONObject logoObject = (JSONObject) logosArray.get(0);
                    url = (String) logoObject.get("url");
                }

                tvChannel.setChannelID(Integer.parseInt(id));
                tvChannel.setChannelName(name);
                tvChannel.setChannelNumber(number);
                tvChannel.setChannelLogo(url);

                tvChannelList.add(tvChannel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tvChannelList;
    }

    public ArrayList<String> channelList(String jsonPath) {
        JSONParser parser = new JSONParser();

        ArrayList<String> list = new ArrayList<>();
        String path = jsonPath + "/channels.json";

        try {
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray dataArray = (JSONArray) jsonObject.get("data");


            for (Object data : dataArray) {
                JSONObject dataObject = (JSONObject) data;

                String name = (String) dataObject.get("name");
                list.add(name);
                System.out.println(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}

