package com.example.forever.tour.WeatherUpdate;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forever.tour.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

//import net.sf.sprockets.widget.GooglePlaceAutoComplete;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private String Url = "https://api.darksky.net/forecast/afa952d7d412a4a7ccdb4b6da40c10b6/42.3601,71.0589?exclude=hourly";
    private String BASE_URL="https://api.darksky.net/";

    //GooglePlaces client = new GooglePlaces("AIzaSyA1HWBWKYl2nnpuC4ppu4Ki3s7b4nvHZPs");

    private boolean checkLoc = true;
    private Context context;
    private String date;
    private View inflatedView;
    private ListView forecastLV;
   // private SearchView citySearchSV;
    private Button searchBtn;
    private RadioGroup tempFormatRB;
    private RadioButton celsiusRB, fahrenRB;
    //private boolean temperatureFormatCelsius = true;
    private boolean temperatureFormat;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Geocoder geocoder;
    private String currentLat = null, currentLng = null,cityName;
    private List<Address> addresslist;


    private WeatherDarkSkyApiCall weatherDarkSkyApiCall;
    private WeatherDarkSkyModel weatherDarkSkyModel;
    //private ArrayList<WeatherDataModel> weatherDataModels = new ArrayList<WeatherDataModel>();
    private ArrayList<WeatherDataModel> weatherDataModels;

    private TextView searchResultTV,weatherPlaceTV;
    private static final int REQUEST_SELECT_PLACE = 1000;
    //private EditText searchResultET;
    //private ConverterClass converterClass;
    private ProgressBar progressBar;

    public ForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_forecast, container, false);
        // Inflate the layout for this fragment

        forecastLV = (ListView) inflatedView.findViewById(R.id.weatherForecastLV);
        //googlePlaceAutoComplete = (GooglePlaceAutoComplete) inflatedView.findViewById(R.id.citySearch);
        searchBtn = (Button) inflatedView.findViewById(R.id.searchBtn);
       // citySearchSV = (SearchView) inflatedView.findViewById(R.id.citySearch);
        tempFormatRB = (RadioGroup) inflatedView.findViewById(R.id.tempFormat);

        celsiusRB = (RadioButton) inflatedView.findViewById(R.id.tempCelsius);
        fahrenRB = (RadioButton) inflatedView.findViewById(R.id.tempFahrenheit);

        weatherPlaceTV = (TextView) inflatedView.findViewById(R.id.weatherPlace);
        searchResultTV = (TextView) inflatedView.findViewById(R.id.searchResult);
        progressBar = (ProgressBar) inflatedView.findViewById(R.id.dataProgress);
        forecastLV.setEmptyView(progressBar);

        //searchResultET = (EditText) inflatedView.findViewById(R.id.searchWeatherCity);

        // onLocationChanged(locationRequest.);

        return inflatedView;
    }

/*
 * Search Weather by city Name using Google PlaceAutocomplete Starts
 */
    private void findPlace() {
        try{
            Intent intent = new PlaceAutocomplete
                    .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(getActivity());
            startActivityForResult(intent,REQUEST_SELECT_PLACE);
        }catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {

                getSelectedPlace(data);
                //Place place = PlaceAutocomplete.getPlace(getActivity(), data);

                //this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.e("Tag",status.getStatusMessage());
                //this.onError(status);
            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(context, "No PLace Selected", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getSelectedPlace(Intent data) {
        Place place = PlaceAutocomplete.getPlace(getActivity(), data);
        Log.e("Tag","Place: "+place.getAddress()+", "+place.getLocale()+", "+place.getName());
        //weatherPlaceTV.setText("Weather At '"+place.getName()+"'");
        searchResultTV.setText(place.getName());
    }

    /*
    * Search Weather by city Name using Google PlaceAutocomplete Ends
    */


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        geocoder = new Geocoder(getContext());
        if (checkLoc==false){
            getRetrofit();
        }
        //getRetrofit();

    }

    public void getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherDarkSkyApiCall = retrofit.create(WeatherDarkSkyApiCall.class);




        if(celsiusRB.isChecked()){
            temperatureFormat = true;
        }else if(fahrenRB.isChecked()) {
            temperatureFormat=false;
        }

        searchResultTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPlace();
            }
        });
        /*searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPlace();
            }
        });
*/

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName = searchResultTV.getText().toString();
                //addresslist = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                    try {
                        addresslist = geocoder.getFromLocationName(cityName, 1);
                        if(addresslist.isEmpty()){
                            Toast.makeText(context, "Please Enter Search Area", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Address location = addresslist.get(0);
                            currentLat = String.valueOf(location.getLatitude());
                            currentLng = String.valueOf(location.getLongitude());
                            getRetrofit();
                            weatherPlaceTV.setText("Weather At '"+cityName+"'");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //getRetrofit();

            }
        });

        if (currentLat==null || currentLng == null){
            //************  Value By Default *************
            currentLng= String.valueOf(90.4789);
            currentLat= String.valueOf(23.4323);
        }

        //String lattd = String.valueOf(23.8103);
        //String lngtd = String.valueOf(90.4125);
        //String dynamicUrl = BASE_URL+"forecast/10cc53dcdd1811ae547734821cb09751/"+lattd+","+lngtd+"?exclude=hourly";

        String dynamicUrl = BASE_URL+"forecast/afa952d7d412a4a7ccdb4b6da40c10b6/"+currentLat+","+currentLng+"?exclude=hourly";
        Call<WeatherDarkSkyModel> getWeatherData = weatherDarkSkyApiCall.getWeatherData(dynamicUrl);

        getWeatherData.enqueue(new Callback<WeatherDarkSkyModel>() {
            @Override
            public void onResponse(Call<WeatherDarkSkyModel> call, Response<WeatherDarkSkyModel> response) {
                if(response.code() == 200) {
                    weatherDarkSkyModel = response.body();
                    //Toast.makeText(context, "Success: "+weatherDarkSkyModel.getDaily().getData().size(), Toast.LENGTH_SHORT).show();

                    weatherDataModels = new ArrayList<>();
                    List<WeatherDarkSkyModel.Datum> weatherDataList = weatherDarkSkyModel.getDaily().getData();
                    //List<WeatherDarkSkyModel.Datum> weatherDataList = weatherDarkSkyModel.getDaily();

                    weatherPlaceTV.setText("Weather At '"+cityName+"'");

                    for(WeatherDarkSkyModel.Datum weather : weatherDataList) {



                            int day = weather.getTime();

                            // String minimumTemp = new DecimalFormat("##").format(weather.getTemperatureMin());
                           // String cityName = ;

                            String approxTemp = String.valueOf(Math.floor(weather.getTemperatureMax()));
                            String maximumTemp = String.valueOf((weather.getTemperatureMax()));
                            String minimumTemp = String.valueOf((weather.getTemperatureMin()));


                            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date(day * 1000L));
                            String summary = weather.getSummary();
                            String humidity = String.valueOf(weather.getHumidity());
                            String sunrise = String.valueOf(weather.getSunriseTime());
                            String sunset = String.valueOf(weather.getSunsetTime());
                            String dewPoint = String.valueOf(weather.getDewPoint());
                            String windSpeed = String.valueOf(weather.getWindSpeed());
                            String cloudCover = String.valueOf(weather.getCloudCover());

                            String iconName = weather.getIcon();
                            weatherDataModels.add(new WeatherDataModel(cityName,approxTemp, date, maximumTemp, minimumTemp, iconName, summary, humidity, sunrise, sunset, dewPoint, windSpeed, cloudCover));
                    }


                    tempFormatRB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                            if(checkedId == R.id.tempCelsius){
                                temperatureFormat = true;
                                updateListView();
                            }
                            else if (checkedId == R.id.tempFahrenheit){
                                temperatureFormat = false;
                                updateListView();
                            }
                        }
                    });
                    updateListView();//update list view data
                }
                else {
                    Toast.makeText(context, "No API response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherDarkSkyModel> call, Throwable t) {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
               // progressBar.setText

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();

    }

    @Override
    public void onPause() {
        googleApiClient.disconnect();
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void updateListView(){


        if (weatherDataModels.size() > 0 ){
            WeatherAdapter adapter = new WeatherAdapter(context,weatherDataModels,temperatureFormat);
            forecastLV.setAdapter(adapter);
            //updateDayWeather(0);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = LocationRequest.create()
                .setInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(this, "changed", Toast.LENGTH_SHORT).show();
       // Toast.makeText(context, "Changed", Toast.LENGTH_SHORT).show();

        //currentLat =  String.valueOf(location.getLatitude());
        //currentLng =  String.valueOf(location.getLongitude());


        if (checkLoc){
            currentLat =  String.valueOf(location.getLatitude());
            currentLng =  String.valueOf(location.getLongitude());
            checkLoc = false;

            try {

                addresslist = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                cityName = addresslist.get(0).getLocality();
               // String country = addresslist.get(0).getCountryName();
               // weatherPlaceTV.setText("Weather At '"+cityName+"'");
                //Toast.makeText(context, "City: "+city+", "+country, Toast.LENGTH_SHORT).show();
                //searchResultTV.setText(city);

            } catch (IOException e) {
                e.printStackTrace();
            }

            getRetrofit();

        }

        //getRetrofit();

    }
}
