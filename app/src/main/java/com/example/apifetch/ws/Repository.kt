package com.example.myapplication.ws

import com.example.apifetch.ws.RetrofitClientInstance

class Repository{

    var client = RetrofitClientInstance.getRetrofitInstance().create(APIInterface::class.java)

    suspend fun getData() = client.getPosts()

}