package com.example.teashop.data.model.product

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.teashop.data.model.review.Review
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
    val feedBackList: List<Review> = listOf(),
    @DrawableRes val imageResourceId:Int
):Parcelable
