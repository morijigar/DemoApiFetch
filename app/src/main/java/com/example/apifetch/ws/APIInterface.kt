package com.example.myapplication.ws


import com.example.apifetch.databse.FactRes
import retrofit2.http.GET

interface APIInterface {


    @GET("facts.json")
    suspend fun getPosts() : FactRes
}