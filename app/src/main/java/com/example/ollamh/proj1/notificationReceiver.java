package com.example.ollamh.proj1;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by carleneberry on 7/13/17.
 *
 */

public class notificationReceiver extends BroadcastReceiver {
    final private String API_KEY = "3b7b12e3bec57d6c";
    private String API_URL;
    private String severeWeather = "Severe Weather Alert";
    private int isAlert= 0;
    private String description="";
    private String weather="";
    private boolean isRain=false;

    @Override
    public void onReceive(Context context, Intent intent) {


        String city=intent.getStringExtra("city");
        String state=intent.getStringExtra("state");

        try {
            severeWeather=getWeatherAlert(state, city);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            weather=isRain(state, city);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(isRain==true){
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setContentTitle("It's Raining!")
                    .setContentText("Don't Forget Your Umbrella! ")
                    .setTicker("It's Raining!")
                    .setSmallIcon(R.drawable.ic_stat_name);

            notificationBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
            notificationBuilder.setAutoCancel(true);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, notificationBuilder.build());

        }

        if(isAlert>0) {
            String title = translate();
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setTicker("Severe Weather Alert")
                    .setSmallIcon(R.drawable.ic_stat_name);

            notificationBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
            notificationBuilder.setAutoCancel(true);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, notificationBuilder.build());
       }

    }


    protected String getWeatherAlert(String state, String city) throws JSONException {
        /// Using the JSON STRING from MainActivity, we can search for alerts & conditions
        JSONObject api = new JSONObject(MainActivity.JSON_STRING);
        severeWeather = api.getJSONObject("alerts").getString("type");
        isAlert = api.getJSONObject("alerts").getInt("alerts");
        description=api.getJSONObject("alerts").getString("message");
        return api.getJSONObject("alerts").getString("type");

    }

    protected String isRain(String state, String city) throws JSONException {
        // Using the JSON STRING from MainActivity, we can search for alerts & conditions
        JSONObject api = new JSONObject(MainActivity.JSON_STRING);
        weather = api.getJSONObject("conditions").getString("weather");
            if(weather.contains("Rain")){
                isRain=true;
            }
            if(weather.contains("Drizzle")){
                isRain=true;
            }
            if(weather.contains("Thunderstorm")){
                isRain=true;
            }
        return api.getJSONObject("conditions").getString("weather");

    }


    String translate() {

        String weather = severeWeather;
        switch (severeWeather) {
            case "HUR":
                weather="New Hurricane Local Statement";
                break;
            case "TOR":
                weather="Tornado Warning";
                break;
            case "TOW":
                weather="Tornado Watch";
                break;
            case "WRN":
                weather="Severe Thunderstorm Warning";
                break;
            case "SEW":
                weather="Severe Thunderstorm Watch";
                break;
            case "WIN":
                weather="Winter Weather Advisory";
                break;
            case "FLO":
                weather="Flood Warning";
                break;
            case "WAT":
                weather="Flood Watch";
                break;
            case "WND":
                weather="High Wind Advisory";
                break;
            case "HEA":
                weather="Heat Advisory";
                break;
            case "FOG":
                weather="Dense Fog Advisory";
                break;
            case "VOL":
                weather="Volcanic Activity Statement";
                break;
            case "FIR":
                weather="Fire Weather Advisory";
                break;
            case "HWW":
                weather="Hurricane Wind Warning";
                break;
            default: break;
        }


        return weather;
    }

}

