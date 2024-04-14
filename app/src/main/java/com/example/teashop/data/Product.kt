package com.example.teashop.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @StringRes val nameId:Int,
    val id:String,
    val rate: Double,
    val rateCnt: Int,
    val price: Int,
    val previousPrice: Int,
    val bonusCnt: Int,
    val description: String,
    val feedBackList: List<Feedback> = listOf(),
    @DrawableRes val imageResourceId:Int
):Parcelable
