package com.abedfattal.quranx.ui.common.preferences

import android.content.Context
import android.content.SharedPreferences
import com.abedfattal.quranx.ui.common.CommonUI.Companion.context
import com.abedfattal.quranx.ui.common.extensions.edit;


/**
 * Created by Abed on 12/14/2017.
 */
class AppPreferences constructor(fileName: String = "AppPreferences") {

    private val mPref: SharedPreferences by lazy {
        context.getSharedPreferences(
            fileName,
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

    fun getStrNullable(key: String): String? {
        return mPref.getString(key, "")!!.run {
            ifEmpty { null }
        }
    }

    fun putOf(vararg pairs: Pair<String, Any>) {
        pairs.forEach { (key, value) ->
            when (value) {
                is Long -> put(key, value)
                is Int -> put(key, value)
                is String -> put(key, value)
                is Boolean -> put(key, value)
                is Float -> put(key, value)
                is Double -> put(key, value)
            }

        }
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

