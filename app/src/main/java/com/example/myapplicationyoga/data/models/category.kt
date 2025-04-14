package com.example.myapplicationyoga.data.models

import kotlinx.serialization.Serializable


@Serializable
data class category (
    val id: Int,
    val categoryname: String
)