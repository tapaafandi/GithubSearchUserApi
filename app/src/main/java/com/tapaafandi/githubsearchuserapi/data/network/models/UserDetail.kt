package com.tapaafandi.githubsearchuserapi.data.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    var username: String? = "",
    var name: String? = "",
    var avatarUrl: String? = "",
    var bio: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var following: String? = "",
    var followers: String? = "",
) : Parcelable