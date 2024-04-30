package com.example.teashop.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private const val PREFS_FILE_NAME = "auth_prefs"
private const val TOKEN_KEY = "jwt_token"
private const val ROLE_KEY = "role"

class TokenStorage {
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

    fun saveTokenAndRole(context: Context, token: String, role: String) {
        val sharedPreferences = initSharedPreferences(context)
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
        sharedPreferences.edit().putString(ROLE_KEY, role).apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = initSharedPreferences(context)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun getRole(context: Context): String? {
        val sharedPreferences = initSharedPreferences(context)
        return sharedPreferences.getString(ROLE_KEY, null)
    }

    fun deleteToken(context: Context) {
        val sharedPreferences = initSharedPreferences(context)
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
        sharedPreferences.edit().remove(ROLE_KEY).apply()
    }
}