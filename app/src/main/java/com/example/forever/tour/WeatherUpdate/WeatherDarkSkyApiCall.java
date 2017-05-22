package com.example.forever.tour.WeatherUpdate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Forever on 4/28/2017.
 */

public interface WeatherDarkSkyApiCall {

    @GET
    Call<WeatherDarkSkyModel> getWeatherData(
            @Url String url
    );
}
