package com.example.task;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    TvChannel tvChannel = new TvChannel();
    TvEvents tvEvent = new TvEvents();
    ArrayList<TvChannel> tvChannelList = new ArrayList<>();
    ArrayList<TvEvents> tvEventsList = new ArrayList<>();
    ArrayList<TvEvents> tvEventsListSorted = new ArrayList<>();
    String jsonPath;
    JsonReaderChannel channel = new JsonReaderChannel();
    JsonReaderEvents events = new JsonReaderEvents();
    Time time = new Time();

    @FXML
    private ListView<String> channelList;
    private ArrayList<String> arrayChannelList;

    @FXML
    private Label jsonPathLabel;

    @FXML
    private Label channelName;

    @FXML
    private Label currentEvent;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    ImageView channelLogo;


    @FXML
    protected void onJsonPath() {
        final DirectoryChooser eventsPathChooser = new DirectoryChooser();
        File file = eventsPathChooser.showDialog(null);
        jsonPath = file.getAbsolutePath().toString();
        jsonPathLabel.setText(jsonPath);
        arrayChannelList = channel.channelList(jsonPath);
        ObservableList<String> list = FXCollections.observableArrayList(arrayChannelList);
        channelList.setItems(list);

    }

    @FXML
    protected void onChoosenChannel() {
        channelName.setText(channelList.getSelectionModel().getSelectedItem().toString());
        tvChannelList = channel.read(jsonPath);
        int k = tvChannelList.size();

        for (int i = 0; i < k; i++) {
            if (channelName.getText().toString().equals(tvChannelList.get(i).getChannelName())) {
                tvChannel = tvChannelList.get(i);
                break;
            }
        }
        tvEventsList = events.read(jsonPath, tvChannel.getChannelID());

        if (tvEventsList.size() > 0) {
            tvEventsListSorted = events.sortEventsByStartTime(tvEventsList);
        }


        if (tvChannel.getChannelLogo() == null) {
            channelLogo.setVisible(false);
        } else {
            channelLogo.setVisible(true);
            try {
                BufferedImage bufferedImage = ImageIO.read(new URL(tvChannel.getChannelLogo()));

                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                channelLogo.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private Label clock;


    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), e -> {
        clock.setText(time.currentTime());
        TvEvents tvEventToShow = new TvEvents();
        String eventId = "";

        if (tvEventsListSorted.size() > 0) {
            tvEventToShow = events.findCurrentEvent(tvEventsListSorted);
        }

        if (tvEventToShow.getStartTime() != 0) {
            currentEvent.setText(tvEventToShow.getProgramName());
            SimpleDateFormat timeInMillis = new SimpleDateFormat("HH:mm");
            String timeString = timeInMillis.format(tvEventToShow.getStartTime());
            startTimeLabel.setText(timeString);
            timeString = timeInMillis.format(tvEventToShow.getEndTime());
            endTimeLabel.setText(timeString);
        }

        if (tvEventToShow.getChannelIdEvents() != tvChannel.getChannelID()) {
            currentEvent.setText("Brak programu");
            startTimeLabel.setText("00:00");
            endTimeLabel.setText("00:00");
        }

    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}