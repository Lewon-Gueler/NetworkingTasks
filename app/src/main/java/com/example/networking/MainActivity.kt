package com.example.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Logging
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging);

        //Komponents
        val btnGet = btnGet
        val btnSend = btnSend
        val btnDelete = btnDelete
        val tvGet = textViewGet
        val etSend = editTextSend
        val tvDelete = textViewDelete

        var retrofit = Retrofit.Builder()
            .baseUrl("http://httpbin.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        // Jason to Kotlin
        val gson = Gson()
        val json: String = """{"id":1,"url":"login"}"""
        val testModel = gson.fromJson(json, Datas::class.java)
        tvGet.text = "$testModel"

        //Kotlin to Jason
        val jsonString = gson.toJson(Datas(1,"Test"))
        tvDelete.text = jsonString

        //val service = retrofit.create(JasonPlaceHolder::class.java)
        //val call = service.getPosts()

        btnGet.setOnClickListener {
            /* service.sendPosts(Datas(1, "String2")).enqueue(object : Callback<Api> {
                 override fun onResponse(call: Call<Api>?, response: Response<Datas>?) {
                     if (response?.isSuccessful() == false) {
                         textView2.text = "Code ${response.code()}"
                         return
                     }
                     val stringBuilder = response?.let {
                         it.body().
                         it.body().origin + "" + "  " + it.body().url
                     }
                     textView2.text = stringBuilder
                 }
                 override fun onFailure(call: Call<Datas>?, t: Throwable?) {
                     textView2.text = t?.message
                 }
             }) */
        }
    }
}
