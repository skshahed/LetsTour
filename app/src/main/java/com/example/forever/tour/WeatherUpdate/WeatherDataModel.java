package com.example.forever.tour.WeatherUpdate;

/**
 * Created by Forever on 4/19/2017.
 */

public class WeatherDataModel {
    private String cityName;
    private String apparentTemp;
    private String weatherDay;
    private String highTemp;
    private String lowTemp;
    private String iconName;

    private String weatherSummary;
    private String humidity;
    private String sunrise;
    private String sunset;
    private String dewPoint;
    private String windSpeed;
    private String cloudCover;


    //all constructor


    public WeatherDataModel(String cityName, String apparentTemp, String weatherDay, String highTemp, String lowTemp, String iconName, String weatherSummary, String humidity, String sunrise, String sunset, String dewPoint, String windSpeed, String cloudCover) {
        this.cityName = cityName;
        this.apparentTemp = apparentTemp;
        this.weatherDay = weatherDay;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.iconName = iconName;
        this.weatherSummary = weatherSummary;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.dewPoint = dewPoint;
        this.windSpeed = windSpeed;
        this.cloudCover = cloudCover;
    }

    public WeatherDataModel(String weatherDay, String highTemp, String lowTemp, String iconName) {
        this.weatherDay = weatherDay;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.iconName = iconName;
    }

    public WeatherDataModel() {
    }


    // all getter and setter method


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getApparentTemp() {
        return apparentTemp;
    }

    public void setApparentTemp(String apparentTemp) {
        this.apparentTemp = apparentTemp;
    }

    public String getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherDay(String weatherDay) {
        this.weatherDay = weatherDay;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getWeatherSummary() {
        return weatherSummary;
    }

    public void setWeatherSummary(String weatherSummary) {
        this.weatherSummary = weatherSummary;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(String cloudCover) {
        this.cloudCover = cloudCover;
    }
}
