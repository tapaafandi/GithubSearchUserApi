package com.tapaafandi.githubsearchuserapi.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tapaafandi.githubsearchuserapi.data.network.models.Users
import com.tapaafandi.githubsearchuserapi.utils.Constants.Companion.API_KEY
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    val listOfUser = MutableLiveData<ArrayList<Users>>()

    fun searchUser(username: String) {
        val listSearchUser = ArrayList<Users>()

        val url = "https://api.github.com/search/users?q=${username}"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", API_KEY)
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                    val result = String(responseBody)
                    try {
                        val responseObject = JSONObject(result)
                        val items = responseObject.getJSONArray("items")

                        for (i in 0 until items.length()) {
                            val listUser = items.getJSONObject(i)
                            val userSearch = Users()
                            userSearch.username = listUser.getString("login")
                            userSearch.avatarUrl = listUser.getString("avatar_url")
                            listSearchUser.add(userSearch)
                        }

                        listOfUser.postValue(listSearchUser)

                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getUsers() : LiveData<ArrayList<Users>> {
        return listOfUser
    }




}