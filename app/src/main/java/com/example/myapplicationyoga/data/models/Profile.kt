package com.example.myapplicationyoga.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.sql.Date
import java.sql.Timestamp


@Serializable
data class Profile (
    val image: String?,
    val birthday: String,
    val username:String,
    val surname:String,
    val id:String?


)


