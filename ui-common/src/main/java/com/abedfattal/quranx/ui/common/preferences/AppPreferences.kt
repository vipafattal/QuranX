package com.abedfattal.quranx.ui.common.preferences

import android.content.Context
import android.content.SharedPreferences
import com.abedfattal.quranx.ui.common.extensions.edit;


/**
 * Created by Abed on 12/14/2017.
 */
class AppPreferences(sharedPreferencesFileName: String = "AppPreferences", context: Context) {

    private val mPref: SharedPreferences by lazy {
        context.getSharedPreferences(
            sharedPreferencesFileName,
            Context.MODE_PRIVATE
        )
    }

    fun getDouble(key: String, defValue: Double = 0.0): Double {
        return mPref.getString(key, defValue.toString())!!.toDouble()
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return mPref.getInt(key, defValue)
    }

    fun getLong(key: String, defValue: Long = 0): Long {
        return mPref.getLong(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return mPref.getBoolean(key, defValue)
    }

    fun getStr(key: String, defValue: String = ""): String {
        return mPref.getString(key, defValue)!!
    }

    fun put(key: String, `val`: String) {

        mPref.edit {
            putString(key, `val`)
        }
    }


    fun put(key: String, `val`: Int) {
        mPref.edit {
            putInt(key, `val`)
        }
    }

    fun put(key: String, `val`: Long) {
        mPref.edit {
            putLong(key, `val`)
        }
    }


    fun put(key: String, `val`: Boolean) {
        mPref.edit {
            putBoolean(key, `val`)
        }

    }


    fun put(key: String, `val`: Float) {
        mPref.edit {
            putFloat(key, `val`)
        }
    }

    fun put(key: String, `val`: Double) {
        mPref.edit {
            putString(key, `val`.toString())
        }
    }

    fun remove(key: String) {
        mPref.edit { this.remove(key) }
    }

    fun clear() {
        mPref.edit {
            clear()
        }
    }
}

