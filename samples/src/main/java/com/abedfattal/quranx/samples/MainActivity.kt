package com.abedfattal.quranx.samples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abedfattal.quranx.core.ReadLibrary
import com.abedfattal.quranx.tajweed.rules.TajweedRules
import com.abedfattal.quranx.tajweed.parser.Tajweed

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ReadLibrary.init(this)
        val tajweed = Tajweed()
        TajweedRules(tajweed)
    }
}