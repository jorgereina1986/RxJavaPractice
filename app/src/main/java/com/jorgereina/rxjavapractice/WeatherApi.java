package com.jorgereina.rxjavapractice;


import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by jorgereina on 5/8/18.
 */

public interface WeatherApi {
    //"http://api.openweathermap.org/data/2.5/weather?q=11237&units=imperial&appid=726214b63da745beaf2dcd7eda63442a"
    @GET("data/2.5/weather?q=11237&units=imperial&appid=726214b63da745beaf2dcd7eda63442a")
    Observable<Weather> getWeather();


}
