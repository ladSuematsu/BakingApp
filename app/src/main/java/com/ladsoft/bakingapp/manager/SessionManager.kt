package com.ladsoft.bakingapp.manager

import android.content.Context
import android.content.SharedPreferences

class SessionManager(val context : Context) {
    private val SHARED_PREFERENCES_FILENAME = "preferences"
    private val LAST_SELECTED_RECEIPT_ID = "last_selected_receipt_id"

    private val sharedPreferences: SharedPreferences
    private val sharedPreferencesEditor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
    }

    var lastSelectedReceiptId = {
        sharedPreferences.getLong(LAST_SELECTED_RECEIPT_ID, 0L)
    }

    fun setLastSelectedReceiptId(receiptId: Long) {
        sharedPreferencesEditor.putLong(LAST_SELECTED_RECEIPT_ID, receiptId).commit()
    }


}