package com.example.myapplicationyoga.data.models

import kotlinx.serialization.Serializable


@Serializable
data class yogaclasses (
    val image: String?,
    val name: String,
    val description:String,
    val categoryId:Int,
    val price:String,
    val id:String
)