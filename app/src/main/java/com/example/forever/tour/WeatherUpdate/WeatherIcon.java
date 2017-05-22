package com.example.forever.tour.WeatherUpdate;


import com.example.forever.tour.R;

/**
 * Created by Forever on 5/2/2017.
 */

public class WeatherIcon {

    public int getIcon(String mIcon) {

        int iconId;

        if (mIcon.equals("clear-day")) {
            iconId = R.string.wi_day_sunny;

        } else if (mIcon.equals("clear-night")) {
            iconId = R.string.wi_night_clear;
        } else if (mIcon.equals("rain")) {
            iconId = R.string.wi_rain;
        } else if (mIcon.equals("snow")) {
            iconId = R.string.wi_snow;
        } else if (mIcon.equals("sleet")) {
            iconId = R.string.wi_sleet;
        } else if (mIcon.equals("wind")) {
            iconId = R.string.wi_windy;
        } else if (mIcon.equals("fog")) {
            iconId = R.string.wi_fog;
        } else if (mIcon.equals("cloudy")) {
            iconId = R.string.wi_cloudy;
        } else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.string.wi_day_cloudy;
        } else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.string.wi_night_partly_cloudy;
        } else {
            iconId = R.drawable.weather_demo;
        }
        return iconId;
    }
}
