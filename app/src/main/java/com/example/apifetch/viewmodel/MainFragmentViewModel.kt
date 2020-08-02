package com.example.apifetch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apifetch.databse.Rows
import com.example.apifetch.util.notifyObserver
import com.example.myapplication.databse.Database
import com.example.myapplication.ws.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainFragmentViewModel : ViewModel() {

    var listData = MutableLiveData<MutableList<Rows>>()
    var listDataRecycle = MutableLiveData<MutableList<Rows>>()

    fun addData(data: Rows) {
        listData.value?.add(data)
        listData.notifyObserver()
    }

    private val repository: Repository = Repository()

    /*val listData : LiveData<MutableList<PostModel>> = liveData(Dispatchers.IO)  {
        val retrievedData = repository.getData()
        emit(retrievedData)
    }*/

    fun callApi() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val retrievedData = repository.getData()
                listData.postValue(retrievedData.rows)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> Log.e("TAG","IO Exception")
                    is HttpException -> {
                        val code = throwable.code()
                        //val errorResponse = convertErrorBody(throwable)
                        Log.e("TAG","HttpException $code")
                    }
                    else -> {
                        Log.e("TAG","throwable")
                    }
                }
            }

        }

    }

    fun getDatabaseList(offset: Int, context: Context): MutableList<Rows> {

        //return Database.getInstance(context).appDatabase.PostDao().getAllData(100, offset)
        return Database.getInstance(context).appDatabase.PostDao().getAllData()


    }


}