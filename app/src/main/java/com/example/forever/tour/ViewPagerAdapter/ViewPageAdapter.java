package com.example.forever.tour.ViewPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.forever.tour.GoogleNearby.NearbyMapFragment;
import com.example.forever.tour.GooglePlacePicker.PlacePickerFragment;
import com.example.forever.tour.WeatherUpdate.ForecastFragment;


/**
 * Created by Forever on 5/1/2017.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
            Log.d("Test", "onCreateView: ---------------------------testing ");
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    NearbyMapFragment nearbyMapFragment =new NearbyMapFragment();
                    return nearbyMapFragment;
                    //break;
                case 1:
                    PlacePickerFragment placePickerFragment =new PlacePickerFragment();
                    return placePickerFragment;


                case 2:
                    ForecastFragment forecastFragment   =new ForecastFragment();
                    return forecastFragment;

            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                   // return "Nearby";

                case 1:
                    //return "Find Place";

                case 2:
                   // return "Forecast";
            }
            return null;
        }
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            Log.d("Test", "onCreateView: ---------------------------testing ");
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /*@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d("Test", "onCreateView: ---------------------------testing ");
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            return rootView;
        }*/
    }

}
