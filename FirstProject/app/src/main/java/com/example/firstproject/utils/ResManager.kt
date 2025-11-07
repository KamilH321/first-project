package com.example.firstproject.utils

import android.content.Context
import androidx.annotation.StringRes

class ResManager (private val context: Context){

    fun getString(@StringRes resId: Int): String = context.getString(resId)

    fun getString(@StringRes resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }
}

