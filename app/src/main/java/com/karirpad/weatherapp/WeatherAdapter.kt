package com.karirpad.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karirpad.weatherapp.model.WeatherModel

class WeatherAdapter(val context: Context, val weatherList: List<WeatherModel.Weather>):
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.vh_weather, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
        holder.bindData(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvWeather: TextView = view.findViewById(R.id.tvWeather)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)

        fun bindData(weather: WeatherModel.Weather) {
            tvWeather.text = weather.main
            tvDesc.text = weather.description
        }
    }
}