package com.abedfattal.quranx.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abedfattal.quranx.sample.core.QuranManagementActivity
import com.abedfattal.quranx.sample.tajweedprocessor.TajweedProcessorActivity
import com.abedfattal.quranx.sample.tajweedrules.TajweedRulesActivity
import com.abedfattal.quranx.sample.wordsprocessor.WordsProcessorActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

     fun openSample(view: View) {
        val activity: Class<out AppCompatActivity> = when (view.id) {
            R.id.core_sample_button -> QuranManagementActivity::class.java
            R.id.tajweed_sample_button -> TajweedProcessorActivity::class.java
            R.id.tajweed_rule_sample_button -> TajweedRulesActivity::class.java
            R.id.words_sample_button -> WordsProcessorActivity::class.java
            else -> throw IllegalArgumentException("openSample sample was called with unknown view id")
        }

        startActivity(Intent(this, activity))
    }

}