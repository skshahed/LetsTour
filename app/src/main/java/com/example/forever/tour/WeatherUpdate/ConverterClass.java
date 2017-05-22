package com.example.forever.tour.WeatherUpdate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Forever on 4/22/2017.
 */

public class ConverterClass {

    public String convertFahrenheit(String fahrenheit){
        //return ((fahrenheit - 32)*5/9);
        return new DecimalFormat("##.#").format(Float.parseFloat(fahrenheit));
    }

    public String convertFahrenToCel(String fahrenheit){
        //return ((fahrenheit - 32)*5/9);
        return new DecimalFormat("##.#").format((Float.parseFloat(fahrenheit)-32)*5/9);
    }

    public String convertCelToFahren(String celsius){
        //return ((celsius*9)/5) + 32;
        return new DecimalFormat("##.#").format(((Float.parseFloat(celsius)*9)/5)+32);
    }

    public String convertKelvinToCel(String kelvin){
        //return (kelvin - 273);
        return new DecimalFormat("##.#").format(Float.parseFloat(kelvin)-273);
    }

    public String convertKelvinToFahren(String kelvin){
        return new DecimalFormat("##.#").format(Float.parseFloat(kelvin)-459);
    }

    public String getTime(String time){
        long convert = Long.parseLong(time);
        //String times = new SimpleDateFormat("hh:mm a").format(new Date(convert * 1000));
        return new SimpleDateFormat("hh:mm a").format(new Date(convert * 1000));
    }

    public String getPercentage(String point){
        return new DecimalFormat("##").format(Float.parseFloat(point)*100);
    }

    public String convertMilesToKm(String miles){
        return new DecimalFormat("##").format(Float.parseFloat(miles)*1.609344);
    }
}
