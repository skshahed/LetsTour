package com.example.forever.tour.WeatherUpdate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forever.tour.R;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Forever on 4/20/2017.
 */

public class WeatherAdapter extends ArrayAdapter<WeatherDataModel> {

    private Context context;
    private ArrayList<WeatherDataModel> weatherDataModels;
    private boolean isFormatCelsius;
    private WeatherIcon weatherIconName = new WeatherIcon();
   // private ConverterClass converterClass = new ConverterClass();
   private ConverterClass converterClass;

    public WeatherAdapter(@NonNull Context context, @NonNull ArrayList<WeatherDataModel> objects, boolean isFormatCelsius) {
        super(context, R.layout.forecast_layout, objects);
        this.context = context;
        this.weatherDataModels = objects;
        this.isFormatCelsius = isFormatCelsius;//if weather format celsius = false it means weather format will be fahrenheit
    }

    class ViewHolder{
        TextView dateTV, highTempTV, lowTempTV;
        WeatherIconView weatherIcon;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;
        if (convertView ==null){
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.forecast_layout,parent,false);

            holder.dateTV = (TextView) convertView.findViewById(R.id.forecastDayName);
            holder.highTempTV = (TextView) convertView.findViewById(R.id.forecastDayHighTemp);
            holder.lowTempTV = (TextView) convertView.findViewById(R.id.forecastDayLowTemp);
            holder.weatherIcon = (WeatherIconView) convertView.findViewById(R.id.forecastIcon);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

       // String icon = String.valueOf(weatherIconName.getIcon(weatherDataModels.get(position).getIconName()));

        converterClass = new ConverterClass();
        String icon = holder.weatherIcon.getResources().getString(weatherIconName.getIcon(weatherDataModels.get(position).getIconName()));

        String yesterdayDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date(((new Date()).getTime() - 86400000)));
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String tomorrowDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date(((new Date()).getTime() + 86400000)));

        if (currentDate.equals(weatherDataModels.get(position).getWeatherDay())){
            holder.dateTV.setText("Today");
        }
        else if (tomorrowDate.equals(weatherDataModels.get(position).getWeatherDay())){
            holder.dateTV.setText("Tomorrow");
        }else if (yesterdayDate.equals(weatherDataModels.get(position).getWeatherDay())){
            holder.dateTV.setText("Yesterday");
        }
        else {
            holder.dateTV.setText(weatherDataModels.get(position).getWeatherDay());
        }

       /* for (int i=0;i<weatherDataModels.size();i++){
        //holder.dateTV.setText(today);
            if(i==0){
                holder.dateTV.setText("Today");
            }else if (i==1){
                holder.dateTV.setText("Tomorrow");
            }else {
                holder.dateTV.setText(weatherDataModels.get(position).getWeatherDay());
            }
        }*/
        holder.weatherIcon.setIconResource(icon);

       // holder.weatherIcon.setImageResource(iconSelector.getIconName(weatherForecast.get(position).getWeatherId(),true));

        if (isFormatCelsius == true){
            holder.highTempTV.setText(converterClass.convertFahrenToCel(weatherDataModels.get(position).getHighTemp())+"\u2103");
            holder.lowTempTV.setText(converterClass.convertFahrenToCel(weatherDataModels.get(position).getLowTemp())+"\u2103");
        }else if (isFormatCelsius == false){
            holder.highTempTV.setText(converterClass.convertFahrenheit(weatherDataModels.get(position).getHighTemp())+"\u2109");
            holder.lowTempTV.setText(converterClass.convertFahrenheit(weatherDataModels.get(position).getLowTemp())+"\u2109");
        }
        holder.weatherIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = weatherDataModels.get(position).getCityName();
                String apparentTemp = weatherDataModels.get(position).getApparentTemp();
                String weatherDate = holder.dateTV.getText().toString();
                String highTemp = holder.highTempTV.getText().toString();
                String lowTemp = holder.lowTempTV.getText().toString();
                String weatherSummary = weatherDataModels.get(position).getWeatherSummary();
                String humidity = weatherDataModels.get(position).getHumidity();
                String sunrise = weatherDataModels.get(position).getSunrise();
                String sunset = weatherDataModels.get(position).getSunset();
                String dewPoint = weatherDataModels.get(position).getDewPoint();
                String windSpeed = weatherDataModels.get(position).getWindSpeed();
                String cloudCover = weatherDataModels.get(position).getCloudCover();

                holder.weatherIcon.buildDrawingCache();
                Bitmap icon= holder.weatherIcon.getDrawingCache();

                final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_DarkActionBar);

                dialog.setContentView(R.layout.weather_details_popup);
                //dialog.setTitle(" Weather Forecasting Details ");

                ImageView iconSet = (ImageView) dialog.findViewById(R.id.showIcon);
                // TextView descrip         = (TextView) dialog.findViewById(R.id.showDescp);
                TextView showDateTV         = (TextView) dialog.findViewById(R.id.showDate);
                TextView temperatureTV         = (TextView) dialog.findViewById(R.id.showDayTemp);
                TextView maxTempTV         = (TextView) dialog.findViewById(R.id.showMax);
                TextView minTempTV         = (TextView) dialog.findViewById(R.id.showMin);
                TextView summaryTV         = (TextView) dialog.findViewById(R.id.weatherSummary);
                TextView humidityTV         = (TextView) dialog.findViewById(R.id.showHumidity);
                TextView sunriseTV         = (TextView) dialog.findViewById(R.id.sunrise);
                TextView sunsetTV         = (TextView) dialog.findViewById(R.id.sunset);
                TextView dewPointTV         = (TextView) dialog.findViewById(R.id.showDewPoint);
                TextView cloudCoverTV         = (TextView) dialog.findViewById(R.id.showCloudCover);
                TextView windSpeedTV         = (TextView) dialog.findViewById(R.id.showWindSpeed);

                Button btnBack       = (Button) dialog.findViewById(R.id.cancel);

                iconSet.setImageBitmap(icon);
                // descrip.setText(description);
                if (isFormatCelsius == true){
                    temperatureTV.setText(converterClass.convertFahrenToCel(apparentTemp)+"\u2103");
                    dewPointTV.setText(converterClass.convertFahrenToCel(dewPoint)+"\u2103");
                }
                else if (isFormatCelsius == false){
                    temperatureTV.setText(converterClass.convertFahrenheit(apparentTemp)+"\u2109");
                    dewPointTV.setText(converterClass.convertFahrenheit(dewPoint)+"\u2109");
                }
                showDateTV.setText(weatherDate+" In "+cityName);

                maxTempTV.setText(highTemp);
                minTempTV.setText(lowTemp);
                summaryTV.setText(weatherSummary);
                humidityTV.setText((converterClass.getPercentage(humidity))+"\u0025");
                sunriseTV.setText(converterClass.getTime(sunrise));
                sunsetTV.setText(converterClass.getTime(sunset));
                cloudCoverTV.setText((converterClass.getPercentage(cloudCover))+"\u0025");
                windSpeedTV.setText(converterClass.convertMilesToKm(windSpeed)+" Km/h");
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //dialog.setCanceledOnTouchOutside(true);

                dialog.show();

            }

        });
        return convertView;
    }

}
