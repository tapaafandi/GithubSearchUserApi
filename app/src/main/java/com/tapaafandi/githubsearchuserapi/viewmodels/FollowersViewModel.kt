package com.tapaafandi.githubsearchuserapi.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tapaafandi.githubsearchuserapi.data.network.models.Users
import com.tapaafandi.githubsearchuserapi.utils.Constants
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowersViewModel : ViewModel() {

    val listOfFollowersUser = MutableLiveData<ArrayList<Users>>()

    fun getFollowersUser(username: String) {
        val listUser = ArrayList<Users>()
        val url ="https://api.github.com/users/${username}/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", Constants.API_KEY)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val followersArray = JSONArray(result)
                    for (i in 0 until followersArray.length()) {
                        val jsonObject = followersArray.getJSONObject(i)
                        val followersUser = Users()
                        followersUser.username = jsonObject.getString("login")
                        followersUser.avatarUrl = jsonObject.getString("avatar_url")
                        listUser.add(followersUser)
                    }
                    listOfFollowersUser.postValue(listUser)

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

    fun getFollowersList() : LiveData<ArrayList<Users>> {
        return listOfFollowersUser
    }
}