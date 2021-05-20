package com.karirpad.weatherapp.remote

import com.karirpad.weatherapp.model.WeatherModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Observable<Response<WeatherModel>>
}