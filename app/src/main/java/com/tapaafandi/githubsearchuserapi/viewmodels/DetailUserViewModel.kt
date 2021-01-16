package com.tapaafandi.githubsearchuserapi.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tapaafandi.githubsearchuserapi.data.network.models.UserDetail
import com.tapaafandi.githubsearchuserapi.utils.Constants
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailUserViewModel : ViewModel() {

    val dataDetailUser = MutableLiveData<UserDetail>()

    fun getUserDetailByUsername(username: String?) {
        val url = "https://api.github.com/users/${username}"
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
                    val jsonObject = JSONObject(result)
                    val users = UserDetail()
                    users.username = jsonObject.getString("login")
                    users.name = jsonObject.getString("name")
                    users.bio = jsonObject.getString("bio")
                    users.avatarUrl = jsonObject.getString("avatar_url")
                    users.company = jsonObject.getString("company")
                    users.location = jsonObject.getString("location")
                    users.repository = jsonObject.getString("public_repos")
                    users.followers = jsonObject.getString("followers")
                    users.following = jsonObject.getString("following")
                    dataDetailUser.postValue(users)
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

    fun getDetailUser() : LiveData<UserDetail> {
        return dataDetailUser
    }
}