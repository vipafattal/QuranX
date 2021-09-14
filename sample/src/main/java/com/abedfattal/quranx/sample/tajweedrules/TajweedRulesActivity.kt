package com.abedfattal.quranx.sample.tajweedrules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.tajweedparser.JuzViewModel
import com.abedfattal.quranx.sample.utils.observer
import com.abedfattal.quranx.sample.utils.viewModelOf
import com.abedfattal.quranx.tajweedpasrser.Tajweed
import com.abedfattal.quranx.tajweedpasrser.TajweedRules

class TajweedRulesActivity : AppCompatActivity() {

    private val juzViewModel: JuzViewModel by lazy { viewModelOf() }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.tajweed_parser_recycler) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tajweed_parser)

        juzViewModel.getJuzVerses(1, "quran-tajweed").observer(this) { verses ->
                recyclerView.adapter = TajweedRulesAdapter(verses!!)
        }
    }

    companion object {
         val tajweed: Tajweed = Tajweed()
         val tajweedRules = TajweedRules(tajweed)
    }
}