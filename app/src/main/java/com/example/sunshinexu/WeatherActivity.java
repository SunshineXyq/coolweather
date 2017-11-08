package com.example.sunshinexu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sunshinexu.gson.Forecast;
import com.example.sunshinexu.gson.Weather;
import com.example.sunshinexu.service.AutoUpdateService;
import com.example.sunshinexu.util.Utility;
import com.example.sunshinexu.util.httpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView title_update_time;
    private TextView degree_text;
    private TextView weather_info_text;
    private LinearLayout forecast_layout;
    private TextView aqi_text;
    private TextView pm25_text;
    private TextView comfort_text;
    private TextView car_wash_text;
    private TextView sport_text;
    private ImageView iv_bing_pic;
    private String weatherId;
    public SwipeRefreshLayout swipe_refresh;
    public DrawerLayout drawer_layout;
    private Button nav_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {     //版本号设置
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        initViews();
        initData();
    }

    private void initData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        String bing_pic = prefs.getString("bing_pic", null);
        if (bing_pic != null) {
            Glide.with(this).load(bing_pic).into(iv_bing_pic);
        } else {
            loadBingPic();
        }
        if (weatherString != null) {
            Weather weather = Utility.handWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            weatherId = getIntent().getStringExtra("weatherId");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });
        nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        httpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"图片下载失败",Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                edit.putString("bing_pic",bingPic);
                edit.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(iv_bing_pic);
                    }
                });
            }
        });
    }

    public void requestWeather(String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId
                + "&key=52708e50e9364dc8816e1b8e3822aef3";
        Log.d("url",weatherUrl);
        httpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        swipe_refresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && weather.status.equals("ok")) {
                            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this)
                                    .edit();
                            edit.putString("weather",responseText);
                            edit.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        swipe_refresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    private void initViews() {
        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        title_update_time = findViewById(R.id.title_update_time);
        degree_text = findViewById(R.id.degree_text);
        weather_info_text = findViewById(R.id.weather_info_text);
        forecast_layout = findViewById(R.id.forecast_layout);
        aqi_text = findViewById(R.id.aqi_text);
        pm25_text = findViewById(R.id.pm25_text);
        comfort_text = findViewById(R.id.comfort_text);
        car_wash_text = findViewById(R.id.car_wash_text);
        sport_text = findViewById(R.id.sport_text);
        iv_bing_pic = findViewById(R.id.iv_bing_pic);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary);
        drawer_layout = findViewById(R.id.drawer_layout);
        nav_button = findViewById(R.id.nav_button);
    }

    private void showWeatherInfo(Weather weather) {
        Intent intent = new Intent(this, AutoUpdateService.class);
        startActivity(intent);
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.upadateTime;
        String degree = weather.now.temperature + "°C";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        title_update_time.setText(updateTime);
        degree_text.setText(degree);
        weather_info_text.setText(weatherInfo);
        forecast_layout.removeAllViews();
        for (Forecast forcast:weather.forecastList) {
            View view   = LayoutInflater.from(this).inflate(R.layout.forcast_item, forecast_layout, false);
            TextView date_text = view.findViewById(R.id.date_text);
            TextView info_text = view.findViewById(R.id.info_text);
            TextView max_text = view.findViewById(R.id.max_text);
            TextView min_text = view.findViewById(R.id.min_text);
            date_text.setText(forcast.date);
            info_text.setText(forcast.more.info);
            max_text.setText(forcast.temperature.max);
            min_text.setText(forcast.temperature.min);
            forecast_layout.addView(view);
        }
        if (weather.aqi != null) {
            aqi_text.setText(weather.aqi.city.aqi);
            pm25_text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度" + weather.suggestion.comfort.info;
        String carWash = "洗车指数" + weather.suggestion.carWash.info;
        String sport = "运动建议" + weather.suggestion.sport.info;
        comfort_text.setText(comfort);
        car_wash_text.setText(carWash);
        sport_text.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
