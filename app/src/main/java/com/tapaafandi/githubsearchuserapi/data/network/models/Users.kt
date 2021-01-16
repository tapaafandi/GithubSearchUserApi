package com.tapaafandi.githubsearchuserapi.data.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var username: String? = "",
    var avatarUrl: String? = "",
) : Parcelable