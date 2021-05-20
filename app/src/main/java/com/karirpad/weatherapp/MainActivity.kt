package com.karirpad.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.karirpad.weatherapp.databinding.ActivityMainBinding
import com.karirpad.weatherapp.model.WeatherModel
import com.karirpad.weatherapp.remote.ApiClient
import com.karirpad.weatherapp.remote.ApiInterface
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: WeatherAdapter

    private var weatherList = mutableListOf<WeatherModel.Weather>()

    private val API_KEY = "49ea99267f49ec8ec6a93d5a0655fb9c"
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = WeatherAdapter(this, weatherList)

        initView()
    }

    fun initView() {
        binding.rvWeather.layoutManager = LinearLayoutManager(this)
        binding.rvWeather.adapter = mAdapter
        binding.btnSearch.setOnClickListener(this)
    }

    fun formValidation() {
        val input = binding.etInput.text.toString()

        if (input.isEmpty()) {
            Toast.makeText(this, "Fill your input", Toast.LENGTH_SHORT).show()
        } else {
            searchWeather(input)

            disposable.add(searchWeather(input))
        }
    }

    fun searchWeather(input: String): DisposableObserver<Response<WeatherModel>> {
        return ApiClient.getClient().create(ApiInterface::class.java).getWeather(input, API_KEY)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<WeatherModel>>() {
                override fun onNext(t: Response<WeatherModel>) {

                    if (t.code() == 200) {
                        weatherList.clear()
                        t.body()?.weather?.let { weatherList.addAll(it) }
                        mAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(applicationContext, "Failur get weather", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    println("done get weather")
                }

            })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnSearch -> {
                formValidation()
            }
        }
    }

    override fun onStop() {
        disposable.dispose()
        super.onStop()
    }
}