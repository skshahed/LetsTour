package com.example.forever.tour.GoogleNearby;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Forever on 5/10/2017.
 */

public interface NearbyGoogleApiCall {

    @GET
    Call<NearbyGoogleModel> getNearbyGoogleData(
            @Url String url
    );
}
