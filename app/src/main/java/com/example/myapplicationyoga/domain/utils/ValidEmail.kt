package com.example.myapplicationyoga.domain.utils

import android.text.TextUtils

fun String.ValidEmail() :Boolean{
    return !TextUtils.isEmpty(this)&& android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}