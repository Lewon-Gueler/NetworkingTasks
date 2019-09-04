package com.example.networking


import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET ("get")
    fun getData(): retrofit2.Call<Datas>

    @POST ("post")
    fun sendData(@Body body: Datas ): retrofit2.Call<Datas>

    @DELETE ("delete")
    fun deleteData(): retrofit2.Call<Datas>

}