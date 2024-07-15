package com.example.lesson14

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(RandomUserApi::class.java)

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = api.getUsers(1)
                _users.value = response.results
            } catch (e: Exception) {
                Log.e(TAG,"Something went wrong")
            }
        }
    }
}
