package com.abedfattal.quranx.sample.tajweedprocessor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.sample.QURAN_TAJWEED_EDITION
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.utils.observer
import com.abedfattal.quranx.sample.utils.viewModelOf
import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors

class TajweedProcessorActivity : AppCompatActivity() {

    private lateinit var tajweed: Tajweed
    private val juzViewModel: JuzViewModel by lazy { viewModelOf() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tajweed_parser)

        init()
        //Binding Juz number 1, to a vertical RecyclerView.
        val recyclerView: RecyclerView = findViewById(R.id.tajweed_parser_recycler)

        juzViewModel.getJuzVerses(2, QURAN_TAJWEED_EDITION).observer(this) { verses ->
            recyclerView.adapter = TajweedAdapter(verses!!, tajweed)
        }
    }


    private fun init() {
        val metaColors = MetaColors(hsl = "#FF6200EE", ikhafa = "#D50000")
        //Optional: applying custom tajweed colors.
        tajweed = Tajweed(metaColors)
    }

    fun doSomething() {
        val rawAyah =
            "يَ[o[ـٰٓ]أَيُّهَا [h:541[ٱ]لَّذِينَ ءَامَن[o[ُوٓ][s[اْ] إِذَا تَدَايَ[f:1655[نت][c:1656[ُم ب]ِدَيْنٍ إِلَ[o[ىٰٓ] أَجَ[a:1657[لٍ م]ُّسَم[f:1658[ًّى ف]َ[h:1659[ٱ]كْتُبُوهُ‌ۚ وَلْيَكْتُب بَّيْنَكُمْ كَاتِ[i:1660[بُۢ ب]ِ[h:1661[ٱ]لْعَ[q:1661[دْ]لِ‌ۚ وَلَا يَأْبَ كَاتِبٌ أ[a:1662[َن ي]َكْتُبَ كَمَا عَلَّمَهُ [h:1663[ٱ]للَّهُ‌ۚ فَلْيَكْتُ[q:1664[بْ] وَلْيُمْلِلِ [h:1665[ٱ]لَّذِى عَلَيْهِ [h:1666[ٱ]لْحَقُّ وَلْيَتَّقِ [h:1667[ٱ]للَّهَ رَبَّهُ[n[ۥ] وَلَا يَ[q:1668[بْ]خَسْ مِنْهُ شَيْـ[f:1669[ـًٔا‌ۚ ف]َإِ[f:1670[ن ك]َانَ [h:1671[ٱ]لَّذِى عَلَيْهِ [h:1666[ٱ]لْحَقُّ سَفِيهًا أَوْ ضَعِيفًا أَوْ لَا يَسْتَطِيعُ أ[a:1672[َن ي]ُمِلَّ هُوَ فَلْيُمْلِلْ وَلِيُّهُ[n[ۥ] بِ[h:1661[ٱ]لْعَ[q:1661[دْ]لِ‌ۚ وَ[h:1673[ٱ]سْتَشْهِدُو[s[اْ] شَهِيدَيْنِ م[u:1674[ِن ر]ِّجَالِكُمْ‌ۖ فَإ[u:110[ِن ل]َّمْ يَكُونَا رَجُلَيْنِ فَرَجُ[a:1675[لٌ و]َ[h:1676[ٱ]مْرَأَتَانِ مِ[g[مّ]َ[f:1677[ن ت]َرْضَوْنَ مِنَ [h:1678[ٱ][l[ل]شُّهَد[o[َا]ٓءِ أَ[f:1679[ن ت]َضِلَّ إِحْدَ[n[ٮٰ]هُمَا فَتُذَكِّرَ إِحْدَ[n[ٮٰ]هُمَا [h:1680[ٱ]لْأُخْرَىٰ‌ۚ وَلَا يَأْبَ [h:1681[ٱ][l[ل]شُّهَد[o[َا]ٓءُ إِذَا مَا دُعُو[s[اْ‌ۚ] وَلَا تَسْــَٔم[o[ُوٓ][s[اْ] أَ[f:1682[ن ت]َكْتُبُوهُ صَغِيرًا أَوْ كَبِيرًا إِلَ[o[ىٰٓ] أَجَلِه[n[ِۦ]‌ۚ ذ[n[َٲ]لِكُمْ أَ[q:1683[قْ]سَطُ عِ[f:264[ند]َ [h:408[ٱ]للَّهِ وَأَ[q:1684[قْ]وَمُ لِلشَّهَ[n[ـٰ]دَةِ وَأَ[q:1685[دْ]نَ[o[ىٰٓ] أَلَّا تَرْتَاب[o[ُوٓ[s[اْ]‌ۖ] إِلّ[o[َآ] أَ[f:1566[ن ت]َكُونَ تِجَ[n[ـٰ]رَةً حَاضِرَ[f:1686[ةً ت]ُدِيرُونَهَا بَيْنَكُمْ فَلَيْسَ عَلَيْكُمْ جُنَاحٌ أَلَّا تَكْتُبُوهَا‌ۗ وَأَشْهِد[o[ُوٓ][s[اْ] إِذَا تَبَايَعْتُمْ‌ۚ وَلَا يُض[m[َا]ٓرَّ كَاتِ[a:1687[بٌ و]َلَا شَهِي[a:1688[دٌ‌ۚ و]َإِ[f:1689[ن ت]َفْعَلُو[s[اْ] فَإِ[g[نّ]َهُ[n[ۥ] فُسُو[i:1690[قُۢ ب]ِكُمْ‌ۗ وَ[h:1018[ٱ]تَّقُو[s[اْ] [h:1019[ٱ]للَّهَ‌ۖ وَيُعَلِّمُكُمُ [h:1691[ٱ]للَّهُ‌ۗ وَ[h:72[ٱ]للَّهُ بِكُلِّ شَىْءٍ عَل[p[ِي]مٌ"
        tajweed.onEachSplit(rawAyah) { metaCode, ayahSplit ->
            //Do something else...
        }
    }
}