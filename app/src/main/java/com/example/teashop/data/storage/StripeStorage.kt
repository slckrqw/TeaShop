package com.example.teashop.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private const val PREFS_FILE_NAME = "stripe"
private const val CUSTOMER_KEY = "customer"
private const val EPH_KEY = "ephemeral"

class StripeStorage {
    private fun initSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveCustomerAndKey(context: Context, customer: String, key: String) {
        val sharedPreferences = initSharedPreferences(context)
        sharedPreferences.edit().putString(CUSTOMER_KEY, customer).apply()
        sharedPreferences.edit().putString(EPH_KEY, key).apply()
    }

    fun getCustomer(context: Context): String? {
        val sharedPreferences = initSharedPreferences(context)
        return sharedPreferences.getString(CUSTOMER_KEY, null)
    }

    fun getCKey(context: Context): String? {
        val sharedPreferences = initSharedPreferences(context)
        return sharedPreferences.getString(EPH_KEY, null)
    }
}