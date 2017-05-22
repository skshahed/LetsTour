package com.example.forever.tour.GoogleNearby;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.forever.tour.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyMapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location myLocation;
    private Geocoder geocoder;
    private View inflatedView;
    private Double currentLat = null, currentLng = null;
    private List<Address> addressList;
    private NearbyGoogleApiCall nearbyGoogleApiCall;
    private NearbyGoogleModel nearbyGoogleModel;
    private String placeType;
    LatLng myPosition;
    private String city, country;
    boolean checkCurrentLoc = true;

    private Button restaurantBtn, cafeBtn, bankBtn, atmBtn;

    private String Url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&key=AIzaSyA98mEx8C6LvpEY99z87MbJr-IEjc_SA0Y";
    private String BASE_URL = "https://maps.googleapis.com/maps/";

    public NearbyMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (inflatedView==null) {
            inflatedView = inflater.inflate(R.layout.fragment_nearby_map, container, false);
        }
        restaurantBtn = (Button) inflatedView.findViewById(R.id.btnRestaurant);
        cafeBtn = (Button) inflatedView.findViewById(R.id.btnCafe);
        bankBtn = (Button) inflatedView.findViewById(R.id.btnBank);
        atmBtn = (Button) inflatedView.findViewById(R.id.btnATM);

       /* SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
      //  mapFragment.getMapAsync(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        geocoder = new Geocoder(getContext());


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void myCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (myLocation != null) {
            // Getting latitude of the current location
            double latitude = myLocation.getLatitude();

            // Getting longitude of the current location
            double longitude = myLocation.getLongitude();

            // Creating a LatLng object for the current location
            myPosition = new LatLng(latitude, longitude);

            currentLat = myLocation.getLatitude();
            currentLng = myLocation.getLongitude();

            try {

                addressList = geocoder.getFromLocation(myLocation.getLatitude(),myLocation.getLongitude(),1);
                city = addressList.get(0).getLocality();
                country = addressList.get(0).getCountryName();

                // String city = addressList.get(0).getLocality();
                // String country = addressList.get(0).getCountryName();
                //Toast.makeText(this, "City: "+city+", "+country, Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }


            /*mMap.addMarker(new MarkerOptions().position(latLng).title("City: "+ city +", "+country));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));*/

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0,120,0,0);


        //myCurrentLocation();
        // Add a marker in Sydney and move the camera
        LatLng defaultMapLoad = new LatLng(23.7516, 90.3943);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(defaultMapLoad).title("City: "+city+", "+country));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultMapLoad));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        restaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeType = "restaurant";
                //getRetrofit();
            }
        });

        cafeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeType = "cafe";
                getRetrofit();
            }
        });

        bankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeType = "bank";
                getRetrofit();
            }
        });

        atmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeType = "atm";
                getRetrofit();
            }
        });
    }

    public void getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nearbyGoogleApiCall = retrofit.create(NearbyGoogleApiCall.class);

        nearbyGoogleModel = new NearbyGoogleModel();

        // String placeType = "restaurant";
        myCurrentLocation();

        //currentLat = myPosition.latitude;
        //currentLng = myPosition.longitude;

        if (currentLat==null || currentLng == null){
            /***********  Value By Default *************/
            currentLng= 90.4789;
            currentLat= 23.4323;
        }

        String dynamicUrl = BASE_URL+"api/place/nearbysearch/json?location="+currentLat+","+currentLng+"&radius=500&type="+placeType+"&key=AIzaSyA98mEx8C6LvpEY99z87MbJr-IEjc_SA0Y";

        Call<NearbyGoogleModel> getNearbyGoogleData = nearbyGoogleApiCall.getNearbyGoogleData(dynamicUrl);

        getNearbyGoogleData.enqueue(new Callback<NearbyGoogleModel>() {
            @Override
            public void onResponse(Call<NearbyGoogleModel> call, Response<NearbyGoogleModel> response) {
                if (response.code() == 200){
                    nearbyGoogleModel = response.body();
                    //List<NearbyGoogleModel.Result> weatherDataList = nearbyGoogleModel.getResults();
                    //Toast.makeText(getActivity(), "Got API response: "+nearbyGoogleModel.getResults().size(), Toast.LENGTH_SHORT).show();

                    try {
                        mMap.clear();
                        // This loop will go through all the results and add marker on each location.
                        for (int i = 0; i < response.body().getResults().size(); i++) {
                            //Toast.makeText(NearByMapsActivity.this,"Size :"+ nearbyGoogleModel.getResults().size(), Toast.LENGTH_SHORT).show();
                            Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                            Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                            final String placeName = response.body().getResults().get(i).getName();
                            final String vicinity = response.body().getResults().get(i).getVicinity();
                            final MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(lat, lng);
                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding Title to the Marker
                            markerOptions.title(placeName + " : " + vicinity);
                            // Adding Marker to the Camera.
                            Marker m = mMap.addMarker(markerOptions);
                            // Adding colour to the marker
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            // move map camera
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                           /* mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                   Toast.makeText(getActivity(), placeName+": "+vicinity, Toast.LENGTH_SHORT).show();
                                    final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                                    dialog.show();

                                }
                            });*/
                        }
                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }


                }
                else {
                    Toast.makeText(getContext(), "Problem On API Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NearbyGoogleModel> call, Throwable t) {
                Toast.makeText(getContext(), "Network Connection Failed", Toast.LENGTH_SHORT).show();

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

        if (checkCurrentLoc) {

            double locationLatitude = location.getLatitude();
            double locationLongitude = location.getLongitude();
            LatLng latLng = new LatLng(locationLatitude, locationLongitude);
            checkCurrentLoc = false;
            try {

                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String city = addressList.get(0).getLocality();
                String country = addressList.get(0).getCountryName();
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("City: " + city + ", " + country));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                // String city = addressList.get(0).getLocality();
                // String country = addressList.get(0).getCountryName();
                //Toast.makeText(this, "City: "+city+", "+country, Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

       // Toast.makeText(getContext(), "Location Change Working", Toast.LENGTH_SHORT).show();

    }
}