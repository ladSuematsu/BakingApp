package com.ladsoft.bakingapp.manager

import android.content.Context
import android.content.SharedPreferences


object SessionManager {

    private val SHARED_PREFERENCES_FILENAME = "preferences"
    private val LAST_SELECTED_RECEIPT_ID = "last_selected_receipt_id"

    lateinit private var sharedPreferences: SharedPreferences
    lateinit private var sharedPreferencesEditor: SharedPreferences.Editor

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
    }

    var lastSelectedReceiptId = {
        sharedPreferences.getLong(LAST_SELECTED_RECEIPT_ID, 0)
    }

    fun setLastSelectedReceiptId(receiptId: Long) {
        sharedPreferencesEditor.putLong(LAST_SELECTED_RECEIPT_ID, receiptId).commit()
    }


}