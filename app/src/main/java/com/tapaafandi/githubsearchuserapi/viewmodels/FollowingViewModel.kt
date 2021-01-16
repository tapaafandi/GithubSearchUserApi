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
import org.json.JSONArray
import java.lang.Exception

class FollowingViewModel : ViewModel() {

    val listOfFollowingUser = MutableLiveData<ArrayList<Users>>()

    fun getFollowingUser(username: String) {
        val listUser = ArrayList<Users>()
        val url = "https://api.github.com/users/${username}/following"

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
                    val followingArray = JSONArray(result)
                    for (i in 0 until followingArray.length()) {
                        val jsonObject = followingArray.getJSONObject(i)
                        val followingUser = Users()
                        followingUser.username = jsonObject.getString("login")
                        followingUser.avatarUrl = jsonObject.getString("avatar_url")
                        listUser.add(followingUser)
                    }
                    listOfFollowingUser.postValue(listUser)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getFollowingList() : LiveData<ArrayList<Users>> {
        return listOfFollowingUser
    }
}