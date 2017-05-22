package com.example.forever.tour.GooglePlacePicker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forever.tour.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacePickerFragment extends Fragment {
    private LocationRequest locationRequest;
    private Button placePickerBtn;
    private View inflatedView;
    private TextView showAddressTV;
    private ImageView mImageView;
    private GoogleApiClient mGoogleApiClient;
    private final int REQUEST_CODE_PLACEPICKER = 1;


    public PlacePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_place_picker, container, false);
        }
        placePickerBtn = (Button) inflatedView.findViewById(R.id.placePicker);
       // nearRestuarentBtn = (Button) v.findViewById(R.id.searchNearBy);
        showAddressTV = (TextView) inflatedView.findViewById(R.id.nearByTitle);
        mImageView= (ImageView) inflatedView.findViewById(R.id.showPlaceImage);

        /*if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }*/

        placePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPlacePickerActivity();
            }
        });


        return inflatedView;
    }

    /* @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableLayoutPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

    }*/

/*
 private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            mImageView.setImageBitmap(placePhotoResult.getBitmap());
        }
    };
*/

    /*
     * Load a bitmap from the photos API asynchronously
     * by using buffers and result callbacks.
     */
   /* private void placePhotosAsync() {
        final String placeId = "ChIJrTLr-GyuEmsRBfy61i59si0"; // Australian Cruise Group
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {


                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getScaledPhoto(mGoogleApiClient, mImageView.getWidth(),
                                            mImageView.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });
    }
*/

    private void startPlacePickerActivity() {

        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();


        try {
            Intent intent = intentBuilder.build(getActivity());
           startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // int RESULT = getActivity().RESULT_OK;

        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == getActivity().RESULT_OK){
            displaySelectedPlace(data);
        }
    }

    private void displaySelectedPlace(Intent data) {

       // Place placeSelected = PlacePicker.getPlace(data, getActivity());
        Place placeSelected = PlacePicker.getPlace(getActivity(), data);
        String websitePath = "No Web Address Found";

       // boolean isCafe = false;
       // boolean isCafe = true;
        //placePhotosAsync();
        /*for (int i : placeSelected.getPlaceTypes()){
            if (i == Place.TYPE_CAFE){
                isCafe = true;
                break;
            }
        }*/
        //if (isCafe) {

           // String name = placeSelected.getName().toString();
        try {
            String addess = placeSelected.getAddress().toString();
            String phoneNumber = placeSelected.getPhoneNumber().toString();
            //Linkify.addLinks(phoneNumber, Linkify.PHONE_NUMBERS);
            showAddressTV.setAutoLinkMask(Linkify.ALL);
            if (placeSelected.getWebsiteUri() != null) {
                websitePath = String.valueOf(placeSelected.getWebsiteUri());
                //URI uri = new URI(whateverYourAddressStringIs);
                websitePath =  websitePath.replace("http://","").replace("https://","");
            }

            float rating = placeSelected.getRating();
            //showAddressTV.setBackgroundColor(Color.parseColor("#991bcf27"));
            showAddressTV.setBackgroundResource(R.drawable.place_picker_text);
            showAddressTV.setText(addess + "\n" + websitePath + "\n" + phoneNumber);
        }catch (Exception e) {
            e.printStackTrace();
        }
       /* }
        else{
            Toast.makeText(getActivity(), "Select cafe", Toast.LENGTH_SHORT).show();
        }*/
    }

  /*  @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create()
                .setInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(), "Hi Place", Toast.LENGTH_SHORT).show();

    }*/
}
