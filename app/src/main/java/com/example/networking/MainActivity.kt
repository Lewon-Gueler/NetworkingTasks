package com.example.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://httpbin.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        // Jason to Kotlin
      // val gson = Gson()
      //  val json: String = """{"id":1,"url":"login"}"""
      //  val testModel = gson.fromJson(json, Datas::class.java)
      //  tvGet.text = "$testModel"

//        //Kotlin to Jason


        val service = retrofit.create(Api::class.java)

        btnGet.setOnClickListener {
            service.getData().enqueue(object : Callback<Datas> {

                override fun onResponse(call: Call<Datas>, response: Response<Datas>) {
                    if (!response.isSuccessful) {
                        tvGet.text = "Code ${response.code()}"
                        return
                    }
                    val stringBuilder = response.let {
                        it.body()?.url
                    }
                    tvGet.text = stringBuilder
                }

                override fun onFailure(call: Call<Datas>, t: Throwable) {
                    tvGet.text = t.message

                }
            })
        }

        btnSend.setOnClickListener {
            val userInput  = etSend.text.toString()
            val jsonObject =  JsonObject() //creating new JsonObject
            jsonObject.addProperty("userInput", userInput)



                service.sendData(jsonObject).enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                       /* response.let {
                              it.body()?.userInput
                        }
                        tvDelete.text = convertM */

                        //val gson = Gson()
                        //val convertM = gson.fromJson(response.body(), Datas::class.java) //konvertierung
                        tvDelete.text = response.body()?.toString()
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        tvDelete.text = t.message
                    }

                })
        }

        btnDelete.setOnClickListener {
            service.deleteData().enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        return
                    }
                    response.let {
                        tvDelete.text = it.body()
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    tvDelete.text = t.message
                }
            })
        }
    }
}

/*Parsing stuff
Parse String into a Json Array loop over the Array to create for each element one Kotlin Object
private fun parseRepos(jsonString: String) : List<Repo> {
    val repos = mutableListOf<Repo>()
    val reposArray = JsonArray(jsonString)
    for (i in 0 until reposArray.length()) {
        val repoObject = reposArray.getJSONObject(i)
        val repo = Repo(repoObject.getString("name"))
        repos.add(repo)
    }
    return repos
}
//Parse Gist Data into a JSON Array
private fun parseGits(jsonString: String) : List<Repo> {
    val gist = mutableListOf<Repo>()
    val reposArray = JSONArray(jsonString)
    for (i in 0 until reposArray.length()) {
        val gistObject = reposArray.getJSONObject(i)
        val createdAt = gistObject.getString("created_at")
        val description = gistObject.getString("created_at")
        val gist = Gist(createdAt, description)
        gist.add(gist)
    }
    return gist
*/