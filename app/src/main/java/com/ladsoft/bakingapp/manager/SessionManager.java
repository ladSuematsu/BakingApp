package com.ladsoft.bakingapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String SHARED_PREFERENCES_FILENAME = "preferences";
    private static final String LAST_SELECTED_RECEIPT_ID = "last_selected_receipt_id";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor sharedPreferencesEditor;
    public SessionManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE);
        this.sharedPreferencesEditor = sharedPreferences.edit();
    }

    public long getLastSelectedReceiptId() {
        return sharedPreferences.getLong(LAST_SELECTED_RECEIPT_ID, 0L);
    }

    public void setLastSelectedReceiptId(long receiptId) {
        sharedPreferencesEditor.putLong(LAST_SELECTED_RECEIPT_ID, receiptId).commit();
    }

}
