package com.example.teashop.data.model.variant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Variant(
    val id: Int,
    var title: VariantType
): Parcelable

